package org.anson.miniProject.domain.sys.dictType.cmd;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AddDictTypeCMD{
    @NotBlank
    private String no;
    private String name;
    private String description;

    @Valid
    private List<Dict> dictList;

    @Data
    public class Dict{
        @NotBlank
        private String no;
        private String name;
        private String description;
    }
}
