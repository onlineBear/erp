package org.anson.miniProject.core.model.dto.service.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceParam;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class AddResourceDTO extends BaseDTO {
    private String menuId;

    @NotEmpty(message = "请输入资源名称")
    private String name;
    private String description;
    private String url;

    // 通用参数
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    private static final BeanCopier toAddResourceParamCopier = BeanCopier.create(AddResourceDTO.class, AddResourceParam.class, false);

    public static AddResourceParam toParam(AddResourceDTO dto) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dto, AddResourceParam.class, toAddResourceParamCopier);
    }
}
