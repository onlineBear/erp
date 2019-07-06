package org.anson.miniProject.core.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("example")
public class ExamplePo extends BasePo{

    /**
     * 表名
     */
    public static final String __TABLENAME = "example";
}
