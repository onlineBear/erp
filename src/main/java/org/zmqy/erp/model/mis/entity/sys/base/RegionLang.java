package org.zmqy.erp.model.mis.entity.sys.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("tRegionLang")
public class RegionLang {
    private String id;

    private String regionId;

    private String langId;

    private String regionName;

    private String regionShortName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;
}
