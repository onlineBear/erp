package org.anson.miniProject.domain.sys.dictType.cmd;

import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ToString(callSuper = true)
public class UpdDictTypeCMD {
    @NotBlank
    private String id;
    private String name;
    private String description;

    @Valid
    private Dict dict;

    @Data
    public class Dict{
        private List<AddDictTypeCMD.AddDictCMD> addDictList;
        @Valid
        private List<UpdDict> updDictList;
        private List<String> delDictList;
    }

    @Data
    public class UpdDict{
        @NotBlank
        private String id;
        private String name;
        private String description;
    }
}
