package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.anson.miniProject.domain.base.BaseEntity;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

@Data
class DictEntity extends BaseEntity {
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toDictCopier = BeanCopier.create(DictEntity.class, Dict.class, false);

    public static Dict toDict(DictEntity entity) throws InstantiationException, IllegalAccessException {
        if (entity == null){
            return null;
        }

        Dict po = BeanHelper.beanToBean(entity, Dict.class, toDictCopier);

        return po;
    }

    public static List<Dict> toDict(List<DictEntity> entityList) throws InstantiationException, IllegalAccessException {
        if (CollUtil.isEmpty(entityList)){
            return null;
        }

        List<Dict> poList = new ArrayList<>();

        for (DictEntity entity : entityList){
            poList.add(toDict(entity));
        }

        return poList;
    }
}
