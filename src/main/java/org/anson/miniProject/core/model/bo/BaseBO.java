package org.anson.miniProject.core.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseBO {
    private String id;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;
}
