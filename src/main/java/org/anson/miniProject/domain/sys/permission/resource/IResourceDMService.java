package org.anson.miniProject.domain.sys.permission.resource;

import org.anson.miniProject.domain.sys.permission.resource.cmd.AddResourceCMD;
import org.anson.miniProject.domain.sys.permission.resource.cmd.UpdResourceCMD;

public interface IResourceDMService {
    String addResource(AddResourceCMD cmd) throws Exception;
    void updateResource(UpdResourceCMD cmd) throws Exception;
    void deleteResource(String id) throws Exception;
}
