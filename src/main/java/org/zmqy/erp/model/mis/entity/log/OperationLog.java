package org.zmqy.erp.model.mis.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tOperationLog")
public class OperationLog {
    private String id;

    private String recordId;

    private Date operTime;

    private String operUserId;

    private String ipv4;

    private String operTypeNo;

    private String operMenuNo;

    private String url;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
