package org.anson.miniProject.core.model.dto.service.sys.dictType;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.core.model.param.sys.base.dict.AddDictParam;
import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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

    private static final BeanCopier toParamCopier = BeanCopier.create(AddDictTypeDTO.class, AddDictTypeParam.class, false);

    public AddDictTypeParam toParam() throws InstantiationException, IllegalAccessException {
        AddDictTypeParam param = BeanHelper.beanToBean(this, AddDictTypeParam.class, toParamCopier);

        param.setDictList(AddDictDTO.toParam(this.dictList));

        return param;
    }
}

@Data
class AddDictDTO{
    @NotEmpty(message = "请输入数据字典编码")
    private String no;
    @NotEmpty(message = "请输入数据字典名称")
    private String name;
    private String description;

    private static final BeanCopier toParamCopier = BeanCopier.create(AddDictDTO.class, AddDictParam.class, false);

    public AddDictParam toParam() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, AddDictParam.class, toParamCopier);
    }

    public static List<AddDictParam> toParam(List<AddDictDTO> dtoList) throws InstantiationException, IllegalAccessException {
        if (IterUtil.isEmpty(dtoList)){
            return null;
        }

        List<AddDictParam> paramList = new ArrayList<>();

        for (AddDictDTO dto : dtoList){
            paramList.add(dto.toParam());
        }

        return paramList;
    }
}
