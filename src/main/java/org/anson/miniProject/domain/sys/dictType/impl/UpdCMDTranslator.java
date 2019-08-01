package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

import java.util.List;

class UpdCMDTranslator {
    public static DictType toDictType(UpdDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        return BeanUtils.beanToBean(cmd, DictType.class);
    }

    public static List<Dict> toAddDictList(UpdDictTypeCMD cmd) throws InstantiationException, IllegalAccessException{
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getAddDictList())){
            return null;
        }

        return BeanUtils.beansToBeans(cmd.getDict().getAddDictList(), Dict.class);
    }

    public static List<Dict> toUpdDictList(UpdDictTypeCMD cmd) throws IllegalAccessException, InstantiationException {
        if (cmd == null || cmd.getDict() == null || CollUtil.isEmpty(cmd.getDict().getUpdDictList())){
            return null;
        }

        return BeanUtils.beansToBeans(cmd.getDict().getUpdDictList(), Dict.class);
    }
}
