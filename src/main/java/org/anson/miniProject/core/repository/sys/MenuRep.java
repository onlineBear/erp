package org.anson.miniProject.core.repository.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.core.mapper.sys.MenuMapper;
import org.anson.miniProject.core.model.dmo.sys.MenuDmo;
import org.anson.miniProject.core.model.po.sys.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class MenuRep {
    @Autowired
    private MenuMapper mapper;

    public MenuDmo getMenu(String menuId){
        Menu menu = this.mapper.selectById(menuId);

        if(menu == null){
            throw new RuntimeException("没有这个菜单, 菜单id=" + menuId);
        }

        return MenuDmo.entity2bo(menu);
    }

    public String addMenu(MenuDmo bo, String operUserId, Date operTime){
        // 检查编码
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.NO, bo.getNo());
        Integer num = this.mapper.selectCount(queryWrapper);

        if(num != 0){
            throw new RuntimeException("菜单编码重复, 菜单编码 = " + bo.getNo());
        }

        // 新增节点本身
        Menu menu = MenuDmo.bo2entity(bo);
        menu.setCreateTime(operTime);
        menu.setCreateUserId(operUserId);
        menu.setLastUpdateTime(operTime);

        this.mapper.insert(menu);

        // 新增子节点
        if(IterUtil.isNotEmpty(bo.getChildMenuDmoList())){
            for(MenuDmo one : bo.getChildMenuDmoList()){
                this.addMenu(one, operUserId, operTime);
            }
        }

        return menu.getId();
    }

    public String mdfMenu(MenuDmo bo, String operUserId, Date operTime){
        Menu menu = MenuDmo.bo2entity(bo);

        // 查询修改前的菜单
        Menu oldMenu = this.mapper.selectById(bo.getId());

        if(oldMenu == null){
            throw new RuntimeException("没有这个菜单, 菜单id = " + bo.getId());
        }

        // 检查编码
        if(StrUtil.isNotEmpty(bo.getNo()) && !bo.getNo().equals(oldMenu.getNo())){
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(Menu.NO, bo.getNo())
                        .le(Menu.ID, bo.getId());
            Integer num = this.mapper.selectCount(queryWrapper);

            if(num != 0){
                throw new RuntimeException("菜单编码重复, 菜单编码 = " + bo.getNo());
            }
        }

        // 若修改了父级菜单
        if(bo.getParentMenuDmo() != null && StrUtil.isNotEmpty(bo.getParentMenuDmo().getId())
                && !bo.getParentMenuDmo().getId().equals(oldMenu.getParentMenuId())){
                // 修改 子菜单的 clientDictId 和 path
                List<String> parentMenuIdList = new ArrayList<>();
                parentMenuIdList.add(menu.getId());
                this.mapper.updateChildByParent(parentMenuIdList, operTime);
        }

        menu.setLastUpdateTime(operTime);
        this.mapper.updateById(menu);

        // 修改子菜单
        if(IterUtil.isNotEmpty(bo.getChildMenuDmoList())){
            for(MenuDmo one : bo.getChildMenuDmoList()){
                this.saveMenu(one, operUserId, operTime);
            }
        }

        return bo.getId();
    }

    public String saveMenu(MenuDmo bo, String operUserId, Date operTime){
        if(StrUtil.isNotEmpty(bo.getId())){
            return this.addMenu(bo, operUserId, operTime);
        }else{
            return this.mdfMenu(bo, operUserId, operTime);
        }
    }

    public void delMenu(String menuId){
        Menu menu = this.mapper.selectById(menuId);

        if(menu == null){
            return;
        }
        // 删除本节点
        //this.mapper.deleteById(menuId);
        // 删除子节点
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight(Menu.PATH, menu.getPath());
        this.mapper.delete(queryWrapper);
    }

    public void delMenu(String[] menuIdArray){
        if(ArrayUtil.isNotEmpty(menuIdArray)){
            for(int i=0;i<menuIdArray.length;i++){
                if(StrUtil.isNotEmpty(menuIdArray[i])){
                    this.delMenu(menuIdArray[i]);
                }
            }
        }

    }
}
