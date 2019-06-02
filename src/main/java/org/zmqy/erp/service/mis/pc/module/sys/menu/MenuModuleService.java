package org.zmqy.erp.service.mis.pc.module.sys.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.security.IResourceDomain;
import org.zmqy.erp.mapper.mis.pc.module.sys.menu.MenuModuleMapper;
import org.zmqy.erp.model.mis.bo.sys.base.MenuBo;
import org.zmqy.erp.model.mis.bo.sys.base.MenuLangBo;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceBo;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceLangBo;

import java.util.*;

@Service
@Slf4j
public class MenuModuleService {

    @Autowired
    private MenuModuleMapper mapper;
    @Autowired
    private IMenuDomain domain;
    @Autowired
    private IResourceDomain resourceDomain;

    public List<Map> getMenuList(String langId){
        return this.mapper.getMenuList(langId);
    }

    public List<Map> getResourceByMenu(String langId, String menuId){
        return this.mapper.getResourceByMenu(langId, menuId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> addMenu(Map<String, Object> paramsMap) throws Exception{
        String menuNo = (String)paramsMap.get("menuNo");
        String parentMenuId = (String)paramsMap.get("parentMenuId");
        String iconUrl = (String)paramsMap.get("iconUrl");
        String menuClientNo = (String)paramsMap.get("menuClientNo");
        Boolean areCatalog = (Boolean)paramsMap.get("areCatalog");
        Integer seq = (Integer)paramsMap.get("seq");
        Boolean areHqDisplay = (Boolean)paramsMap.get("areHqDisplay");
        Boolean areShopDisplay = (Boolean)paramsMap.get("areShopDisplay");
        Boolean areDcDisplay = (Boolean)paramsMap.get("areDcDisplay");

        String menuName = (String)paramsMap.get("menuName");
        String menuDesc = (String)paramsMap.get("menuDesc");

        String langId = (String)paramsMap.get("loginLangId");
        String operUserId = (String)paramsMap.get("loginUserId");

        Date operTime = new Date();

        MenuBo menuBo = new MenuBo();
        menuBo.setMenuNo(menuNo);
        menuBo.setParentMenuId(parentMenuId);
        menuBo.setIconUrl(iconUrl);
        menuBo.setMenuClientNo(menuClientNo);
        menuBo.setAreCatalog(areCatalog);
        menuBo.setSeq(seq);
        menuBo.setAreHqDisplay(areHqDisplay);
        menuBo.setAreShopDisplay(areShopDisplay);
        menuBo.setAreDcDisplay(areDcDisplay);

        // 菜单国际化信息
        MenuLangBo menuLangBo = new MenuLangBo();
        menuLangBo.setLangId(langId);
        menuLangBo.setMenuName(menuName);
        menuLangBo.setMenuDesc(menuDesc);

        List<MenuLangBo> menuLangBoList = new ArrayList<>();
        menuLangBoList.add(menuLangBo);

        menuBo.setMenuLangBoList(menuLangBoList);

        // 菜单资源
        List<Map> resList = (List<Map>) paramsMap.get("resList");
        List<ResourceBo> resourceBoList = new ArrayList<>();

        List<ResourceLangBo> resourceLangBoList = new ArrayList<>();

        if(resList != null && resList.size() > 0){
            for(Map res : resList){
                String resUrl = (String)res.get("resUrl");
                String resOperNo = (String)res.get("resOperNo");
                String resName = (String)res.get("resName");
                String resDesc = (String)res.get("resDesc");

                ResourceBo resourceBo = new ResourceBo();
                resourceBo.setResUrl(resUrl);
                resourceBo.setResOperNo(resOperNo);

                ResourceLangBo resourceLangBo = new ResourceLangBo();
                resourceLangBo.setLangId(langId);
                resourceLangBo.setResName(resName);
                resourceLangBo.setResDesc(resDesc);

                resourceLangBoList.add(resourceLangBo);

                resourceBo.setResourceLangBoList(resourceLangBoList);
                resourceBoList.add(resourceBo);
            }
        }

        menuBo.setResourceBoList(resourceBoList);

        Map<String, String> rsMap = new HashMap<>();
        rsMap.put("menuId", this.domain.add(menuBo, operUserId, operTime));

        return rsMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfMenu(Map<String, Object> paramsMap) throws Exception{
        String langId = (String)paramsMap.get("loginLangId");
        String operUserId = (String)paramsMap.get("loginUserId");

        String menuId = (String)paramsMap.get("menuId");
        String menuNo = (String)paramsMap.get("menuNo");
        String parentMenuId = (String)paramsMap.get("parentMenuId");
        String iconUrl = (String)paramsMap.get("iconUrl");
        String menuClientNo = (String)paramsMap.get("menuClientNo");
        Boolean areCatalog = (Boolean)paramsMap.get("areCatalog");
        Integer seq = (Integer)paramsMap.get("seq");
        Boolean areHqDisplay = (Boolean)paramsMap.get("areHqDisplay");
        Boolean areShopDisplay = (Boolean)paramsMap.get("areShopDisplay");
        Boolean areDcDisplay = (Boolean)paramsMap.get("areDcDisplay");

        String menuName = (String)paramsMap.get("menuName");
        String menuDesc = (String)paramsMap.get("menuDesc");

        Date operTime = new Date();

        // 修改菜单
        MenuBo menuBo = new MenuBo();
        menuBo.setId(menuId);
        menuBo.setMenuNo(menuNo);
        menuBo.setParentMenuId(parentMenuId);
        menuBo.setIconUrl(iconUrl);
        menuBo.setMenuClientNo(menuClientNo);
        menuBo.setAreCatalog(areCatalog);
        menuBo.setSeq(seq);
        menuBo.setAreHqDisplay(areHqDisplay);
        menuBo.setAreShopDisplay(areShopDisplay);
        menuBo.setAreDcDisplay(areDcDisplay);

        MenuLangBo menuLangBo = new MenuLangBo();
        menuLangBo.setLangId(langId);
        menuLangBo.setMenuName(menuName);
        menuLangBo.setMenuDesc(menuDesc);

        List<MenuLangBo> menuLangBoList = new ArrayList<>();
        menuLangBoList.add(menuLangBo);

        menuBo.setMenuLangBoList(menuLangBoList);

        // 新增/修改资源
        List<Map> resList = (List<Map>) paramsMap.get("resList");
        List<ResourceBo> resourceBoList = new ArrayList<>();

        if(resList != null && resList.size() > 0){
            for(Map res : resList){
                String resId = (String)res.get("resId");
                String resUrl = (String)res.get("resUrl");
                String resOperNo = (String)res.get("resOperNo");
                String resName = (String)res.get("resName");
                String resDesc = (String)res.get("resDesc");

                ResourceBo resourceBo = new ResourceBo();
                resourceBo.setId(resId);
                resourceBo.setMenuId(menuId);
                resourceBo.setResUrl(resUrl);
                resourceBo.setResOperNo(resOperNo);

                ResourceLangBo resourceLangBo = new ResourceLangBo();
                resourceLangBo.setLangId(langId);
                resourceLangBo.setResName(resName);
                resourceLangBo.setResDesc(resDesc);

                List<ResourceLangBo> resourceLangBoList = new ArrayList<>();
                resourceLangBoList.add(resourceLangBo);

                resourceBo.setResourceLangBoList(resourceLangBoList);
                resourceBoList.add(resourceBo);
            }
        }

        menuBo.setResourceBoList(resourceBoList);

        // 删除资源
        List<String> delResIdList = (List<String>)paramsMap.get("delResIdList");

        if(delResIdList != null && delResIdList.size() > 0){
            this.resourceDomain.delByUrl(delResIdList);
        }

        this.domain.mdfById(menuBo, operUserId, operTime);
    }

    @Transactional
    public void mdfMenuSeq(String menuId, String parentMenuId, String frontMenuId, String behindMenuId,
                           String operUserId) throws Exception{
        Date operTime = new Date();
        this.domain.mdfMenuSeq(menuId, parentMenuId, frontMenuId, behindMenuId, operUserId, operTime);
    }

}
