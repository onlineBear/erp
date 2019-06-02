package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tMenuLang")
public class MenuLang {
    private String id;

    private String menuId;
    public static final String MENUID = "menuId";

    private String langId;
    public static final String LANGID = "langId";

    private String menuName;

    private String menuDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
