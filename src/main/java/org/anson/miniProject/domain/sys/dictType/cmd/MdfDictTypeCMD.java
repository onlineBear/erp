package org.anson.miniProject.domain.sys.dictType.cmd;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ToString(callSuper = true)
public class MdfDictTypeCMD {
    @NotBlank
    private String id;
    private String name;
    private String description;

    private Dict dict;

    @Data
    public class Dict{
        private List<AddDict> addDictList;
        private List<MdfDict> mdfDictList;
        private List<String> delDictList;
    }

    @Data
    public class AddDict{
        @NotBlank
        private String no;
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    public class MdfDict{
        @NotBlank
        private String id;
        private String name;
        private String description;
    }
}
