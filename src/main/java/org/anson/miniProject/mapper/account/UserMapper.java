package org.anson.miniProject.mapper.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.model.entity.account.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
