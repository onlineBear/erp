package org.anson.miniProject.core.model.service.dictType;

import lombok.Data;

@Data
public class DictTypeAddVo {
    private String id;

    public DictTypeAddVo(String id){
        this.id = id;
    }
}
