package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.IterUtil;
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

        return BeanHelper.beanToBean(cmd, DictType.class, toDictTypeCopier);
    }

    public static List<Dict> toDictList(AddDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        List<AddDictTypeCMD.Dict> addDictList = cmd.getDictList();

        if (IterUtil.isEmpty(addDictList)){
            return null;
        }

        List<Dict> dictList = new ArrayList<>();

        for (AddDictTypeCMD.Dict addDict : addDictList){
            Dict dict = BeanHelper.beanToBean(addDict, Dict.class, toDictCopier);
            dictList.add(dict);
        }

        return dictList;
    }
}
