package org.anson.miniProject.core.model.dto.service.sys.dictType;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UpdDictTypeDTO extends BaseDTO {
    @NotEmpty(message = "请输入数据字典类型id")
    private String id;
    private String name;
    private String description;

    @Valid
    private UpdDictTypeDTO.DictDTO dict;

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
    public static class DictDTO{
        private List<AddDictDTO> addDictList;
        @Valid
        private List<UpdDictDTO> updDictList;
        private List<String> delDictList;
    }

    @Data
    public static class AddDictDTO{
        @NotEmpty(message = "请输入数据字典编码")
        private String no;
        @NotEmpty(message = "请输入数据字典名称")
        private String name;
        private String description;
    }

    @Data
    public static class UpdDictDTO {
        @NotBlank
        private String id;
        private String name;
        private String description;
    }

    public static UpdDictTypeCMD toUpdDictTypeCMD(UpdDictTypeDTO dto) throws InstantiationException, IllegalAccessException {
        UpdDictTypeCMD cmd = BeanUtils.beanToBean(dto, UpdDictTypeCMD.class);

        UpdDictTypeDTO.DictDTO dictDTO = dto.getDict();

        if (dictDTO != null && CollUtil.isNotEmpty(dictDTO.getAddDictList())){
            List<UpdDictTypeCMD.AddDictCMD> addDictCMDList = BeanUtils.beansToBeans(dto.getDict().getAddDictList(), UpdDictTypeCMD.AddDictCMD.class);

            UpdDictTypeCMD.Dict dict = cmd.getDict();
            if (dict == null){
                cmd.setDict(new UpdDictTypeCMD.Dict());
            }

            dict = cmd.getDict();
            dict.setAddDictList(addDictCMDList);
        }

        if (dictDTO != null && CollUtil.isNotEmpty(dictDTO.getUpdDictList())){
            List<UpdDictTypeCMD.UpdDict> updDictList = BeanUtils.beansToBeans(dictDTO.getUpdDictList(), UpdDictTypeCMD.UpdDict.class);

            UpdDictTypeCMD.Dict dict = cmd.getDict();
            if (dict == null){
                cmd.setDict(new UpdDictTypeCMD.Dict());
            }

            dict = cmd.getDict();
            dict.setUpdDictList(updDictList);
        }

        return cmd;
    }
}
