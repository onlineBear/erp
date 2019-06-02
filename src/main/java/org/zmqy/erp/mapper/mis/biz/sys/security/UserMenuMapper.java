package org.zmqy.erp.mapper.mis.biz.sys.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.security.UserMenu;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMenuMapper extends BaseMapper<UserMenu> {
    public List<Map> geRecentUserMenu();
    public void batchInsert(List<UserMenu> userMenuList);
}
