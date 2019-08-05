package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
abstract class AddDictTypeCMDConverter {
    public abstract DictType toDictType(AddDictTypeCMD cmd);

    public List<Dict> toDictList(AddDictTypeCMD cmd){
        if (cmd == null || CollUtil.isEmpty(cmd.getDictList())){
            return null;
        }

        return toDictList(cmd.getDictList());
    }

    protected abstract Dict toDict(AddDictTypeCMD.AddDictCMD addDictCMD);

    protected abstract List<Dict> toDictList(List<AddDictTypeCMD.AddDictCMD> addDictCMDList);
}
