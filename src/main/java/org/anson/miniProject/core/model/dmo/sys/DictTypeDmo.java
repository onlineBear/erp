package org.anson.miniProject.core.model.dmo.sys;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DictTypeDmo {
    private String id;
    private String no;
    private String name;
    private String description;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private List<DictDmo> dictDmoList;

    private static final BeanCopier entity2boCopier = BeanCopier.create(DictType.class, DictTypeDmo.class, false);
    private static final BeanCopier bo2entityCopier = BeanCopier.create(DictTypeDmo.class, DictType.class, false);

    public static DictTypeDmo entity2bo(DictType entity){
        if(entity == null){
            return null;
        }

        DictTypeDmo bo = new DictTypeDmo();

        entity2boCopier.copy(entity, bo, null);

        return bo;
    }

    public static List<DictTypeDmo> entity2bo(List<DictType> entityList){
        if(IterUtil.isEmpty(entityList)){
            return null;
        }

        List<DictTypeDmo> boList = new ArrayList<>();

        for(DictType entity : entityList){
            boList.add(entity2bo(entity));
        }

        return boList;
    }

    public static DictType bo2entity(DictTypeDmo bo){
        if(bo == null){
            return null;
        }

        DictType entity = new DictType();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<DictType> bo2entity(List<DictTypeDmo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<DictType> entityList = new ArrayList<>();

        for(DictTypeDmo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
