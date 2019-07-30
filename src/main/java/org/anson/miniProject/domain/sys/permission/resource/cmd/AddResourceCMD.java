package org.anson.miniProject.domain.sys.permission.resource.cmd;

import lombok.Data;
import org.anson.miniProject.domain.base.BaseCMD;

@Data
public class AddResourceCMD extends BaseCMD {
    private String menuId;

    private String name;
    private String description;
    private String url;
}
