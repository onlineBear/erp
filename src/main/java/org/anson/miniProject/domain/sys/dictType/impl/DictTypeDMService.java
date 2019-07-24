package org.anson.miniProject.domain.sys.dictType.impl;

import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeDMService implements IDictTypeDMService {
    // 接口方法
    @Override
    public String addDictType(AddDictTypeCMD cmd) throws Exception {
        // 由 cmd 初始化 dictTypeEntity
        DictTypeEntity dictTypeEntity = new DictTypeEntity();

        // 存储
        String id = this.rep.insert(dictTypeEntity);
        return id;
    }

    // 非接口方法

    // 注入
    @Autowired
    private DictTypeRep rep;
}
