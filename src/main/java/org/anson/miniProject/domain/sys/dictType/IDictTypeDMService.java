package org.anson.miniProject.domain.sys.dictType;

import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IDictTypeDMService {
    String addDictType(@Valid AddDictTypeCMD cmd) throws Exception;
    void updateDictType(@Valid UpdDictTypeCMD cmd) throws Exception;
    void delDictType(String id) throws Exception;
}
