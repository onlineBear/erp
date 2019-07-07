package org.anson.miniProject.core.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("example")
public class ExamplePo extends BasePo{
    private String no;

    /**
     * 表名
     */
    public static final String __TABLENAME = "example";

    public static final String NO = "no";
}
