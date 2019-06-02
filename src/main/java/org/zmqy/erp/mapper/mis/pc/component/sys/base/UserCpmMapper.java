package org.zmqy.erp.mapper.mis.pc.component.sys.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCpmMapper {
    public List<String> getUserResourceUrlList(@Param("userId")String userId);
}
