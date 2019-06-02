package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tMenu")
public class Menu {
    private String id;
    public static final String ID = "id";

    private String menuNo;
    public static final String MENUNO = "menuNo";

    private String parentMenuId;
    public static final String PARENTMENUID = "parentMenuId";

    private String iconUrl;

    private String menuClientNo;

    private Boolean areCatalog;

    private Integer menuLevel;

    private Integer seq;
    public static final String SEQ = "seq";

    private Boolean areHqDisplay;

    private Boolean areShopDisplay;

    private Boolean areDcDisplay;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private String reportId;
}
