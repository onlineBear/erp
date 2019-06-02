package org.zmqy.erp.model.mis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tCheckBill")
public class CheckBill {
    private String id;

    private String billId;
    public static final String BILLID = "billId";

    private String flowId;
    public static final String FLOWID = "flowId";

    private Integer curentLevel;
    public static final String CURENTLEVEL = "curentLevel";

    private String flowDtlId;

    private String curentCheckUserId;

    private Date curentCheckTime;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

}