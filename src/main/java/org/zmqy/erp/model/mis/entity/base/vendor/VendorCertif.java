package org.zmqy.erp.model.mis.entity.base.vendor;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tVendorCertif")
public class VendorCertif {
    private String id;
    public static final String ID = "id";

    private String vendorId;
    public static final String VENDORID = "vendorId";

    private String certifTypeNo;
    public static final String CERTIFTYPENO = "certifTypeNo";

    private String certifNo;
    public static final String CERTIFNO = "certifNo";

    private Date expirationDate;

    private String picUrl;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
