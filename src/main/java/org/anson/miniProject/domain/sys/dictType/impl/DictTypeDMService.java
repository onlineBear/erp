package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.anson.miniProject.tool.helper.POHelper;
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
        if (cmd == null){
            throw new RuntimeException("请输入新增数据字典类型命令");
        }

        List<Dict> dictList = AddCMDTranslator.toDictList(cmd);
        if (CollUtil.isNotEmpty(dictList)){
            // 编码是否重复
            String errMsg = POHelper.getRepStr(dictList, Dict.class, Dict.NO, ",");
            if (StrUtil.isNotEmpty(errMsg)){
                throw new RuntimeException(String.format("数据字典编码重复了, 重复的编码 : {}", errMsg));
            }
        }

        // 数据字典类型
        DictType dictType = AddCMDTranslator.toDictType(cmd);

        // 编码是否重复
        if (this.dao.isExistsByNo(dictType.getNo())){
            throw new RuntimeException(String.format("已有这个数据字典类型编码, 编码 : %s", dictType.getNo()));
        }

        String dictTypeId = this.dao.insert(dictType);
        this.dictDao.batchInsert(dictTypeId, dictList);

        return dictTypeId;
    }

    @Override
    public void updateDictType(UpdDictTypeCMD cmd) throws Exception {
        // 检查 cmd
        if (cmd == null){
            throw new RuntimeException("请输入新增数据字典类型命令");
        }

        // 更新数据字典类型
        DictType dictType = UpdCMDTranslator.toDictType(cmd);
        this.dao.updateById(dictType);

        // 删除数据字典
        if (cmd.getDict() != null){
            this.dictDao.deleteByDictType(dictType.getId(), cmd.getDict().getDelDictList());
        }

        // 修改数据字典
        List<Dict> updDictList = UpdCMDTranslator.toUpdDictList(cmd);
        this.dictDao.batchUpdateById(dictType.getId(), updDictList);

        // 新增数据字典
        List<Dict> addDictList = UpdCMDTranslator.toAddDictList(cmd);
        this.dictDao.batchInsert(dictType.getId(), addDictList);
    }

    @Override
    public void delDictType(String id) throws Exception {
        // 检查参数
        if (StrUtil.isEmpty(id)){
            return;
        }

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
