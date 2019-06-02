package org.zmqy.erp.model.mis.entity.base.store;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tStoreLang")
public class StoreLang {
    private String id;

    private String storeId;

    private String langId;

    private String storeName;

    private String storeShortName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
