package org.zmqy.erp.service.mis.pc.module.sys.columnInfo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IDtlColumnDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IHeadColumnDomain;
import org.zmqy.erp.mapper.mis.pc.component.sys.columnInfo.ColumnCpmMapper;
import org.zmqy.erp.mapper.mis.pc.module.sys.columnInfo.ColumnModuleMapper;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.DtlColumnBo;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.DtlColumnLangBo;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.HeadColumnBo;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.HeadColumnLangBo;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.json.JacksonUtil;

import java.io.IOException;
import java.util.*;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 09:38
 */
@Service
public class ColumnModuleService {
    @Autowired
    private IHeadColumnDomain headColumnDomain;

    @Autowired
    private IDtlColumnDomain dtlColumnDomain;

    @Autowired
    private ColumnModuleMapper columnModuleMapper;

    @Autowired
    private IMenuDomain menuDomain;

    @Autowired
    private ColumnCpmMapper columnCpmMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addHeadColumnAndDtl(Map<String, Object> paramsMap) throws Exception {
        String userId = (String) paramsMap.get("loginUserId");
        String langId = (String) paramsMap.get("loginLangId");
        String storeId = (String) paramsMap.get("loginStoreId");
        String menuId = paramsMap.get("menuId") + "";
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");
        List<Map<String, Object>> headColumnBoList = (List<Map<String, Object>>) paramsMap.get("headColumnList");
        Date date = new Date();

        for (int i = 0; i < headColumnBoList.size(); i++) {
            try {
                HeadColumnBo headColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(headColumnBoList.get(i)), HeadColumnBo.class);
                headColumnBo.setSeq(i + 1);
                headColumnBo.setMenuId(menuId);
                headColumnBo.setPageTypeNo(pageTypeNo);
                if (headColumnBo.getAreSysRequired() == true) {
                    headColumnBo.setAreUserRequired(true);
                } else if (headColumnBo.getAreSysRequired() == false) {
                    headColumnBo.setAreUserRequired(false);
                }

                List<HeadColumnLangBo> headColumnLangList = new ArrayList<>();
                HeadColumnLangBo headColumnLangBo = JacksonUtil.readValue(JacksonUtil.toJSon(headColumnBoList.get(i)), HeadColumnLangBo.class);
                headColumnLangBo.setLangId(langId);
                headColumnLangList.add(headColumnLangBo);
                headColumnBo.setHeadColumnLangBoList(headColumnLangList);

                headColumnDomain.add(headColumnBo, userId, date);
            } catch (IOException e) {
                throw new RuntimeException("数组转换失败");
            }
        }

