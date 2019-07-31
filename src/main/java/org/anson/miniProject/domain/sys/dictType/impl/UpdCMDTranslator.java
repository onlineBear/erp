package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

class UpdCMDTranslator {
    private static final BeanCopier toDictTypeCopier = BeanCopier.create(UpdDictTypeCMD.class, DictType.class, false);
    private static final BeanCopier toDictCopier = BeanCopier.create(AddDictTypeCMD.Dict.class, Dict.class, false);
    private static final BeanCopier updToDictCopier = BeanCopier.create(UpdDictTypeCMD.UpdDict.class, Dict.class, false);

    public static DictType toDictType(UpdDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        DictType po = new DictType();
        toDictTypeCopier.copy(cmd, po, null);

        return po;
    }

    public static List<Dict> toSaveDictList(UpdDictTypeCMD cmd) throws InstantiationException, IllegalAccessException{
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getAddDictList())){
            return null;
        }

        List<Dict> dictList = new ArrayList<>();

        for (AddDictTypeCMD.Dict one : cmd.getDict().getAddDictList()){
            Dict dict = new Dict();
            toDictCopier.copy(one, dict, null);
            dictList.add(dict);
        }

        return dictList;
    }

    public static List<Dict> toUpdDictList(UpdDictTypeCMD cmd) throws IllegalAccessException, InstantiationException {
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getUpdDictList())){
            return null;
        }

        List<Dict> dictList = new ArrayList<>();

        for (UpdDictTypeCMD.UpdDict one : cmd.getDict().getUpdDictList()){
            Dict dict = new Dict();
            updToDictCopier.copy(one, dict, null);
            dictList.add(dict);
        }

        return dictList;
    }
}
