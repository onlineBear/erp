package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tCommon")
public class Common {
    private String id;
    public static final String ID = "id";

    private String comNo;
    public static final String COMNO = "comNo";

    private String comTypeId;
    public static final String COMTYPEID = "comTypeId";

    private Double num1;

    private String text1;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
