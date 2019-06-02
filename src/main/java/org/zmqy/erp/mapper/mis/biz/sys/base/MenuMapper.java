package org.zmqy.erp.mapper.mis.biz.sys.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;

import java.util.Date;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    public void plusSeq(@Param("parentMenuId")String parentMenuId, @Param("seq")Integer seq,
                          @Param("operTime")Date operTime);
    public void minusSeq(@Param("parentMenuId")String parentMenuId, @Param("seq")Integer seq,
                        @Param("operTime")Date operTime);
}
