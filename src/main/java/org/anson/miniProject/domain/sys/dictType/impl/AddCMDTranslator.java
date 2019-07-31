package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

class AddCMDTranslator {
    private static final BeanCopier toDictTypeCopier = BeanCopier.create(AddDictTypeCMD.class, DictType.class, false);
    private static final BeanCopier toDictCopier = BeanCopier.create(AddDictTypeCMD.Dict.class, Dict.class, false);

    public static DictType toDictType(AddDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        DictType po = new DictType();
        toDictTypeCopier.copy(cmd, po, null);

        return po;
    }

    public static List<Dict> toDictList(AddDictTypeCMD cmd) throws InstantiationException, IllegalAccessException{
        if (cmd == null || CollUtil.isEmpty(cmd.getDictList())){
            return null;
        }

        List<Dict> dictList = new ArrayList<>();

        for (AddDictTypeCMD.Dict one : cmd.getDictList()){
            Dict dict = new Dict();
            toDictCopier.copy(one, dict, null);
            dictList.add(dict);
        }

        return dictList;
    }
}
