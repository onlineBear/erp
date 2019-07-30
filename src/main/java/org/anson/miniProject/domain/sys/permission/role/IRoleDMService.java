package org.anson.miniProject.domain.sys.permission.role;

import org.anson.miniProject.domain.sys.permission.role.cmd.AddRoleCMD;
import org.anson.miniProject.domain.sys.permission.role.cmd.UpdRoleCMD;

public interface IRoleDMService {
    String addRole(AddRoleCMD cmd) throws Exception;
    void updateRole(UpdRoleCMD cmd) throws Exception;
    void deleteRole(String id) throws Exception;
}