        List<Map<String, Object>> dtlList = (List<Map<String, Object>>) paramsMap.get("dtlList");
        if (dtlList != null) {
            if (dtlList.size() > 0) {
                for (Map<String, Object> param : dtlList) {
                    String name = param.get("dtlNo") + "";
                    List<Map<String, Object>> mapList = (List<Map<String, Object>>) param.get("dtlData");
                    for (int i = 0; i < mapList.size(); i++) {
                        try {
                            DtlColumnBo dtlColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(mapList.get(i)), DtlColumnBo.class);
                            dtlColumnBo.setSeq(i + 1);
                            dtlColumnBo.setArrayNo(name);
                            dtlColumnBo.setMenuId(menuId);
                            dtlColumnBo.setPageTypeNo(pageTypeNo);
                            if (dtlColumnBo.getAreSysRequired() == true) {
                                dtlColumnBo.setAreUserRequired(true);
                            }

                            List<DtlColumnLangBo> dtlColumnLangBoList = new ArrayList<>();
                            DtlColumnLangBo dtlColumnLangBo = JacksonUtil.readValue(JacksonUtil.toJSon(mapList.get(i)), DtlColumnLangBo.class);
                            dtlColumnLangBo.setLangId(langId);
                            dtlColumnLangBoList.add(dtlColumnLangBo);
                            dtlColumnBo.setDtlColumnLangBoList(dtlColumnLangBoList);
                            dtlColumnDomain.add(dtlColumnBo, userId, date);

                        } catch (IOException e) {
                            throw new RuntimeException("数组转换失败");
                        }
                    }

                }
            }

        }

    }

    @Transactional(rollbackFor = Exception.class)
    public PageInfo getColumnAll(Map<String, Object> paramsMap) {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuNoOrName = paramsMap.get("menuNoOrName") + "";
        String pageTypeNo = paramsMap.get("pageTypeNo") + "";
        Integer page = Integer.valueOf(paramsMap.get("page") + "");
        Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") + "");
        paramsMap.put("menuNoOrName", "%" + menuNoOrName + "%");

        PageHelper.startPage(page, pageSize);
        PageInfo pageInfo = new PageInfo<>(columnModuleMapper.getColumnAll(paramsMap));

        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map getColumnDetail(Map<String, Object> paramsMap) {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuId = paramsMap.get("menuId") + "";
        String pageTypeNo = paramsMap.get("pageTypeNo") + "";

        List<Map> headColumnList = columnModuleMapper.getHeadDetail(paramsMap);


        List<String> dtlNoList = columnCpmMapper.getDtlNoListByMenuId(paramsMap);

        //封装返回参数
        List<Map<String, Object>> dtlList = new ArrayList<>();
        Map map = new HashMap();

        if (dtlNoList.size() > 0) {
            for (String dtlNo : dtlNoList) {
                Map<String, Object> dtlMap = new HashMap<>();
                dtlMap.put("dtlNo", dtlNo);
                paramsMap.put("dtlNo", dtlNo);
                List<Map> dtlData = columnModuleMapper.getDtlDetail(paramsMap);
                dtlMap.put("dtlData", dtlData);
                dtlList.add(dtlMap);
            }
        }


        map.put("headColumnList", headColumnList);
        map.put("dtlList", dtlList);
        if (headColumnList.size() > 0) {
            map.put("menuId", menuId);
            Menu menu = menuDomain.getById(menuId);
            if (menu != null) {
                map.put("menuNo", menu.getMenuNo());
            } else {
                map.put("menuNo", "");
            }


            map.put("pageTypeNo", pageTypeNo);
        }

        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map getItColumnDetail(Map<String, Object> paramsMap) {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuId = paramsMap.get("menuId") + "";
        String pageTypeNo = paramsMap.get("pageTypeNo") + "";

        List<Map> headColumnList = columnModuleMapper.getHeadDetail(paramsMap);

        List<String> dtlNoList = columnCpmMapper.getDtlNoListByMenuId(paramsMap);

        //封装返回参数
        List<Map<String, Object>> dtlList = new ArrayList<>();
        Map map = new HashMap(16);

        if (dtlNoList.size() > 0) {
            for (String dtlNo : dtlNoList) {
                Map<String, Object> dtlMap = new HashMap<>(16);
                dtlMap.put("dtlNo", dtlNo);
                paramsMap.put("dtlNo", dtlNo);
                List<Map> dtlData = columnModuleMapper.getDtlDetail(paramsMap);
                List<Map> formulaData = new ArrayList<>();
                for (int i = 0; i < dtlData.size(); i++) {
                    if (!StringUtil.isEmpty((String) dtlData.get(i).get("formula"))) {
                        formulaData.add(dtlData.get(i));
                        dtlData.remove(i);
                        i--;
                    }
                }
                dtlMap.put("dtlData", dtlData);
                dtlMap.put("formulaData", formulaData);
                dtlList.add(dtlMap);

                for (int j = 0; j < headColumnList.size(); j++) {
                    if (dtlNo.equals(headColumnList.get(j).get("columnNo"))) {
                        headColumnList.remove(j);
                    }
                }


            }
        }


        map.put("headColumnList", headColumnList);
        map.put("dtlList", dtlList);
        if (headColumnList.size() > 0) {
            map.put("menuId", menuId);
            Menu menu = menuDomain.getById(menuId);
            if (menu != null) {
                map.put("menuNo", menu.getMenuNo());
            } else {
                map.put("menuNo", "");
            }


            map.put("pageTypeNo", pageTypeNo);
        }

        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addFormula(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String dtlNo = paramsMap.get("dtlNo") + "";
        Date date = new Date();
        try {
            DtlColumnBo dtlColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(paramsMap), DtlColumnBo.class);
            dtlColumnBo.setArrayNo(dtlNo);


            List<DtlColumnLangBo> dtlColumnLangBoList = new ArrayList<>();
            DtlColumnLangBo dtlColumnLangBo = JacksonUtil.readValue(JacksonUtil.toJSon(paramsMap), DtlColumnLangBo.class);
            dtlColumnLangBo.setLangId(loginLangId);
            dtlColumnLangBoList.add(dtlColumnLangBo);
            dtlColumnBo.setDtlColumnLangBoList(dtlColumnLangBoList);

            dtlColumnDomain.add(dtlColumnBo, loginUserId, date);

        } catch (IOException e) {
            throw new RuntimeException("转换异常");
        }


    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfColumnDetail(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        //要修改的单头
        List<Map<String, Object>> headColumnList = (List<Map<String, Object>>) paramsMap.get("headColumnList");
        //要修改的单体
        List<Map<String, Object>> dtlList = (List<Map<String, Object>>) paramsMap.get("dtlList");
        //要删除的单体
        List<String> delList = (List<String>) paramsMap.get("delList");
        String menuId = (String) paramsMap.get("menuId");
        String newMenuId = (String) paramsMap.get("newMenuId");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");
        String newPageTypeNo = (String) paramsMap.get("newPageTypeNo");
        Date date = new Date();

        if (delList.size() > 0) {
            for (String id : delList) {
                dtlColumnDomain.delById(id);
            }
        }

        //先将菜单编码给全部改掉
        headColumnDomain.update(menuId, pageTypeNo, newMenuId, newPageTypeNo);


        //修改了菜单编码,先将所有的菜单编码给改了
        dtlColumnDomain.update(menuId, pageTypeNo, newMenuId, newPageTypeNo);


        //单体的新增修改
        if (headColumnList.size() > 0) {
            for (Map<String, Object> map : headColumnList) {
                try {
                    HeadColumnBo headColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(map), HeadColumnBo.class);
                    if (menuId.equals(newMenuId)) {
                        headColumnBo.setMenuId(menuId);
                    } else {
                        headColumnBo.setMenuId(newMenuId);
                    }

                    if (pageTypeNo.equals(newPageTypeNo)) {
                        headColumnBo.setPageTypeNo(pageTypeNo);
                    } else {
                        headColumnBo.setPageTypeNo(newPageTypeNo);
                    }
                    if (headColumnBo.getAreSysRequired() == true) {
                        headColumnBo.setAreUserRequired(true);
                    } else if (headColumnBo.getAreSysRequired() == false) {
                        headColumnBo.setAreUserRequired(false);
                    }

                    List<HeadColumnLangBo> headColumnLangList = new ArrayList<>();
                    HeadColumnLangBo headColumnLangBo = new HeadColumnLangBo();
                    headColumnLangBo.setColumnName(map.get("columnName") + "");
                    headColumnLangBo.setLangId(loginLangId);
                    headColumnLangList.add(headColumnLangBo);
                    headColumnBo.setHeadColumnLangBoList(headColumnLangList);

                    if (headColumnBo.getId() == null) {
                        headColumnDomain.add(headColumnBo, loginUserId, date);
                    } else {
                        headColumnDomain.mdfById(headColumnBo, loginUserId, date);
                    }

                } catch (IOException e) {
                    throw new RuntimeException("转换异常");
                }
            }
        }
        //单体修改
        if (dtlList.size() > 0) {
            for (Map<String, Object> map : dtlList) {
                try {
                    DtlColumnBo dtlColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(map), DtlColumnBo.class);
                    if (menuId.equals(newMenuId)) {
                        dtlColumnBo.setMenuId(menuId);
                    } else {
                        dtlColumnBo.setMenuId(newMenuId);
                    }

                    if (pageTypeNo.equals(newPageTypeNo)) {
                        dtlColumnBo.setPageTypeNo(pageTypeNo);
                    } else {
                        dtlColumnBo.setPageTypeNo(newPageTypeNo);
                    }

                    if (dtlColumnBo.getAreSysRequired()) {
                        dtlColumnBo.setAreUserRequired(true);
                    }

                    List<DtlColumnLangBo> dtlColumnLangBoList = new ArrayList<>();
                    DtlColumnLangBo dtlColumnLangBo = new DtlColumnLangBo();
                    dtlColumnLangBo.setLangId(loginLangId);
                    dtlColumnLangBo.setColumnName(map.get("columnName") + "");
                    dtlColumnLangBoList.add(dtlColumnLangBo);
                    dtlColumnBo.setDtlColumnLangBoList(dtlColumnLangBoList);

                    if (dtlColumnBo.getId() == null) {
                        dtlColumnDomain.add(dtlColumnBo, loginUserId, date);
                    } else {
                        dtlColumnDomain.mdfById(dtlColumnBo, loginUserId, date);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("转换异常");
                }


            }
        }


    }

    @Transactional(rollbackFor = Exception.class)
    public void delColumnDetail(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuId = (String) paramsMap.get("menuId");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");

        headColumnDomain.del(menuId, pageTypeNo);
        dtlColumnDomain.del(menuId, pageTypeNo);

    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfColumnByIt(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuId = (String) paramsMap.get("menuId");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");

        //要修改的单头
        List<Map<String, Object>> headColumnList = (List<Map<String, Object>>) paramsMap.get("headColumnList");
        //要修改的单体
        List<Map<String, Object>> dtlList = (List<Map<String, Object>>) paramsMap.get("dtlList");

        Date date = new Date();


        //单头的新增修改
        if (headColumnList.size() > 0) {
            for (Map<String, Object> map : headColumnList) {
                try {
                    HeadColumnBo headColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(map), HeadColumnBo.class);
                    headColumnBo.setMenuId(menuId);
                    headColumnBo.setPageTypeNo(pageTypeNo);
                    headColumnBo.setAreSysRequired(null);
                    headColumnBo.setColumnNo(null);


                    List<HeadColumnLangBo> headColumnLangList = new ArrayList<>();
                    HeadColumnLangBo headColumnLangBo = new HeadColumnLangBo();
                    headColumnLangBo.setColumnName(map.get("columnName") + "");
                    headColumnLangBo.setLangId(loginLangId);
                    headColumnLangList.add(headColumnLangBo);
                    headColumnBo.setHeadColumnLangBoList(headColumnLangList);

                    headColumnDomain.mdfById(headColumnBo, loginUserId, date);


                } catch (IOException e) {
                    throw new RuntimeException("转换异常");
                }
            }
        }

        if (dtlList.size() > 0) {
            for (Map<String, Object> map : dtlList) {

                try {
                    DtlColumnBo dtlColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(map), DtlColumnBo.class);
                    dtlColumnBo.setMenuId(menuId);
                    dtlColumnBo.setPageTypeNo(pageTypeNo);
                    dtlColumnBo.setAreSysRequired(null);
                    dtlColumnBo.setColumnNo(null);
                    dtlColumnBo.setDataTypeNo(null);


                    List<DtlColumnLangBo> dtlColumnLangBoList = new ArrayList<>();
                    DtlColumnLangBo dtlColumnLangBo = new DtlColumnLangBo();
                    dtlColumnLangBo.setLangId(loginLangId);
                    dtlColumnLangBo.setColumnName(map.get("columnName") + "");
                    dtlColumnLangBoList.add(dtlColumnLangBo);
                    dtlColumnBo.setDtlColumnLangBoList(dtlColumnLangBoList);


                    dtlColumnDomain.mdfById(dtlColumnBo, loginUserId, date);
                } catch (IOException e) {
                    throw new RuntimeException("转换异常");
                }

            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Map> getColumnNoAll(Map<String, Object> paramsMap) {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String menuId = (String) paramsMap.get("menuId");
        String pageTypeNo = (String) paramsMap.get("pageTypeNo");
        String dtlNo = (String) paramsMap.get("dtlNo");
        //将是公式的列过滤掉
        paramsMap.put("isFormula", "aaa");
        List<Map> dtlList = columnModuleMapper.getDtlDetail(paramsMap);
        return dtlList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfFormula(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        Date date = new Date();

        try {
            DtlColumnBo dtlColumnBo = JacksonUtil.readValue(JacksonUtil.toJSon(paramsMap), DtlColumnBo.class);
            dtlColumnDomain.mdfById(dtlColumnBo, loginUserId, date);
        } catch (IOException e) {
            throw new RuntimeException("转换异常");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void delFormula(Map<String, Object> paramsMap) throws Exception {
        String loginUserId = (String) paramsMap.get("loginUserId");
        String loginLangId = (String) paramsMap.get("loginLangId");
        String loginStoreId = (String) paramsMap.get("loginStoreId");
        String id = (String) paramsMap.get("id");
        dtlColumnDomain.delById(id);
    }
}
