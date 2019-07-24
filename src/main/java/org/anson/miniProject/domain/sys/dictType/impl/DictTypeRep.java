package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.tool.helper.CollHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeRep {
    public String insert(AddDictTypeCMD cmd) throws Exception{
        // 数据字典类型
        DictType dictType = AddCMDTranslator.toDictType(cmd);
        String id = this.dao.insert(dictType);

        // 数据字典
        List<Dict> dictList = AddCMDTranslator.toDictList(cmd);
        if (IterUtil.isNotEmpty(dictList)){
            for (Dict dict : dictList){
                dict.setDictTypeId(id); // 关联关系
                this.dictDao.insert(dict);
            }
        }

        return id;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean isExistsByNo(String dictTypeNo){
        return this.dao.isExistsByNo(dictTypeNo);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<String> isExistsByDictNo(String dictTypeId, List<String> dictNoList){
        // dictNoList 值重复
        List<String> repeatedDictNoList = CollHelper.findRepeatedVal(dictNoList);

        if (IterUtil.isNotEmpty(repeatedDictNoList)){
            return repeatedDictNoList;
        }

        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq(Dict.DICTTYPEID, dictTypeId)
          .in(Dict.NO, dictNoList)
          .select(Dict.NO, Dict.ID);

        List<Dict> rsList = this.dictDao.selectList(qw);

        if (IterUtil.isEmpty(rsList)){
            return null;
        }

        List<String> repeadDictNoList = new ArrayList<>();

        for (Dict rs : rsList){
            repeadDictNoList.add(rs.getNo());
        }

        return repeadDictNoList;
    }

    @Autowired
    private DictTypeDao dao;
    @Autowired
    private DictDao dictDao;
}
