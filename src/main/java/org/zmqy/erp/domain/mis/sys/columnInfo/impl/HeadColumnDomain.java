package org.zmqy.erp.domain.mis.sys.columnInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.columnInfo.IHeadColumnDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IUserColumnDomain;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.HeadColumnLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.HeadColumnMapper;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.HeadColumnBo;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.HeadColumnLangBo;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumn;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumnLang;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统单头配置
 * @Auther: lly
 * @Date: 2018-12-17 09:58
 */
@Component
public class HeadColumnDomain implements IHeadColumnDomain {
    @Autowired
    private HeadColumnMapper headColumnMapper;
    @Autowired
    private HeadColumnLangMapper headColumnLangMapper;
    @Autowired
    private IUserColumnDomain userColumnDomain;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(HeadColumnBo bo, String operUserId, Date operTime) throws Exception {

        HeadColumn headColumn = HeadColumnBo.bo2entity(bo);
        String id = this.add(headColumn, operUserId, operTime);

        List<HeadColumnLangBo> headColumnLangBoList = bo.getHeadColumnLangBoList();
        if (headColumnLangBoList == null || headColumnLangBoList.size() <= 0) {
            throw new RuntimeException("名称为空");
        }

        for (HeadColumnLangBo headColumnLangBo : headColumnLangBoList) {
            headColumnLangBo.setHeadColumnId(id);
            this.add(headColumnLangBo, operUserId, operTime);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdfById(HeadColumnBo bo, String operUserId, Date operTime) throws Exception {
        if (bo == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(bo.getId())) {
            throw new RuntimeException("参数为空");
        }
        Map<String, Object> param = new HashMap<>();
        param.put(HeadColumn.MENUID, bo.getMenuId());
        param.put(HeadColumn.PAGETYPENO, bo.getPageTypeNo());
        param.put(HeadColumn.COLUMNNO, bo.getColumnNo());
        List<HeadColumn> headColumnList = headColumnMapper.selectByMap(param);
        if (headColumnList.size() > 0) {
            if (!headColumnList.get(0).getId().equals(bo.getId())) {
                throw new RuntimeException("列编码有重复");
            }
        }

        bo.setCreateTime(null);
        bo.setCreateUserId(null);
        bo.setLastUpdateTime(operTime);

        headColumnMapper.updateById(HeadColumnBo.bo2entity(bo));
        List<HeadColumnLangBo> headColumnLangBoList = bo.getHeadColumnLangBoList();
        if (headColumnLangBoList != null) {
            for (HeadColumnLangBo headColumnLangBo : headColumnLangBoList) {

                if (StringUtil.isEmpty(headColumnLangBo.getLangId())) {
                    throw new RuntimeException("语言编码为空");
                }
                if (StringUtil.isEmpty(headColumnLangBo.getColumnName())) {
                    throw new RuntimeException("名称为空");
                }
                headColumnLangBo.setCreateUserId(null);
                headColumnLangBo.setCreateTime(null);
                headColumnLangBo.setLastUpdateTime(operTime);


                QueryWrapper<HeadColumnLang> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(HeadColumnLang.HEADCOLUMNID, bo.getId());
                queryWrapper.eq(HeadColumnLang.LANGID, headColumnLangBo.getLangId());

                headColumnLangMapper.update(HeadColumnLangBo.bo2entity(headColumnLangBo), queryWrapper);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(String menuId, String pageTypeNo) {

        Map<String, Object> param = new HashMap<>();
        param.put(HeadColumn.MENUID, menuId);
        param.put(HeadColumn.PAGETYPENO, pageTypeNo);
        List<HeadColumn> headColumnList = headColumnMapper.selectByMap(param);
        headColumnMapper.deleteByMap(param);

        if (headColumnList.size() > 0) {
            for (HeadColumn headColumn : headColumnList) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(HeadColumnLang.HEADCOLUMNID, headColumn.getId());
                headColumnLangMapper.deleteByMap(paramMap);
                // 删除用户列配置
                userColumnDomain.del(headColumn.getId());
            }
        }


    }


    public HeadColumn selectById(String id) {
        return headColumnMapper.selectById(id);
    }

    //
    @Transactional(rollbackFor = Exception.class)
    public String add(HeadColumn entity, String operUserId, Date operTime) throws Exception {
        if (entity == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(operUserId)) {
            throw new RuntimeException("参数为空");
        }
        if (operTime == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(entity.getColumnNo())) {
            throw new RuntimeException("列编码为空");
        }
        if (StringUtil.isEmpty(entity.getMenuId())) {
            throw new RuntimeException("菜单编码为空");
        }
        if (StringUtil.isEmpty(entity.getPageTypeNo())) {
            throw new RuntimeException("页面类型为空");
        }
        //判断该条数据是否重复
        Map<String, Object> param = new HashMap<>();
        param.put(HeadColumn.MENUID, entity.getMenuId());
        param.put(HeadColumn.COLUMNNO, entity.getColumnNo());
        param.put(HeadColumn.PAGETYPENO, entity.getPageTypeNo());
        List<HeadColumn> headColumnList = headColumnMapper.selectByMap(param);
        if (headColumnList.size() > 0) {
            throw new RuntimeException("有重复数据");
        }

        if (entity.getSeq() == null) {
            entity.setSeq(1);
        }
        if (entity.getAreHidden() == null) {
            entity.setAreHidden(false);
        }
        if (entity.getAreSysRequired() == null) {
            entity.setAreSysRequired(false);
        }

        if (entity.getAreUserRequired() == null) {
            entity.setAreUserRequired(false);
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        headColumnMapper.insert(entity);

        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(HeadColumnLangBo headColumnLangBo, String operUserId, Date operTime) {
        if (StringUtil.isEmpty(headColumnLangBo.getColumnName())) {
            throw new RuntimeException("列名称为空");
        }
        if (StringUtil.isEmpty(headColumnLangBo.getLangId())) {
            throw new RuntimeException("语言id为空");
        }

        headColumnLangBo.setCreateUserId(operUserId);
        headColumnLangBo.setCreateTime(operTime);
        headColumnLangBo.setLastUpdateTime(operTime);
        headColumnLangMapper.insert(HeadColumnLangBo.bo2entity(headColumnLangBo));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String menuId, String pageTypeNo, String newMenuId, String newPageTypeNo) {
        //先将菜单编码给全部改掉
        QueryWrapper<HeadColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HeadColumn.MENUID, menuId);
        queryWrapper.eq(HeadColumn.PAGETYPENO, pageTypeNo);
        HeadColumn headColumn = new HeadColumn();
        if (!menuId.equals(newMenuId)) {
            headColumn.setMenuId(newMenuId);
        }
        if (!pageTypeNo.equals(newPageTypeNo)) {
            headColumn.setPageTypeNo(newPageTypeNo);
        }

        if (!pageTypeNo.equals(newPageTypeNo) || !menuId.equals(newMenuId)) {
            headColumnMapper.update(headColumn, queryWrapper);
        }

    }

    public HeadColumnLang selectColumnName(String headColumnId, String langId) {
        Map<String, Object> param = new HashMap<>();
        param.put(HeadColumnLang.HEADCOLUMNID, headColumnId);
        param.put(HeadColumnLang.LANGID, langId);
        List<HeadColumnLang> headColumnLangList = headColumnLangMapper.selectByMap(param);
        if (headColumnLangList.size() > 0) {
            return headColumnLangList.get(0);
        }
        return null;
    }

}
