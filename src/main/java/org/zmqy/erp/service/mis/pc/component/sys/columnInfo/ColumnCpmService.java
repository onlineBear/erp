package org.zmqy.erp.service.mis.pc.component.sys.columnInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IHeadColumnDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IUserColumnDomain;
import org.zmqy.erp.mapper.mis.pc.component.sys.columnInfo.ColumnCpmMapper;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.DtlColumnBo;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.HeadColumnBo;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.UserColumn;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.json.JacksonUtil;

import java.io.IOException;
import java.util.*;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-18 11:43
 */
@Service
public class ColumnCpmService {
    @Autowired
    private ColumnCpmMapper columnCpmMapper;

    @Autowired
    private IUserColumnDomain userColumnDomain;

    @Autowired
    private IHeadColumnDomain headColumnDomain;

    @Autowired
    private IMenuDomain menuDomain;

    @Transactional(rollbackFor = Exception.class)
    public List<Map> getUseHeadColumnConfig(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuNo = (String) paramsMap.get("menuNo");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");
        List<Map> columnList = columnCpmMapper.getUseHeadColumnByMenuNo(paramsMap);
        //获取所有单体编码
        List<String> dtlNoList = columnCpmMapper.getDtlNoList(paramsMap);
        for (int i = 0; i < columnList.size(); i++) {
            if (dtlNoList.size() > 0) {
                for (String dtlNo : dtlNoList) {
                    if (dtlNo.equals(columnList.get(i).get("columnNo"))) {
                        columnList.remove(i);
                        i--;
                        continue;
                    }
                }
            }

        }

        return columnList;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map mdfUserHeadColumn(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");

        //要修改的单头
        List<Map<String, Object>> headColumnList = (List<Map<String, Object>>) paramsMap.get("headColumnList");
        Date date = new Date();


        //单头的修改
        if (headColumnList.size() > 0) {
            for (Map<String, Object> map : headColumnList) {
                try {
                    HeadColumnBo headColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(map), HeadColumnBo.class);
                    String userColumnId = (String) map.get("userColumnId");
                    UserColumn userColumn = new UserColumn();

                    if (StringUtil.isEmpty(userColumnId)) {
                        userColumn.setUserId(loginUserId);
                        userColumn.setColumnId(headColumnBo.getId());
                        userColumn.setColumnName((String) map.get("columnName"));
                        userColumn.setSeq(headColumnBo.getSeq());
                        userColumn.setAreUserHidden((Boolean) map.get("areUserHidden"));

                        userColumnDomain.add(userColumn, loginUserId, date);
                    } else {
                        userColumn.setId(userColumnId);
                        userColumn.setColumnName((String) map.get("columnName"));
                        userColumn.setSeq(headColumnBo.getSeq());
                        userColumn.setAreUserHidden((Boolean) map.get("areUserHidden"));
                        userColumnDomain.mdfById(userColumn, loginUserId, date);
                    }


                } catch (IOException e) {
                    throw new RuntimeException("转换异常");
                }
            }
        }

        //根据此条件获取到非隐藏的列
        if (headColumnList.size() > 0) {
            Menu menu = menuDomain.getById((String) headColumnList.get(0).get("menuId"));
            paramsMap.put("menuNo", menu.getMenuNo());
            paramsMap.put("pageTypeNo", headColumnList.get(0).get("pageTypeNo"));
        }
        List<Map> userHeadColumnList = columnCpmMapper.getUseHeadColumnByMenuNo(paramsMap);

        Map map = new HashMap();
        if (userHeadColumnList != null) {
            for (int i = 0; i < userHeadColumnList.size(); i++) {
                Boolean flag = (Boolean) userHeadColumnList.get(i).get("areUserHidden");
                if (flag) {
                    userHeadColumnList.remove(i);
                    i--;
                    continue;
                }
                map.put((String) userHeadColumnList.get(i).get("columnNo"), userHeadColumnList.get(i));
                userHeadColumnList.get(i).put("seq", i + 1);
            }
        }

        return map;

    }


    public Map getUserHeadColumn(Map<String, Object> paramsMap) {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuNo = (String) paramsMap.get("menuNo");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");

        List<Map> userHeadColumnList = columnCpmMapper.getUseHeadColumnByMenuNo(paramsMap);

        Map map = new HashMap();
        if (userHeadColumnList != null) {
            for (int i = 0; i < userHeadColumnList.size(); i++) {
                Boolean flag = (Boolean) userHeadColumnList.get(i).get("areUserHidden");
                if (flag) {
                    userHeadColumnList.remove(i);
                    i--;
                    continue;
                }
                map.put((String) userHeadColumnList.get(i).get("columnNo"), userHeadColumnList.get(i));
                userHeadColumnList.get(i).put("seq", i + 1);
            }
        }

        //获取所有单体编码
        List<String> dtlNoList = columnCpmMapper.getDtlNoList(paramsMap);


        //封装返回参数
        List<Map> dtlList = new ArrayList<>();

        if (dtlNoList.size() > 0) {
            for (String dtlNo : dtlNoList) {
                Map<String, Object> dtlMap = new HashMap<>();
                dtlMap.put("dtlNo", dtlNo);
                paramsMap.put("dtlNo", dtlNo);
                List<Map> dtlData = columnCpmMapper.getUseDelColumnConfigByMenuNo(paramsMap);
                Map dtlNoMap = columnCpmMapper.getDelNameByDtlNo(menuNo, pageTypeNo, dtlNo, loginLangId);
                dtlMap.put("dtlData", dtlData);
                if (dtlNoMap != null) {
                    dtlMap.put("dtlName", dtlNoMap.get("columnName"));
                    dtlMap.put("areRequired", dtlNoMap.get("areSysRequired"));
                }
                dtlList.add(dtlMap);
            }
        }

        map.put("dtlList", dtlList);
        return map;
    }

