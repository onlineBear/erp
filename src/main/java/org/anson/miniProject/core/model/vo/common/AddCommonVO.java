package org.anson.miniProject.core.model.vo.common;

import lombok.Data;

@Data
public class AddCommonVO {
    private String id;

    public AddCommonVO(){}

    public AddCommonVO(String id){
        this.id = id;
    }
}
