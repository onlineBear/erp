package org.anson.miniProject.mapper.sys.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}
