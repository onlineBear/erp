package org.zmqy.erp.mapper.mis.biz.sys.columnInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.UserColumn;

import java.util.List;
import java.util.Map;

@Repository
public interface UserColumnMapper extends BaseMapper<UserColumn> {
    /**
     * 获取用户列必填项
     *
     * @param langId     语言id
     * @param userId     用户id
     * @param menuNo     菜单编码
     * @param pageTypeNo 页面类型id
     * @return
     */
    List<Map<String, String>> getUserRequiredColumn(@Param("langId") String langId, @Param("userId") String userId,
                                                    @Param("menuNo") String menuNo, @Param("pageTypeNo") String pageTypeNo);

    void resetById(@Param("id") String id);
}
