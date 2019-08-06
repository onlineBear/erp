package org.anson.miniProject.controller.pc;

import lombok.Getter;
import org.anson.miniProject.constrant.dict.ClientEnum;

public abstract class BaseController {
    @Getter
    protected ClientEnum clientKey;
    @Getter
    protected String menuId;

    protected abstract void setClientKey();
    protected abstract void setMenuId();
}
