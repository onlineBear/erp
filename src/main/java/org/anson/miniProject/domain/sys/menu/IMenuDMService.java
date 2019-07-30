package org.anson.miniProject.domain.sys.menu;

import org.anson.miniProject.domain.sys.menu.cmd.AddMenuCMD;
import org.anson.miniProject.domain.sys.menu.cmd.UpdMenuCMD;

public interface IMenuDMService {
    String addMenu(AddMenuCMD cmd) throws Exception;
    void updateMenu(UpdMenuCMD cmd) throws Exception;
}
