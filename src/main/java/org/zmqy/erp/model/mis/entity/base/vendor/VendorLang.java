package org.zmqy.erp.model.mis.entity.base.vendor;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tVendorLang")
public class VendorLang {
    private String id;

    private String langId;
    public static final String LANGID = "langId";

    private String vendorId;
    public static final String VENDORID = "vendorId";

    private String vendorName;

    private String vendorShortName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
