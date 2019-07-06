package org.anson.miniProject.core.model.dmo.sys;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.Dict;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DictBo {
    private String id;

    private DictTypeBo dictTypeBo;

    private String no;
    private String name;
    private String key;
    private String description;
    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private static final BeanCopier entity2boCopier = BeanCopier.create(Dict.class, DictBo.class, false);
    private static final BeanCopier bo2entityCopier = BeanCopier.create(DictBo.class, Dict.class, false);

    public static DictBo entity2bo(Dict entity){
        if(entity == null){
            return null;
        }

        DictBo bo = new DictBo();

        entity2boCopier.copy(entity, bo, null);

        if(StrUtil.isNotEmpty(entity.getDictTypeId())){
            DictTypeBo dictTypeBo = new DictTypeBo();
            dictTypeBo.setId(entity.getDictTypeId());
            bo.setDictTypeBo(dictTypeBo);
        }

        return bo;
    }

    public static List<DictBo> entity2bo(List<Dict> entityList){
        if(IterUtil.isEmpty(entityList)){
            return null;
        }

        List<DictBo> boList = new ArrayList<>();

        for(Dict entity : entityList){
            boList.add(entity2bo(entity));
        }

        return boList;
    }

    public static Dict bo2entity(DictBo bo){
        if(bo == null){
            return null;
        }

        Dict entity = new Dict();

        bo2entityCopier.copy(bo, entity, null);

        if(bo.getDictTypeBo() != null && bo.getDictTypeBo().getId() != null){
            entity.setDictTypeId(bo.getDictTypeBo().getId());
        }

        return entity;
    }

    public static List<Dict> bo2entity(List<DictBo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<Dict> entityList = new ArrayList<>();

        for(DictBo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
