package org.zmqy.erp.domain.mis.sys.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.security.IResourceDomain;
import org.zmqy.erp.mapper.mis.biz.sys.base.MenuLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.base.MenuMapper;
import org.zmqy.erp.model.mis.bo.sys.base.MenuBo;
import org.zmqy.erp.model.mis.bo.sys.base.MenuLangBo;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceBo;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.model.mis.entity.sys.base.MenuLang;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class MenuDomain implements IMenuDomain {
    @Autowired
    private MenuMapper mapper;
    @Autowired
    private MenuLangMapper menuLangMapper;
    @Autowired
    private IResourceDomain resourceDomain;

    @Transactional(rollbackFor = Exception.class)
    public String add(MenuBo bo, String operUserId, Date operTime) throws Exception {
        if (bo == null) {
            throw new RuntimeException("请输入菜单");
        }

        Menu menu = MenuBo.bo2entity(bo);

        this.add(menu, operUserId, operTime);

        List<MenuLangBo> menuLangBoList = bo.getMenuLangBoList();

        if (menuLangBoList == null || menuLangBoList.size() <= 0) {
            throw new RuntimeException("请输入菜单名称");
        }

        for (MenuLangBo menuLangBo : menuLangBoList) {
            MenuLang menuLang = MenuLangBo.bo2entity(menuLangBo);
            menuLang.setMenuId(menu.getId());

            this.add(menuLang, operUserId, operTime);
        }

        if (bo.getResourceBoList() != null) {
            for (ResourceBo resourceBo : bo.getResourceBoList()) {
                resourceBo.setMenuId(menu.getId());
                this.resourceDomain.saveById(resourceBo, operUserId, operTime);
            }
        }

        return menu.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfById(MenuBo bo, String operUserId, Date operTime) throws Exception {
        if (bo == null) {
            throw new RuntimeException("请输入菜单");
        }

        Menu menu = MenuBo.bo2entity(bo);

        this.mdfById(menu, operUserId, operTime);

        if (bo.getMenuLangBoList() != null) {
            for (MenuLangBo menuLangBo : bo.getMenuLangBoList()) {
                MenuLang menuLang = MenuLangBo.bo2entity(menuLangBo);
                menuLang.setMenuId(menu.getId());

                this.save(menuLang, operUserId, operTime);
            }
        }

        if (bo.getResourceBoList() != null) {
            for (ResourceBo resourceBo : bo.getResourceBoList()) {
                resourceBo.setMenuId(menu.getId());
                this.resourceDomain.saveById(resourceBo, operUserId, operTime);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String save(MenuBo bo, String operUserId, Date operTime) throws Exception {
        if (bo == null) {
            throw new RuntimeException("请输入菜单");
        }

        if (StringUtil.isEmpty(bo.getId())) {
            return this.add(bo, operUserId, operTime);
        } else {
            this.mdfById(bo, operUserId, operTime);
            return bo.getId();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfMenuSeq(String menuId, String parentMenuId, String frontMenuId, String behindMenuId,
                           String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(menuId)){
            throw new RuntimeException("请输入菜单id");
        }

        if(StringUtil.isEmpty(parentMenuId)){
            throw new RuntimeException("请输入父级菜单id");
        }

        if(StringUtil.isNotEmpty(frontMenuId) && StringUtil.isNotEmpty(behindMenuId)){
            throw new RuntimeException("前菜单和后菜单只输入一个即可");
        }

        Integer seq = null;

        if(StringUtil.isNotEmpty(frontMenuId)){
            Menu frontMenu = this.getById(frontMenuId);

            if(frontMenu == null){
                throw new RuntimeException("没有这个前菜单");
            }

            seq = frontMenu.getSeq() + 1;

            this.mapper.plusSeq(parentMenuId, seq, operTime);

        }else if(StringUtil.isNotEmpty(behindMenuId)){
            Menu behindMenu = this.getById(behindMenuId);

            if(behindMenu == null){
                throw new RuntimeException("没有这个后菜单");
            }

            seq = behindMenu.getSeq() - 1;

            this.mapper.minusSeq(parentMenuId, seq, operTime);
        }else {
            seq = 1;
        }

        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setSeq(seq);
        menu.setParentMenuId(parentMenuId);

        this.mdfById(menu, operUserId, operTime);

        // 层级不能超过5级
        menu = this.getById(menuId);

        if(menu.getMenuLevel() > 5){
            throw new RuntimeException("菜单层级不能超过5级");
        }
    }

    public Boolean existsMenu(String menuId) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.ID, menuId);

        if (this.mapper.selectCount(queryWrapper) > 0) {
            return true;
        }

        return false;
    }

    // 以下为 类私有方法
    @Transactional(rollbackFor = Exception.class)
    public String add(Menu entity, String operUserId, Date operTime) throws Exception{
        if (StringUtil.isEmpty(entity.getMenuNo())) {
            throw new RuntimeException("请输入菜单编码");
        }

        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq(Menu.MENUNO, entity.getMenuNo());

        if (this.mapper.selectOne(menuQueryWrapper) != null) {
            throw new RuntimeException("菜单编码重复");
        }

        if (StringUtil.isEmpty(entity.getParentMenuId())) {
            throw new RuntimeException("请输入父级菜单");
        }

        Menu parentMenu = mapper.selectById(entity.getParentMenuId());

        if (parentMenu == null) {
            throw new RuntimeException("没有这个父级菜单");
        }

        entity.setMenuLevel(parentMenu.getMenuLevel() + 1);

        if (StringUtil.isEmpty(entity.getIconUrl())) {
            entity.setIconUrl("");
        }

        if (StringUtil.isEmpty(entity.getMenuClientNo())) {
            //throw new RuntimeException("请输入菜单客户端编码");
            entity.setMenuClientNo(parentMenu.getMenuClientNo());
        }

        if (entity.getAreCatalog() == null) {
            entity.setAreCatalog(false);
        }

        if (entity.getSeq() == null) {
            entity.setSeq(1);
        }

        if (entity.getAreHqDisplay() == null) {
            entity.setAreHqDisplay(true);
        }

        if (entity.getAreShopDisplay() == null) {
            entity.setAreShopDisplay(true);
        }

        if (entity.getAreDcDisplay() == null) {
            entity.setAreDcDisplay(true);
        }
        if(entity.getReportId() == null){
            entity.setReportId("");
        }

        entity.setId(null);
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfById(Menu entity, String operUserId, Date operTime) throws Exception{
        if (StringUtil.isEmpty(entity.getId())) {
            throw new RuntimeException("请输入菜单id");
        }

        if (StringUtil.isEmpty(entity.getMenuNo())) {
            entity.setMenuNo(null);
        }

        if (StringUtil.isEmpty(entity.getParentMenuId())) {
            entity.setParentMenuId(null);
        } else {
            Menu paraentMenu = this.mapper.selectById(entity.getParentMenuId());

            if (paraentMenu == null) {
                throw new RuntimeException("没有这个父级菜单");
            }

            entity.setMenuLevel(paraentMenu.getMenuLevel() + 1);
        }

        if (StringUtil.isEmpty(entity.getMenuClientNo())) {
            entity.setMenuClientNo(null);
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.mapper.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public String add(MenuLang entity, String operUserId, Date operTime) {
        if (entity == null) {
            throw new RuntimeException("请输入菜单名称");
        }

        if (StringUtil.isEmpty(entity.getMenuId())) {
            throw new RuntimeException("请输入菜单");
        }

        if (StringUtil.isEmpty(entity.getLangId())) {
            throw new RuntimeException("请输入语言");
        }

        if (StringUtil.isEmpty(entity.getMenuName())) {
            throw new RuntimeException("请输入菜单名称");
        }

        if (StringUtil.isEmpty(entity.getMenuDesc())) {
            entity.setMenuDesc("");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.menuLangMapper.insert(entity);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdf(MenuLang entity, String operUserId, Date operTime) {
        if (entity == null) {
            throw new RuntimeException("请输入菜单名称");
        }

        if (StringUtil.isEmpty(entity.getMenuId())) {
            throw new RuntimeException("请输入菜单id");
        }

        if (StringUtil.isEmpty(entity.getLangId())) {
            throw new RuntimeException("请输入语言id");
        }

        // 修改
        if (entity.getMenuName() != null && entity.getMenuName().trim().equals("")) {
            throw new RuntimeException("请输入菜单名称");
        }

        // 什么都没更新
        if (entity.getMenuName() == null && entity.getMenuDesc() == null) {
            return;
        }

        QueryWrapper<MenuLang> menuLangQueryWrapper = new QueryWrapper<>();
        menuLangQueryWrapper.eq(MenuLang.MENUID, entity.getMenuId())
                            .eq(MenuLang.LANGID, entity.getLangId());

        entity.setId(null);
        entity.setMenuId(null);
        entity.setLangId(null);
        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        this.menuLangMapper.update(entity, menuLangQueryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public String save(MenuLang entity, String operUserId, Date operTime) {
        if (entity == null) {
            throw new RuntimeException("请输入菜单名称");
        }

        QueryWrapper<MenuLang> menuLangQueryWrapper = new QueryWrapper<>();
        menuLangQueryWrapper.eq(MenuLang.MENUID, entity.getMenuId()).eq(MenuLang.LANGID, entity.getLangId());

        if (this.menuLangMapper.selectCount(menuLangQueryWrapper) == 0) {
            return this.add(entity, operUserId, operTime);
        } else {
            this.mdf(entity, operUserId, operTime);
            return entity.getId();
        }
    }

    public List<Menu> selectByMap(Map<String,Object> paramsMap){
        return mapper.selectByMap(paramsMap);
    }

    public Menu getById(String menuId){
        return this.mapper.selectById(menuId);
    }

    @Override
    public Menu getByMenuNo(String menuNo) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.MENUNO, menuNo);

        return this.mapper.selectOne(queryWrapper);
    }
}