    public List<Map> getUseDelColumnConfig(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuNo = (String) paramsMap.get("menuNo");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");
        String dtlNo = (String) paramsMap.get("dtlNo");

        return columnCpmMapper.getUseDelColumnConfigByMenuNo(paramsMap);

    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfUserDelColumn(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        Date date = new Date();

        //要修改的单体
        List<Map<String, Object>> dtlList = (List<Map<String, Object>>) paramsMap.get("dtlList");

        //单体修改
        if (dtlList.size() > 0) {
            for (Map<String, Object> map : dtlList) {
                try {
                    DtlColumnBo dtlColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(map), DtlColumnBo.class);
                    //用户配置id
                    String userColumnId = (String) map.get("userColumnId");

                    String columnName = (String) map.get("columnName");
                    if (dtlColumnBo.getWidth() == null) {
                        dtlColumnBo.setWidth(0);
                    }

                    UserColumn userColumn = new UserColumn();

                    if (StringUtil.isEmpty(userColumnId)) {
                        userColumn.setUserId(loginUserId);
                        userColumn.setColumnId(dtlColumnBo.getId());
                        userColumn.setColumnName(columnName);
                        userColumn.setSeq(dtlColumnBo.getSeq());
                        userColumn.setAreUserHidden((Boolean) map.get("areUserHidden"));
                        userColumn.setWidth(0);

                        userColumnDomain.add(userColumn, loginUserId, date);


                    } else {
                        userColumn.setId(userColumnId);
                        userColumn.setColumnName(columnName);
                        userColumn.setSeq(dtlColumnBo.getSeq());
                        userColumn.setAreUserHidden((Boolean) map.get("areUserHidden"));
                        userColumn.setWidth(null);
                        userColumnDomain.mdfById(userColumn, loginUserId, date);
                    }


                } catch (IOException e) {
                    throw new RuntimeException("转换异常");
                }


            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void mdfUserColumnWidth(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String id = (String) paramsMap.get("id");
        Integer width = (Integer) paramsMap.get("width");
        Date date = new Date();

        Map<String, Object> param = new HashMap<>();
        param.put(UserColumn.COLUMNID, id);
        param.put(UserColumn.USERID, loginUserId);

        List<UserColumn> userColumnList = userColumnDomain.selectByMap(param);
        if (userColumnList.size() > 0) {
            UserColumn userColumn = userColumnList.get(0);
            userColumn.setWidth(width);
            userColumnDomain.mdfById(userColumn, loginUserId, date);
        } else {
            UserColumn userColumn = new UserColumn();
            userColumn.setWidth(width);
            userColumn.setColumnId(id);
            userColumn.setUserId(loginUserId);
            userColumnDomain.add(userColumn, loginUserId, date);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public Map getDefaultHeadColumn(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuNo = paramsMap.get("menuNo") + "";
        String pageTypeNo = paramsMap.get("pageTypeNo") + "";

        List<Map> headList = columnCpmMapper.getHeadDetail(paramsMap);
        if (headList.size() > 0) {
            for (Map map : headList) {
                String userColumnId = (String) map.get("userColumnId");
                userColumnDomain.resetById(userColumnId);
            }
        }

        List<Map> userHeadColumnList = columnCpmMapper.getUseHeadColumnByMenuNo(paramsMap);
        Map map = new HashMap();
        if (userHeadColumnList != null) {
            for (int i = 0; i < userHeadColumnList.size(); i++) {
                Boolean flag = (Boolean) userHeadColumnList.get(i).get("areUserHidden");
                if (flag) {
                    userHeadColumnList.remove(i);
                    i--;
                    continue;
                }
                map.put((String) userHeadColumnList.get(i).get("columnNo"), userHeadColumnList.get(i));
                userHeadColumnList.get(i).put("seq", i + 1);
            }
        }

        map.put("headColumnList", headList);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Map> getDefaultDtlColumn(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuNo = (String) paramsMap.get("menuNo");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");
        String dtlNo = (String) paramsMap.get("dtlNo");
        List<Map> dtlList = columnCpmMapper.getDtlDetail(paramsMap);
        if (dtlList.size() > 0) {
            for (Map map : dtlList) {
                String userColumnId = (String) map.get("userColumnId");
                userColumnDomain.resetById(userColumnId);
            }
        }

        return columnCpmMapper.getDtlDetail(paramsMap);

    }
}
