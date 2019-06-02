package org.zmqy.erp.mapper.mis.pc.unauthorized;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UnAuthorizedMapper {
    public List<Map> getLanguageList();

    public List<Map> getStoreList(@Param("langNo") String langNo, @Param("userNo") String userNo);

    public Map loginSuccess(@Param("userNo") String userNo, @Param("langId") String langId,
                            @Param("storeId") String storeId, @Param("token") String token);

    String getUserIdByNo(@Param("userNo") String userNo);
}
