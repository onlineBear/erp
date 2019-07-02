package org.anson.miniProject.tool.helper;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdHelper {
    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static String nextSnowflakeId(){
        long id = snowflake.nextId();
        return String.valueOf(id);
    }
}
