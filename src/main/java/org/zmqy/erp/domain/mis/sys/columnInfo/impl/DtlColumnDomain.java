package org.zmqy.erp.domain.mis.sys.columnInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.columnInfo.IDtlColumnDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IUserColumnDomain;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.DtlColumnLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.DtlColumnMapper;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.DtlColumnBo;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.DtlColumnLangBo;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumn;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumnLang;
import org.zmqy.erp.tool.helper.id.IdHelper;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统单体配置
 * @Auther: lly
 * @Date: 2018-12-17 15:16
 */
@Component
public class DtlColumnDomain implements IDtlColumnDomain {
    @Autowired
    private DtlColumnMapper dtlColumnMapper;

    @Autowired
    private DtlColumnLangMapper dtlColumnLangMapper;

    @Autowired
    private IUserColumnDomain userColumnDomain;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(DtlColumnBo bo, String operUserId, Date operTime) throws Exception {
        if (bo == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(operUserId)) {
            throw new RuntimeException("参数为空");
        }
        if (operTime == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(bo.getColumnNo())) {
            throw new RuntimeException("列编码为空");
        }
        if (StringUtil.isEmpty(bo.getMenuId())) {
            throw new RuntimeException("菜单编码为空");
        }
        if (StringUtil.isEmpty(bo.getPageTypeNo())) {
            throw new RuntimeException("页面类型为空");
        }
        if (StringUtil.isEmpty(bo.getDataTypeNo())) {
            bo.setDataTypeNo("dataType-string");
        }
        if (StringUtil.isEmpty(bo.getArrayNo())) {
            throw new RuntimeException("数组编码为空");
        }
        //判断该条数据是否重复
        Map<String, Object> param = new HashMap<>();
        param.put(DtlColumn.MENUID, bo.getMenuId());
        param.put(DtlColumn.COLUMNNO, bo.getColumnNo());
        param.put(DtlColumn.PAGETYPENO, bo.getPageTypeNo());
        param.put(DtlColumn.ARRAYNO, bo.getArrayNo());
        List<DtlColumn> dtlColumnList = dtlColumnMapper.selectByMap(param);
        if (dtlColumnList.size() > 0) {
            throw new RuntimeException("有重复数据");
        }

        if (bo.getAlias() == null) {
            bo.setAlias("");
        }

        if (bo.getSeq() == null) {
            bo.setSeq(1);
        }
        if (bo.getAreHidden() == null) {
            bo.setAreHidden(false);
        }
        if (bo.getAreSysRequired() == null) {
            bo.setAreSysRequired(false);
        }
        if (bo.getAreUserRequired() == null) {
            bo.setAreUserRequired(false);
        }
        if(bo.getWidth() == null){
            bo.setWidth(0);
        }


        String id = IdHelper.nextId();
        bo.setId(id);
        bo.setCreateUserId(operUserId);
        bo.setCreateTime(operTime);
        bo.setLastUpdateTime(operTime);
        dtlColumnMapper.insert(DtlColumnBo.bo2entity(bo));

        List<DtlColumnLangBo> dtlColumnLangBoList = bo.getDtlColumnLangBoList();
        if (dtlColumnLangBoList == null) {
            throw new RuntimeException("参数为空");
        }
        if (dtlColumnLangBoList.size() <= 0) {
            throw new RuntimeException("参数为空");
        }
        for (DtlColumnLangBo dtlColumnLangBo : dtlColumnLangBoList) {
            this.add(dtlColumnLangBo, operUserId, operTime, id);
        }

        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdfById(DtlColumnBo bo, String operUserId, Date operTime) throws Exception {
        if (bo == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(bo.getId())) {
            throw new RuntimeException("参数为空");
        }
        Map<String, Object> param = new HashMap<>();
        param.put(DtlColumn.MENUID, bo.getMenuId());
        param.put(DtlColumn.PAGETYPENO, bo.getPageTypeNo());
        param.put(DtlColumn.ARRAYNO, bo.getArrayNo());
        param.put(DtlColumn.COLUMNNO, bo.getColumnNo());
        List<DtlColumn> dtlColumnList = dtlColumnMapper.selectByMap(param);
        if (dtlColumnList.size() > 0) {
            if (!dtlColumnList.get(0).getId().equals(bo.getId())) {
                throw new RuntimeException("列编码重复");
            }
        }

        bo.setCreateTime(null);
        bo.setCreateUserId(null);
        bo.setLastUpdateTime(operTime);

        dtlColumnMapper.updateById(DtlColumnBo.bo2entity(bo));
        List<DtlColumnLangBo> DtlColumnLangBoList = bo.getDtlColumnLangBoList();
        if (DtlColumnLangBoList != null) {
            for (DtlColumnLangBo dtlColumnLangBo : DtlColumnLangBoList) {

                if (StringUtil.isEmpty(dtlColumnLangBo.getLangId())) {
                    throw new RuntimeException("语言id为空");
                }
                if (StringUtil.isEmpty(dtlColumnLangBo.getColumnName())) {
                    throw new RuntimeException("名称为空");
                }
                dtlColumnLangBo.setCreateUserId(null);
                dtlColumnLangBo.setCreateTime(null);
                dtlColumnLangBo.setLastUpdateTime(operTime);


                QueryWrapper<DtlColumnLang> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(DtlColumnLang.DTLCOLUMNID, bo.getId());
                queryWrapper.eq(DtlColumnLang.LANGID, dtlColumnLangBo.getLangId());

                dtlColumnLangMapper.update(DtlColumnLangBo.bo2entity(dtlColumnLangBo), queryWrapper);

            }


        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delById(String id) throws Exception {
        dtlColumnMapper.deleteById(id);
        Map<String, Object> param = new HashMap<>();
        param.put(DtlColumnLang.DTLCOLUMNID, id);
        dtlColumnLangMapper.deleteByMap(param);
        userColumnDomain.del(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void add(DtlColumnLangBo bo, String operUserId, Date operTime, String id) {
        if (StringUtil.isEmpty(bo.getColumnName())) {
            throw new RuntimeException("列名称不能为空");
        }
        if (StringUtil.isEmpty(bo.getLangId())) {
            throw new RuntimeException("语言id为空");
        }
        bo.setId(IdHelper.nextId());
        bo.setDtlColumnId(id);
        bo.setCreateUserId(operUserId);
        bo.setCreateTime(operTime);
        bo.setLastUpdateTime(operTime);
        dtlColumnLangMapper.insert(DtlColumnLangBo.bo2entity(bo));

    }

    public DtlColumn selectById(String id) {
        return dtlColumnMapper.selectById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(String menuId, String pageTypeNo) {

        Map<String, Object> param = new HashMap<>();
        param.put(DtlColumn.MENUID, menuId);
        param.put(DtlColumn.PAGETYPENO, pageTypeNo);
        List<DtlColumn> dtlColumnList = dtlColumnMapper.selectByMap(param);
        dtlColumnMapper.deleteByMap(param);

        if (dtlColumnList.size() > 0) {
            for (DtlColumn dtlColumn : dtlColumnList) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(DtlColumnLang.DTLCOLUMNID, dtlColumn.getId());
                dtlColumnLangMapper.deleteByMap(paramMap);
                //删除所有用户的单体配置
                userColumnDomain.del(dtlColumn.getId());
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String menuId, String pageTypeNo, String newMenuId, String newPageTypeNo) {
        QueryWrapper<DtlColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DtlColumn.MENUID, menuId);
        queryWrapper.eq(DtlColumn.PAGETYPENO, pageTypeNo);

        DtlColumn dtlColumn = new DtlColumn();
        if (!menuId.equals(newMenuId)) {
            dtlColumn.setMenuId(newMenuId);
        }
        if (!pageTypeNo.equals(newPageTypeNo)) {
            dtlColumn.setPageTypeNo(newPageTypeNo);
        }
        if (!pageTypeNo.equals(newPageTypeNo) || !menuId.equals(newMenuId)) {
            dtlColumnMapper.update(dtlColumn, queryWrapper);
        }
    }

}
