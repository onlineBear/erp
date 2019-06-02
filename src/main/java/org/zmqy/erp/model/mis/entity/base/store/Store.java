package org.zmqy.erp.model.mis.entity.base.store;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tStore")
public class Store {
    private String id;
    public static final String ID = "id";

    private String storeNo;
    public static final String STORENO = "storeNo";

    private String storeTypeNo;

    private Boolean areSettleUnit;

    private String settleStoreNo;

    private String pricePlanNo;

    private String longitude;

    private String latitude;

    private Boolean areAutoClearStockEachDay;

    private Date lastClearDate;

    private Boolean areFranchise;

    private Double franchiseSettleRate;

    private String franchiseTypeId;

    private String franchiseSettleTypeId;

    private String varietyGroupNo;

    private String priceGroupNo;

    private String contractGroupNo;

    private String orderSchedule;

    private String boss;

    private String tel;

    private String mobile;

    private String wechat;

    private String memo1;

    private String memo2;

    private String memo3;

    private String nationNo;

    private String provinceNo;

    private String cityNo;

    private String address;

    private String postalcode;

    private String email;

    private Date openDate;

    private String openTime;

    private String closetime;

    private Long area;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
