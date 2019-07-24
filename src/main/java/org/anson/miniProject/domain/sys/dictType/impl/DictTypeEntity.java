package org.anson.miniProject.domain.sys.dictType.impl;

import lombok.Data;
import org.anson.miniProject.domain.base.BaseEntity;

import java.util.List;

@Data
class DictTypeEntity extends BaseEntity {
    private String no;
    private String name;
    private String description;

    private List<DictEntity> dictList;
}
