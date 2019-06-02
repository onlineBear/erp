package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tRegion")
public class Region {
    private String id;

    private String regionNo;
    public static final String REGIONNO = "regionNo";

    private String upRegionId;

    private Integer regionLevel;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
