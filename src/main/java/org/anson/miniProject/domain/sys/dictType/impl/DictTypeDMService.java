package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeDMService implements IDictTypeDMService {
    // 接口方法
    @Override
    public String addDictType(AddDictTypeCMD cmd) throws Exception {
        // 检查 cmd

        // 数据字典类型
        DictType dictType = AddCMDTranslator.toDictType(cmd);

        // 编码是否重复
        if (this.dao.isExistsByNo(dictType.getNo())){
            throw new RuntimeException(String.format("已有这个数据字典类型编码, 编码 : %s", dictType.getNo()));
        }

        // 数据字典
        // 编码是否重复
        List<Dict> dictList = AddCMDTranslator.toDictList(cmd);

        if (CollUtil.isNotEmpty(dictList)){

        }

        String dictTypeId = this.dao.insert(new DictType());
        this.dictDao.insert(null);

        return dictTypeId;
    }

    @Override
    public void updateDictType(UpdDictTypeCMD cmd) throws Exception {
        // 检查 cmd

        // 更新数据字典类型
        DictType dictType = UpdCMDTranslator.toDictType(cmd);
        this.dao.updateById(dictType);

        // 删除数据字典
        this.dictDao.deleteByDictType(dictType.getId(), cmd.getDict().getDelDictList());

        // 修改数据字典
        List<Dict> updDictList = UpdCMDTranslator.toUpdDictList(cmd);
        this.dictDao.batchUpdateById(updDictList);

        // 新增数据字典
        List<Dict> addDictList = UpdCMDTranslator.toSaveDictList(cmd);
        this.dictDao.batchInsert(addDictList);
    }

    @Override
    public void delDictType(String id) throws Exception {
        // 删除数据字典类型
        this.dao.deleteById(id);
        // 删除数据字典
        this.dictDao.deleteByDictType(id);
    }

    // 非接口方法

    // 注入
    @Autowired
    private DictTypeDao dao;
    @Autowired
    private DictDao dictDao;
}
