package org.anson.miniProject.core.model.dmo.sys;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DictTypeBo {
    private String id;
    private String no;
    private String name;
    private String description;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private List<DictBo> dictBoList;

    private static final BeanCopier entity2boCopier = BeanCopier.create(DictType.class, DictTypeBo.class, false);
    private static final BeanCopier bo2entityCopier = BeanCopier.create(DictTypeBo.class, DictType.class, false);

    public static DictTypeBo entity2bo(DictType entity){
        if(entity == null){
            return null;
        }

        DictTypeBo bo = new DictTypeBo();

        entity2boCopier.copy(entity, bo, null);

        return bo;
    }

    public static List<DictTypeBo> entity2bo(List<DictType> entityList){
        if(IterUtil.isEmpty(entityList)){
            return null;
        }

        List<DictTypeBo> boList = new ArrayList<>();

        for(DictType entity : entityList){
            boList.add(entity2bo(entity));
        }

        return boList;
    }

    public static DictType bo2entity(DictTypeBo bo){
        if(bo == null){
            return null;
        }

        DictType entity = new DictType();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<DictType> bo2entity(List<DictTypeBo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<DictType> entityList = new ArrayList<>();

        for(DictTypeBo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
