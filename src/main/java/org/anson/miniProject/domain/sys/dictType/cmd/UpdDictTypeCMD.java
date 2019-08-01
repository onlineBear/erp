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
    public static class Dict{
        private List<AddDictCMD> addDictList;
        @Valid
        private List<UpdDict> updDictList;
        private List<String> delDictList;
    }

    @Data
    public static class AddDictCMD {
        @NotBlank
        private String no;
        private String name;
        private String description;
    }

    @Data
    public static class UpdDict{
        @NotBlank
        private String id;
        private String name;
        private String description;
    }
}
