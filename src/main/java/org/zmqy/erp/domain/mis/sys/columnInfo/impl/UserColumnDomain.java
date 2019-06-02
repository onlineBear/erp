package org.zmqy.erp.domain.mis.sys.columnInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zmqy.erp.constract.mis.enums.PageTypeNoEnum;
import org.zmqy.erp.domain.mis.sys.columnInfo.IUserColumnDomain;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.DtlColumnMapper;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.HeadColumnMapper;
import org.zmqy.erp.mapper.mis.biz.sys.columnInfo.UserColumnMapper;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumn;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumn;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.UserColumn;
import org.zmqy.erp.tool.helper.id.IdHelper;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户单头配置
 * @Auther: lly
 * @Date: 2018-12-20 09:08
 */
@Component
public class UserColumnDomain implements IUserColumnDomain {
    @Autowired
    private UserColumnMapper mapper;
    @Autowired
    private HeadColumnMapper headColumnMapper;
    @Autowired
    private DtlColumnMapper dtlColumnMapper;

    @Override
    public String add(UserColumn entity, String operUserId, Date operTime) throws Exception {
        if (entity == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(entity.getColumnId())) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(entity.getUserId())) {
            throw new RuntimeException("参数为空");
        }
        QueryWrapper<UserColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserColumn.COLUMNID, entity.getColumnId());
        queryWrapper.eq(UserColumn.USERID, entity.getUserId());
        if (mapper.selectCount(queryWrapper) != 0) {
            throw new RuntimeException("数据重复");
        }

        //判断设置隐藏属性是否和必填属性起了冲突,必填无法设置隐藏
        HeadColumn headColumn = headColumnMapper.selectById(entity.getColumnId());
        if (headColumn != null) {
            if (headColumn.getAreUserRequired() || headColumn.getAreSysRequired()) {
                if ((entity.getAreUserHidden() == null) ? false : entity.getAreUserHidden()) {
                    throw new RuntimeException("列" + entity.getColumnName() + "是必填状态,无法隐藏");
                }
            }
        }

        //判断设置隐藏属性是否和必填属性起了冲突,必填无法设置隐藏
        DtlColumn dtlColumn = dtlColumnMapper.selectById(entity.getColumnId());
        if (dtlColumn != null) {
            if (dtlColumn.getAreUserRequired() || dtlColumn.getAreSysRequired()) {
                if ((entity.getAreUserHidden() == null) ? false : entity.getAreUserHidden()) {
                    throw new RuntimeException("列" + entity.getColumnName() + "是必填状态,无法隐藏");
                }

            }
        }


        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);
        entity.setId(IdHelper.nextId());
        mapper.insert(entity);


        return entity.getId();
    }

    @Override
    public void mdfById(UserColumn entity, String operUserId, Date operTime) throws Exception {
        if (entity == null) {
            throw new RuntimeException("参数为空");
        }
        if (StringUtil.isEmpty(entity.getId())) {
            throw new RuntimeException("参数为空");
        }
        UserColumn userColumn = mapper.selectById(entity.getId());

        if (userColumn == null) {
            throw new RuntimeException("该数据不存在,无法修改");
        }


        //判断设置隐藏属性是否和必填属性起了冲突,必填无法设置隐藏
        HeadColumn headColumn = headColumnMapper.selectById(userColumn.getColumnId());
        if (headColumn != null) {
            if (headColumn.getAreUserRequired() || headColumn.getAreSysRequired()) {
                if ((entity.getAreUserHidden() == null) ? false : entity.getAreUserHidden()) {
                    throw new RuntimeException("列" + entity.getColumnName() + "是必填状态,无法隐藏");
                }
            }
        }

        //判断设置隐藏属性是否和必填属性起了冲突,必填无法设置隐藏
        DtlColumn dtlColumn = dtlColumnMapper.selectById(userColumn.getColumnId());
        if (dtlColumn != null) {
            if (dtlColumn.getAreUserRequired() || dtlColumn.getAreSysRequired()) {
                if ((entity.getAreUserHidden() == null) ? false : entity.getAreUserHidden()) {
                    throw new RuntimeException("列" + entity.getColumnName() + "是必填状态,无法隐藏");
                }
            }
        }

        entity.setCreateUserId(null);
        entity.setCreateTime(null);
        entity.setLastUpdateTime(operTime);

        mapper.updateById(entity);

    }

    /**
     * 重置用户配置
     *
     * @param id 用户配置列id
     * @throws Exception
     */
    @Override
    public void resetById(String id) throws Exception {
        mapper.resetById(id);

    }

    /**
     * 将用户配置了这个列的配置都删除
     *
     * @param columnId
     */
    @Override
    public void del(String columnId) {
        Map<String, Object> param = new HashMap<>();
        param.put(UserColumn.COLUMNID, columnId);
        mapper.deleteByMap(param);
    }

    @Override
    public List<UserColumn> selectByMap(Map<String, Object> params) {
        return mapper.selectByMap(params);
    }

    /**
     * 获取该用户所有必填项
     *
     * @param langId     语言id
     * @param userId     用户id
     * @param menuNo     菜单No
     * @param pageTypeNo 页面id
     * @return
     */
    @Override
    public List<Map<String, String>> getUserRequiredColumn(String langId, String userId, String menuNo, PageTypeNoEnum pageTypeNo) {
        return mapper.getUserRequiredColumn(langId, userId, menuNo, pageTypeNo.getCode());
    }

}
