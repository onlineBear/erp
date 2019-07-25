package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

class AddCMDTranslator {
    private static final BeanCopier toDictTypeEntityCopier = BeanCopier.create(AddDictTypeCMD.class, DictTypeEntity.class, false);
    private static final BeanCopier toDictEntityCopier = BeanCopier.create(AddDictTypeCMD.Dict.class, DictEntity.class, false);

    public static DictTypeEntity toDictTypeEntity(AddDictTypeCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        DictTypeEntity entity = BeanHelper.beanToBean(cmd, DictTypeEntity.class, toDictTypeEntityCopier);

        if (CollUtil.isNotEmpty(cmd.getDictList())){
            List<DictEntity> dictEntityList = new ArrayList<>();
            for (AddDictTypeCMD.Dict dict : cmd.getDictList()){
                dictEntityList.add(BeanHelper.beanToBean(dict, DictEntity.class, toDictEntityCopier));
            }
            entity.setDictList(dictEntityList);
        }

        return entity;
    }
}
