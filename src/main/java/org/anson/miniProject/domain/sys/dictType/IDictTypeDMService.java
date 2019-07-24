package org.anson.miniProject.domain.sys.dictType;

import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;

public interface IDictTypeDMService {
    String addDictType(AddDictTypeCMD cmd) throws Exception;
}
