package org.anson.miniProject.core.model.param.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.Dict;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DictDmo {
    private String id;

    private DictTypeDmo dictTypeDmo;

    private String no;
    private String name;
    private String key;
    private String description;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private static final BeanCopier entity2boCopier = BeanCopier.create(Dict.class, DictDmo.class, false);
    private static final BeanCopier bo2entityCopier = BeanCopier.create(DictDmo.class, Dict.class, false);

    public static DictDmo entity2bo(Dict entity){
        if(entity == null){
            return null;
        }

        DictDmo bo = new DictDmo();

        entity2boCopier.copy(entity, bo, null);

        if(StrUtil.isNotEmpty(entity.getDictTypeId())){
            DictTypeDmo dictTypeDmo = new DictTypeDmo();
            dictTypeDmo.setId(entity.getDictTypeId());
            //this.setDictTypeDmo(dictTypeDmo);
        }

        return bo;
    }

    public static List<DictDmo> entity2bo(List<Dict> entityList){
        if(IterUtil.isEmpty(entityList)){
            return null;
        }

        List<DictDmo> boList = new ArrayList<>();

        for(Dict entity : entityList){
            boList.add(entity2bo(entity));
        }

        return boList;
    }

    public static Dict bo2entity(DictDmo bo){
        if(bo == null){
            return null;
        }

        Dict entity = new Dict();

        bo2entityCopier.copy(bo, entity, null);

        /*
        if(this.getDictTypeDmo() != null && this.getDictTypeDmo().getId() != null){
            entity.setDictTypeId(this.getDictTypeDmo().getId());
        }
        */

        return entity;
    }

    public static List<Dict> bo2entity(List<DictDmo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<Dict> entityList = new ArrayList<>();

        for(DictDmo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
