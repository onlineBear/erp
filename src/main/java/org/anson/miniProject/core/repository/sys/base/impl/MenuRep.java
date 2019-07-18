package org.anson.miniProject.core.repository.sys.base.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.MenuMapper;
import org.anson.miniProject.core.model.po.sys.base.Menu;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.base.IMenuRep;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.anson.miniProject.tool.helper.LogicDelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class MenuRep extends BaseRep<Menu, MenuMapper>
                     implements IMenuRep {
    // 接口命令(需要事务)
    @Override
    public String insert(Menu po, String operUserId, Date operTime) throws Exception{
        // 必填检查
        Object[] valArray = {po.getNo(), po.getName(), po.getParentMenuId()};
        String[] errArray = {"请输入菜单编码", "请输入菜单名称", "请选择上级菜单"};
        InputParamHelper.required(valArray, errArray);

        // 检查编码
        if (this.isExistsByNo(po.getNo())){
            throw new RuntimeException("菜单编码重复, 菜单编码 = " + po.getNo());
        }

        // 检查上级菜单
        Menu parentMenu = this.mapper.selectById(po.getId());

        if (parentMenu == null){
            throw new RuntimeException("没有这个上级菜单, 上级菜单id = " + po.getParentMenuId());
        }

        po.setId(po.getNo());
        po.setClientKey(parentMenu.getClientKey());     // 客户端key 和 父菜单一致
        po.setPath(this.calcPath(parentMenu.getPath(), po.getId()));    // path = 父菜单 path + / + id

        po.setCreateTime(operTime);
        po.setCreateUserId(operUserId);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    @Override
    public String update(Menu po, String operUserId, Date operTime) throws Exception{
        // 查询修改前的菜单
        Menu oldMenu = this.mapper.selectById(po.getId());

        if(oldMenu == null){
            throw new RuntimeException("没有这个菜单, 菜单id = " + po.getId());
        }

        // 若修改了父级菜单
        if(StrUtil.isNotEmpty(po.getParentMenuId()) && !po.getParentMenuId().equals(oldMenu.getParentMenuId())){
            Menu parentMenu = this.mapper.selectById(po.getParentMenuId());
            if (parentMenu == null){
                throw new RuntimeException("没有这个上级菜单, 上级菜单id = " + po.getParentMenuId());
            }

            // 修改 子菜单的 clientKey 和 path
            List<String> parentMenuIdList = new ArrayList<>();
            parentMenuIdList.add(po.getId());
            this.mapper.updateChildByParent(parentMenuIdList, operTime);

            // 设置自己的 clientKey 和 path
            po.setPath(this.calcPath(parentMenu.getPath(), po.getId()));
            po.setClientKey(parentMenu.getClientKey());
        }

        po.setNo(null); // 编码不可修改

        po.setCreateUserId(null);
        po.setCreateTime(null);
        po.setLastUpdateTime(operTime);
        this.mapper.updateById(po);

        return po.getId();
    }

    @Override
    public void del(String menuId, String operUserId, Date operTime) throws Exception {
        Menu po = this.mapper.selectById(menuId);

        if (po == null){
            return;
        }

        this.mapper.deleteById(menuId);
        this.delHelper.recordDelData(po, operUserId, operTime);
    }

    // 接口查询(只读事务)

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(没有事务)
    private Boolean isExistsByNo(String no){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.NO, no);
        Integer count = this.mapper.selectCount(queryWrapper);

        return count >= 1 ? true : false;
    }

    private String calcPath(String parentMenuPath, String id){
        StringBuilder sb = new StringBuilder();
        return sb.append(parentMenuPath).append("/").append(id).toString();
    }

    // 注入
    @Autowired
    private LogicDelHelper delHelper;
    @Override
    @Autowired
    public void setMapper(MenuMapper mapper){
        this.mapper = mapper;
    }
}
