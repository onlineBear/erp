package org.anson.miniProject.core.model.vo;

import lombok.Data;

@Data
public class AddDictTypeVo {
    private String id;

    public AddDictTypeVo(String id){
        this.id = id;
    }
}
