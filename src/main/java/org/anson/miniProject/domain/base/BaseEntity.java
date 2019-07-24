package org.anson.miniProject.domain.base;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseEntity {
    private String id;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;
}
