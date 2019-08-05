package org.anson.miniProject.service.sys.dictType.model;

import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddDictTypeDTO extends BaseDTO {
    @NotEmpty(message = "请输入数据字典类型编码")
    private String no;
    @NotEmpty(message = "请输入数据字典类型名称")
    private String name;
    private String description;

    @Valid
    private List<AddDictDTO> dictList;

    // 通用字段
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    @Data
    public static class AddDictDTO{
        @NotEmpty(message = "请输入数据字典编码")
        private String no;
        @NotEmpty(message = "请输入数据字典名称")
        private String name;
        private String description;
    }
}
