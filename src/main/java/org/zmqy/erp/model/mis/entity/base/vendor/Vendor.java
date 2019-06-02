package org.zmqy.erp.model.mis.entity.base.vendor;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tVendor")
public class Vendor {
    private String id;

    private String vendorNo;
    public static final String VENDORNO = "vendorNo";

    private String legalPerson;

    private String salesManager;

    private String tel;

    private String nationNo;

    private String provinceNo;

    private String cityNo;

    private String address;

    private String postalcode;

    private String email;

    private String vendorTypeNo;

    private String vendorNatureNo;

    private String taxTypeNo;

    private String socialCreditCode;

    private String subjectTypeNo;

    private Date foundDate;

    private String issueBankNo;

    private String issueBankAccount;

    private String zfbAccount;

    private String wechatAccount;

    private Boolean areInvoiceNotInOrder;

    private Boolean areOnlineSettle;

    private Boolean hasUpload2B2B;

    private String interfaceNo;

    private String accountSubject;

    private String settleStoreId;

    private Date enterDate;

    private Date exeuntDate;

    private String payModeNo;

    private Double registeredCapital;

    private Integer firstDay;

    private String contactPerson1;

    private String contractEmail1;

    private String contractMobile1;

    private String contractQQ1;

    private String contractWechat1;

    private String contactPerson2;

    private String contractEmail2;

    private String contractMobile2;

    private String contractQQ2;

    private String contractWechat2;

    private String contactPerson3;

    private String contractEmail3;

    private String contractMobile3;

    private String contractQQ3;

    private String contractWechat3;

    private String memo1;

    private String memo2;

    private String memo3;

    private String memo4;

    private String memo5;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
