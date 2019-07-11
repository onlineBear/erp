package org.anson.miniProject.core.model.dto.service.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.permission.resource.AddResourceBO;
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

    private static final BeanCopier toAddResourceBOCopier = BeanCopier.create(AddResourceDTO.class, AddResourceBO.class, false);

    public static AddResourceBO toAddResourceBO(AddResourceDTO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, AddResourceBO.class, toAddResourceBOCopier);
    }
}
