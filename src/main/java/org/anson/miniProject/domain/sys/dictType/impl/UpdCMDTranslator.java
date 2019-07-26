package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

class UpdCMDTranslator {
    private static final BeanCopier toDictTypeCopier = BeanCopier.create(UpdDictTypeCMD.class, DictType.class, false);
    private static final BeanCopier toDictCopier = BeanCopier.create(AddDictTypeCMD.Dict.class, Dict.class, false);
    private static final BeanCopier updToDictCopier = BeanCopier.create(UpdDictTypeCMD.UpdDict.class, Dict.class, false);

    public static DictType toDictType(UpdDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        DictType po = BeanHelper.beanToBean(cmd, DictType.class, toDictTypeCopier);

        return po;
    }

    public static List<Dict> toSaveDictList(UpdDictTypeCMD cmd) throws InstantiationException, IllegalAccessException{
        if (cmd == null || cmd.getDict() != null || CollUtil.isEmpty(cmd.getDict().getAddDictList())){
            return null;
        }

        return BeanHelper.beansToBeans(cmd.getDict().getAddDictList(), Dict.class, toDictCopier);
    }

    public static List<Dict> toUpdDictList(UpdDictTypeCMD cmd) throws IllegalAccessException, InstantiationException {
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getUpdDictList())){
            return null;
        }

        return BeanHelper.beansToBeans(cmd.getDict().getUpdDictList(), Dict.class, updToDictCopier);
    }
}
