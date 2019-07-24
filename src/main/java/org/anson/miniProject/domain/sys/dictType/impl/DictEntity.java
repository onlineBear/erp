package org.anson.miniProject.domain.sys.dictType.impl;

import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.domain.base.BaseEntity;

@Data
@Builder
class DictEntity extends BaseEntity {
    private String no;
    private String name;
    private String dictKey;
    private String description;
}
