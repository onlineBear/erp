package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

import java.util.List;

class AddCMDTranslator {
    public static DictType toDictType(AddDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        DictType dictType = BeanUtils.beanToBean(cmd, DictType.class);

        return dictType;
    }

    public static List<Dict> toDictList(AddDictTypeCMD cmd) throws InstantiationException, IllegalAccessException{
        if (CollUtil.isEmpty(cmd.getDictList())){
            return null;
        }

        return BeanUtils.beansToBeans(cmd.getDictList(), Dict.class);
    }
}
