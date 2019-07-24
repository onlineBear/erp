package org.anson.miniProject.domain.sys.dictType.cmd;

import lombok.Data;

import java.util.List;

@Data
public class AddDictTypeCMD{
    private String no;
    private String name;
    private String description;

    private List<Dict> dictList;

    @Data
    public class Dict{
        private String no;
        private String name;
        private String description;
    }
}
