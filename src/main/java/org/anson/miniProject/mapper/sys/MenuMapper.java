package org.anson.miniProject.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.anson.miniProject.model.entity.sys.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    Integer updateChildByParent(@Param("parentMenuIdList") List<String> parentMenuIdList,
                                @Param("operTime") Date operTime);
}