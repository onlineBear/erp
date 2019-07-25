package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.IterUtil;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdateDictTypeCMD;
import org.anson.miniProject.tool.helper.CollHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeDMService implements IDictTypeDMService {
    // 接口方法
    @Override
    public String addDictType(AddDictTypeCMD cmd) throws Exception {
        // 检查 cmd

        DictTypeEntity entity = AddCMDTranslator.toDictTypeEntity(cmd);
        // 数据字典类型

        // 编码是否重复
        if (this.rep.isExistsByNo(entity.getNo())){
            throw new RuntimeException(String.format("已有这个数据字典类型编码, 编码 : %s", entity.getNo()));
        }

        // 数据字典
        // 编码是否重复
        List<AddDictTypeCMD.Dict> dictList = cmd.getDictList();

        if (IterUtil.isNotEmpty(dictList)){
            List<String> dictNoList = new ArrayList<>();
            for (AddDictTypeCMD.Dict dict : dictList){
                dictNoList.add(dict.getNo());
            }

            List<String> repeatedDictNoList = CollHelper.findRepeatedVal(dictNoList);

            if (IterUtil.isNotEmpty(repeatedDictNoList)){
                StringBuilder sb = new StringBuilder("以下数据字典编码重复了, 请重新输入, 数据字典编码 : ");
                for (int i=0;i<repeatedDictNoList.size();i++){
                    if (i != repeatedDictNoList.size() - 1){
                        sb.append(repeatedDictNoList.get(i)).append(", ");
                    }else {
                        sb.append(repeatedDictNoList.get(i));
                    }
                }

                throw new RuntimeException(sb.toString());
            }
        }

        String dictTypeId = this.rep.insert(entity);

        return dictTypeId;
    }

    @Override
    public void updateDictType(UpdateDictTypeCMD cmd) throws Exception {

        // 更新数据字典类型

        // 删除数据字典

        // 新增数据字典


    }

    // 非接口方法

    // 注入
    @Autowired
    private DictTypeRep rep;
}
