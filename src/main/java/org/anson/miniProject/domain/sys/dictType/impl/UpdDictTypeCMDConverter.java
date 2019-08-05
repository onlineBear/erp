package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
abstract class UpdDictTypeCMDConverter {
    abstract DictType toDictType(UpdDictTypeCMD cmd);

    public List<Dict> toAddDictList(UpdDictTypeCMD cmd){
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getAddDictList())){
            return null;
        }

        return toAddDictList(cmd.getDict().getAddDictList());
    }

    protected abstract Dict toAddDict(UpdDictTypeCMD.AddDictCMD addDictCMD);
    protected abstract List<Dict> toAddDictList(List<UpdDictTypeCMD.AddDictCMD> addDictCMDList);

    public List<Dict> toUpdDictList(UpdDictTypeCMD cmd){
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getUpdDictList())){
            return null;
        }

        return toUpdDictList(cmd.getDict().getUpdDictList());
    }

    protected abstract Dict toUpdDict(UpdDictTypeCMD.UpdDict updDict);
    protected abstract List<Dict> toUpdDictList(List<UpdDictTypeCMD.UpdDict> updDictList);
}
