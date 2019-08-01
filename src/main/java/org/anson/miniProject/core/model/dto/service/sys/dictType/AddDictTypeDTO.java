package org.anson.miniProject.core.model.dto.service.sys.dictType;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

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

    public static AddDictTypeCMD toAddDictTypeCMD(AddDictTypeDTO dto) throws InstantiationException, IllegalAccessException {
        if (dto == null){
            return null;
        }

        AddDictTypeCMD cmd = BeanUtils.beanToBean(dto, AddDictTypeCMD.class);

        if (cmd != null && CollUtil.isNotEmpty(cmd.getDictList())){
            cmd.setDictList(BeanUtils.beansToBeans(dto.getDictList(), AddDictTypeCMD.AddDictCMD.class));
        }

        return cmd;
    }
}
