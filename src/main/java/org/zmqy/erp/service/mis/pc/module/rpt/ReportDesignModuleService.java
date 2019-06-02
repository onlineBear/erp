package org.zmqy.erp.service.mis.pc.module.rpt;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.zmqy.erp.constract.mis.constract.GeneralParam;
import org.zmqy.erp.domain.mis.rpt.IReportDesignDomain;
import org.zmqy.erp.mapper.mis.pc.module.rpt.ReportDesignModuleMapper;
import org.zmqy.erp.model.mis.bo.rpt.ReportDesignBo;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesign;
import org.zmqy.erp.model.mis.entity.rpt.ReportDesignHistory;
import org.zmqy.erp.model.mis.entity.rpt.ReportRelation;
import org.zmqy.erp.tool.helper.zmqy.param.QueryParamHelper;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.json.JacksonUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-13 14:09
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportDesignModuleService {
    @Autowired
    private ReportDesignModuleMapper mapper;
    @Autowired
    private IReportDesignDomain domain;

    public Map decodeSql(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        String sql = (String) paramsMap.get("sql");
        String viewSql = decodeSql(sql, "");

        Map map = new HashMap();
        map.put("viewSql", viewSql);
        return map;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getReport(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        Integer page = (Integer) paramsMap.get(GeneralParam.PAGE_PAGE_KEY);
        Integer pageSize = (Integer) paramsMap.get(GeneralParam.PAGE_PAGESIZE_KEY);

        //报表名称
        String reportName = (String) paramsMap.get("reportName");
        if (!StringUtil.isEmpty(reportName)) {
            paramsMap.put("reportName", "%" + reportName + "%");
        }
        //报表类型
        List<String> reportTypeList = null;

        reportTypeList = (List<String>) paramsMap.get("reportTypeList");
        for (int i = 0; i < reportTypeList.size(); i++) {
            reportTypeList.set(i, "%" + reportTypeList.get(i) + "%");
        }


        //所属主报表
        String parentReportList = (String) paramsMap.get("parentReportList");
        String parentReport = "";
        if (!StringUtil.isEmpty(parentReportList)) {
            String[] arr = parentReportList.split(",");
            for (int i = 0; i < arr.length; i++) {
                if (i == 0) {
                    parentReport += "'" + arr[i] + "'";
                } else {
                    parentReport += ",'" + arr[i] + "'";
                }

            }
            paramsMap.put("parentReport", parentReport);
        }

        //最后更新时间
        String lastUpdateTime = (String) paramsMap.get("lastUpdateTime");
        //说明
        String reportState = (String) paramsMap.get("reportState");
        if (!StringUtil.isEmpty(reportState)) {
            paramsMap.put("reportState", "%" + reportState + "%");
        }
        //创建人
        String createUser = (String) paramsMap.get("createUser");
        if (!StringUtil.isEmpty(createUser)) {
            paramsMap.put("createUser", "%" + createUser + "%");
        }
        //发布状态
        String publishStatus = (String) paramsMap.get("publishStatus");

        PageHelper.startPage(page, pageSize);
        List<Map> list = mapper.getReport(paramsMap, reportTypeList);
        //将所属主报表查询出来
        if (list.size() > 0) {
            for (Map map : list) {
                map.get("id");
                List<Map> parentNameAll = mapper.getParentReportAll((String) map.get("id"));
                if (parentNameAll.size() > 0) {
                    String parentNames = "";
                    for (int i = 0; i < parentNameAll.size(); i++) {
                        if (i == 0) {
                            parentNames += parentNameAll.get(i).get("reportName");
                        } else {
                            parentNames += "," + parentNameAll.get(i).get("reportName");
                        }
                    }

                    map.put("parentReport", parentNames);
                }
            }
        }
        Long total = new PageInfo<>(list).getTotal();
        Map map = new HashMap();
        map.put("list", list);
        map.put("total", total);

        return map;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getReportList(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        Integer page = (Integer) paramsMap.get(GeneralParam.PAGE_PAGE_KEY);
        Integer pageSize = (Integer) paramsMap.get(GeneralParam.PAGE_PAGESIZE_KEY);
        if (page == null) {
            page = 1;
            pageSize = 500000;
        }
        //查询条件,报表名称
        String reportName = (String) paramsMap.get("reportName");
        if (!StringUtil.isEmpty(reportName)) {
            paramsMap.put("reportName", "%" + reportName + "%");
        }

        PageHelper.startPage(page, pageSize);

        List<Map> reportList = mapper.getReportList();

        Long total = new PageInfo(reportList).getTotal();
        Map map = new HashMap();
        map.put("list", reportList);
        map.put("total", total);
        return map;
    }

    public void mdfReportPublish(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        List<String> ids = (List<String>) paramsMap.get("ids");
        Date date = new Date();
        for (int i = 0; i < ids.size(); i++) {
            domain.publishReport(ids.get(i), date);

            ReportDesign reportDesign = domain.selectById(ids.get(i));
            //记录版本历史
            ReportDesignHistory reportDesignHistory = ReportDesign.toReportHistory(reportDesign);
            reportDesignHistory.setReportId(reportDesign.getId());
            domain.addReportHistory(reportDesignHistory, userId, date);
        }

    }

    public void delReport(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        List<String> ids = (List<String>) paramsMap.get("ids");
        if (ids.size() > 0) {
            for (String id : ids) {
                domain.delReport(id);
            }
        }
    }


    /**
     * 解析sql,将sql用来展示查看
     *
     * @param sql
     * @return
     */
    public String decodeSql(String sql, String type) {
        String viewSql = sql;
        if (StringUtil.isEmpty(sql)) {
            return viewSql;
        }


        //将sql中的时间变量给转换成真实值
        viewSql = decodeDate(sql);
        viewSql = viewSql.replaceAll("\r|\n", "");

        String[] sqlArr = viewSql.split("#");
        for (int i = 1; i < sqlArr.length; i += 2) {
            //testExecute测试执行sql,故解析sql时将所有变量置空
            if ("testExecute".equals(type)) {
                viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                continue;
            }
            //动态变量
            if (sqlArr[i].contains("{queryCondition}")) {
                viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                continue;
            }
            //排序变量
            if (sqlArr[i].contains("{order by}")) {
                viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                continue;
            }
            //查询参数常量
            if (sqlArr[i].startsWith("<")) {
                boolean flag = false;
                String param = sqlArr[i].substring(sqlArr[i].indexOf("<") + 1, sqlArr[i].lastIndexOf(">")).trim();
                for (int j = 1; j < sqlArr.length; j += 2) {
                    List<String> list = new ArrayList<>();
                    if (sqlArr[j].contains(":")) {
                        String str = sqlArr[j].substring(sqlArr[j].indexOf(":") + 1, sqlArr[j].lastIndexOf("}")).trim();
                        if (param.equals(str)) {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    viewSql = viewSql.replace("#" + sqlArr[i] + "#", "<条件取值>");
                } else {
                    viewSql = viewSql.replace("#" + sqlArr[i] + "#", " ");
                }

                continue;
            }
            //查询参数
            if (sqlArr[i].contains(":")) {
                viewSql = viewSql.replace("#" + sqlArr[i] + "#", " and " + sqlArr[i].substring(sqlArr[i].indexOf("{") + 1, sqlArr[i].indexOf(":")) + " = '<条件取值>'");
                continue;
            }

        }

        return viewSql;
    }

    /**
     * 将sql中的关于时间的系统变量给解析出来
     *
     * @param sql
     * @return
     */
    public String decodeDate(String sql) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = null;
        //将变量替换成具体的值
        //今天
        if (sql.contains("{$today}")) {
            sql = sql.replace("{$today}", "'" + sdf.format(new Date()) + " 00:00:00" + "'");
        }
        //昨天
        if (sql.contains("{$yesterday}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            sql = sql.replace("{$yesterday}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //明天
        if (sql.contains("{$tomorrow}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            sql = sql.replace("{$tomorrow}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //上周同天
        if (sql.contains("{$lastWeekDay}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            sql = sql.replace("{$lastWeekDay}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //上月同天
        if (sql.contains("{$lastMonthDay}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            sql = sql.replace("{$lastMonthDay}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //去年同天
        if (sql.contains("{$lastYearDay}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -1);
            sql = sql.replace("{$lastYearDay}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //本年初
        if (sql.contains("{$currentYearBegin}")) {
            calendar = Calendar.getInstance();
            GregorianCalendar gc = (GregorianCalendar) calendar;
            calendar.set(Calendar.YEAR, Integer.valueOf(gc.get(1)));
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DATE, 1);
            sql = sql.replace("{$currentYearBegin}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //本年末
        if (sql.contains("{$currentYearEnd}")) {
            calendar = Calendar.getInstance();
            GregorianCalendar gc = (GregorianCalendar) calendar;
            calendar.set(Calendar.YEAR, Integer.valueOf(gc.get(1)));
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DATE, 31);
            sql = sql.replace("{$currentYearEnd}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //去年初
        if (sql.contains("{$lastYearBegin}")) {
            calendar = Calendar.getInstance();
            GregorianCalendar gc = (GregorianCalendar) calendar;
            calendar.set(Calendar.YEAR, Integer.valueOf(gc.get(1)) - 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DATE, 1);
            sql = sql.replace("{$lastYearBegin}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //去年末
        if (sql.contains("{$lastYearEnd}")) {
            calendar = Calendar.getInstance();
            GregorianCalendar gc = (GregorianCalendar) calendar;
            calendar.set(Calendar.YEAR, Integer.valueOf(gc.get(1)) - 1);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DATE, 31);
            sql = sql.replace("{$lastYearEnd}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //上月初
        if (sql.contains("{$lastMonthBegin}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            sql = sql.replace("{$lastMonthBegin}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //上月末
        if (sql.contains("{$lastMonthEnd}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            sql = sql.replace("{$lastMonthEnd}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //本月初
        if (sql.contains("{$currentMonthBegin}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            sql = sql.replace("{$currentMonthBegin}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }
        //本月末
        if (sql.contains("{$currentMonthEnd}")) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            sql = sql.replace("{$currentMonthEnd}", "'" + sdf.format(calendar.getTime()) + " 00:00:00" + "'");
        }

        return sql;
    }


    public Map testExecuteSql(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String sql = (String) paramsMap.get("sql");
        sql = "select top 10 * from (" + sql + ")as a";
        //解析sql
        String viewSql = decodeSql(sql, "testExecute");
        //执行sql
        Map map = ExecuteSql(viewSql);
        return map;
    }


    /**
     * 执行sql
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public Map<String, Object> ExecuteSql(String sql) throws Exception {
        QueryParamHelper.checkFilterCondition(sql);
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> columnList = new ArrayList<>();
        try {
            Properties prop = new Properties();
            //读取配置文件 数据库账密
            InputStream is = new FileInputStream(ClassUtils.getDefaultClassLoader().getResource("").getPath() + "tool/mybatis/db.properties");
            prop.load(is);
            String driver = prop.getProperty("db.driver");
            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            //注册jdbc驱动
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            //获得结果集结构信息,元数据
            ResultSetMetaData md = resultSet.getMetaData();
            //获得列数
            int columnCount = md.getColumnCount();
            //获取所有的列
            for (int i = 0; i < columnCount; i++) {
                columnList.add(md.getColumnName(i + 1));
            }
            while (resultSet.next()) {
                Map<String, Object> rowData = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), resultSet.getObject(i));
                }
                list.add(rowData);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("columnList", columnList);
        map.put("list", list);
        return map;

    }


    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Set<String> getSqlParams(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        String sql = (String) paramsMap.get("sql");
        Set<String> list = new LinkedHashSet<>();
        if (StringUtil.isEmpty(sql)) {
            return list;
        }
        String[] sqlArr = sql.split("#");
        for (int i = 1; i < sqlArr.length; i += 2) {
            if (sqlArr[i].contains(":")) {
                list.add(sqlArr[i].substring(sqlArr[i].indexOf(":") + 1, sqlArr[i].indexOf("}")));
            }
        }

        return list;
    }

    public Map addReport(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        Date date = new Date();
        //报表id
        String id = (String) paramsMap.get("id");
        ReportDesignBo bo = addReportCommon(paramsMap, date, storeId);

        //如果id为空,则是新增,否则是修改
        if (StringUtil.isEmpty(id)) {
            bo.getReportDesign().setPublishStatus("publishStatus-noPublish");
            id = domain.addReport(bo, userId, date);
        } else {
            domain.mdfReport(bo, date);
        }
        Map map = new HashMap();
        map.put("id", id);
        return map;
    }

    public ReportDesignBo addReportCommon(Map<String, Object> paramsMap, Date date, String storeId) {
        paramsMap.remove(GeneralParam.LOGIN_LANGID_KEY);
        paramsMap.remove(GeneralParam.LOGIN_USERID_KEY);
        paramsMap.remove(GeneralParam.LOGIN_STOREID_KEY);
        //将报表配置装换成json保存
        String config = JacksonUtil.toJSon(paramsMap);

        //报表说明
        String reportMemo = (String) paramsMap.get("reportMemo");
        //所有的列配置
        List<Map<String, Object>> columns = (List<Map<String, Object>>) paramsMap.get("columns");
        //报表类别
        List<String> publishList = (List<String>) paramsMap.get("publishList");
        //报表id
        String id = (String) paramsMap.get("id");

        //封装参数到domain层
        ReportDesignBo bo = new ReportDesignBo();
        List<ReportRelation> reportRelationList = new ArrayList<>();
        //报表类别
        String types = "";
        if (publishList.size() == 0) {
            types = "普通报表";
        }


        //删除所有关联关系
        if (!StringUtil.isEmpty(id)) {
            //删除关系之前先解除关系
            List<Map> relationList = mapper.getReportRelation(id);
            for (Map map : relationList) {
                String newType = "";
                //关系类型
                String relationType = (String) map.get("relationType");
                if ("chain".equals(relationType)) {
                    continue;
                }
                //关联的报表
                String childReportId = (String) map.get("childReportId");
                Map reportMap = mapper.getReportById(childReportId);
                String reportType = (String) reportMap.get("reportType");
                switch (relationType) {
                    case "father_son":
                        if (reportType.equals("子报表")) {
                            newType = "普通报表";
                        } else {
                            String[] arr = reportType.split(",");
                            for (String str : arr) {
                                if (!str.equals("子报表")) {
                                    newType += str + ",";
                                }
                            }
                        }
                        break;
                    case "group":
                        int a = mapper.getRelationByChild(childReportId, "combinParent");
                        if (a > 1) {
                            newType = relationType;
                            break;
                        }

                        if (reportType.equals("组合子报表")) {
                            newType = "普通报表";
                        } else {
                            String[] arr = reportType.split(",");
                            for (String str : arr) {
                                if (!str.equals("组合子报表")) {
                                    newType += str + ",";
                                }
                            }
                        }
                        break;
                }

                if (newType.endsWith(",")) {
                    newType = newType.substring(0, newType.length() - 1);
                }
                ReportDesignBo boss = new ReportDesignBo();
                ReportDesign res = new ReportDesign();
                res.setId(childReportId);
                res.setReportType(newType);
                boss.setReportDesign(res);
                domain.mdfReport(boss, new Date());


            }
            //关系处理完毕,全部删除
            mapper.delReportRelation(id);
        }

        String chartsType = "";

        for (String type : publishList) {
            switch (type) {
                //链式报表
                case "chain":
                    for (Map<String, Object> chainMap : columns) {
                        //获取链式关联
                        List<Map<String, Object>> links = (List<Map<String, Object>>) chainMap.get("chainData");
                        if (links != null) {
                            boolean flag = false;
                            for (Map<String, Object> link : links) {
                                if (link.get("reportId") != null) {
                                    //新建关联关系
                                    ReportRelation reportRelation = new ReportRelation();
                                    reportRelation.setChildReportId((String) link.get("reportId"));
                                    reportRelation.setRelationType("chain");
                                    reportRelation.setRelationState("链式关系");
                                    reportRelationList.add(reportRelation);
                                    flag = true;
                                }
                            }
                            if (flag) {
                                types += "链式报表,";
                            }

                        }

                    }
                    break;
                //父子报表
                case "father_son":
                    //父子报表的配置
                    Map<String, Object> joinReportsMap = (Map<String, Object>) paramsMap.get("joinReports");
                    String reportId = (String) joinReportsMap.get("reportId");
                    ReportDesign reportDesign = domain.selectById(reportId);
                    if (reportDesign != null) {
                        String reportType = reportDesign.getReportType();
                        if (reportType.equals("普通报表")) {
                            reportDesign.setReportType("子报表");
                        } else {
                            String[] arr = reportType.split(",");
                            boolean flag = false;
                            for (String str : arr) {
                                if (!str.equals("子报表")) {
                                    flag = true;
                                }
                            }

                            if (flag) {
                                reportType += ",子报表";
                            }
                            reportDesign.setReportType(reportType);

                        }

                        ReportDesignBo reportDesignBo = new ReportDesignBo();
                        ReportDesign reportDesign1 = new ReportDesign();
                        reportDesign1.setId(reportDesign.getId());
                        reportDesign1.setReportType(reportDesign.getReportType());
                        reportDesignBo.setReportDesign(reportDesign1);
                        domain.mdfReport(reportDesignBo, date);

                        //新建关联关系
                        ReportRelation reportRelation = new ReportRelation();
                        reportRelation.setChildReportId(reportId);
                        reportRelation.setRelationType("father_son");
                        reportRelation.setRelationState("父子关系");
                        reportRelationList.add(reportRelation);
                        types += "父报表,";

                    }
                    break;
                //组合报表
                case "group":
                    //组合报表配置
                    List<Map<String, Object>> combinReports = (List<Map<String, Object>>) paramsMap.get("combinReports");
                    for (Map<String, Object> map : combinReports) {
                        //获取id
                        String combinId = (String) map.get("reportId");
                        ReportDesign reportDesign1 = domain.selectById(combinId);
                        if (reportDesign1 != null) {
                            String reportType = reportDesign1.getReportType();
                            if (reportType.equals("普通报表")) {
                                reportDesign1.setReportType("组合子报表");
                            } else {
                                String[] arr = reportType.split(",");
                                boolean flag = false;
                                for (String str : arr) {
                                    if (str.equals("组合子报表")) {
                                        flag = true;
                                    }
                                }

                                if (!flag) {
                                    reportType += ",组合子报表";
                                }
                                reportDesign1.setReportType(reportType);

                            }

                            ReportDesignBo reportDesignBo = new ReportDesignBo();
                            ReportDesign reportDesign2 = new ReportDesign();
                            reportDesign2.setId(reportDesign1.getId());
                            reportDesign2.setReportType(reportDesign1.getReportType());
                            reportDesignBo.setReportDesign(reportDesign2);
                            domain.mdfReport(reportDesignBo, date);

                            //新建关联关系
                            ReportRelation reportRelation = new ReportRelation();
                            reportRelation.setChildReportId(combinId);
                            reportRelation.setRelationType("group");
                            reportRelation.setRelationState("组合父子关系");
                            reportRelationList.add(reportRelation);
                            types += "组合父报表,";
                        }

                    }
                    break;
                case "chart":
                    types += "图形报表,";
                    //还需要将图标类型给加上
                    List<Map<String, Object>> charts = (List<Map<String, Object>>) paramsMap.get("charts");
                    if (charts != null) {
                        for (Map<String, Object> map : charts) {
                            chartsType += map.get("name") + ",";
                        }
                    }

                    break;
                case "pivot":
                    types += "交叉报表,";
                    break;
            }
        }
        if (types.endsWith(",")) {
            types = types.substring(0, types.length() - 1);
        }
        if (chartsType.endsWith(",")) {
            chartsType = chartsType.substring(0, chartsType.length() - 1);
        }
        //sql
        String editorValue = (String) paramsMap.get("editorValue");

        //报表详情Map
        Map<String, Object> reportForm = (Map<String, Object>) paramsMap.get("reportForm");
        //报表名称
        String reportName = (String) reportForm.get("reportName");
        //报表类别
        String reportType = (String) reportForm.get("reportType");


        //封装参数
        ReportDesign reportDesign = new ReportDesign();
        reportDesign.setId(id);
        reportDesign.setReportName(reportName);
        reportDesign.setCategory(reportType);
        reportDesign.setReportType(types);
        reportDesign.setChartType(chartsType);
        reportDesign.setReportState(reportMemo);
        reportDesign.setSql(editorValue);
        reportDesign.setCreateStore("总部[0000]");
        reportDesign.setCreateStoreId(storeId);
        reportDesign.setConfig(config);
        bo.setReportDesign(reportDesign);

        bo.setReportRelationList(reportRelationList);
        return bo;
    }

    public Map publishReport(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        Date date = new Date();
        //报表id
        String id = (String) paramsMap.get("id");
        //解析参数,封装入参
        ReportDesignBo bo = addReportCommon(paramsMap, date, storeId);

        //如果id为空,则是新增,否则是修改
        if (StringUtil.isEmpty(id)) {
            bo.getReportDesign().setPublishStatus("publishStatus-publish");
            id = domain.addReport(bo, userId, date);
        } else {
            domain.mdfReport(bo, date);

        }
        //记录版本历史
        ReportDesignHistory reportDesignHistory = ReportDesign.toReportHistory(bo.getReportDesign());
        reportDesignHistory.setReportId(bo.getReportDesign().getId());
        reportDesignHistory.setConfig(bo.getReportDesign().getConfig());
        reportDesignHistory.setPublishStatus("publishStatus-publish");
        domain.addReportHistory(reportDesignHistory, userId, date);

        Map map = new HashMap();
        map.put("id", id);
        return map;

    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Map> getMenuList(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        return mapper.getMenuList(langId);
    }

    public void reportBindMenu(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        String menuId = (String) paramsMap.get("menuId");
        List<String> reportIdList = (List<String>) paramsMap.get("reportIdList");
        //取最后那个报表来绑定
        if (reportIdList.size() > 0) {

            String reportId = reportIdList.get(reportIdList.size() - 1);
            Map report = mapper.getReportById(reportId);
            if (report == null) {
                return;
            }
            if (!"publishStatus-publish".equals(report.get("publishStatus"))) {
                throw new RuntimeException("未发布报表无法绑定菜单");
            }

            if ("子报表".equals(report.get("reportType"))) {
                throw new RuntimeException("子报表无法当做单独模块出现,无法绑定");
            }

            //将这个报表id与所有绑定的菜单解除关联
            mapper.unbundMenu(reportId);
            //绑定菜单
            int a = mapper.reportBindMenu(reportId, menuId);
            if (a <= 0) {
                throw new RuntimeException("绑定失败");
            }
        }

    }

    public List<Map> getReportHistoryList(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        //报表id
        String id = (String) paramsMap.get("id");
        return mapper.getReportHistoryList(id);
    }

    public Map getReportHistoryDetail(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        //历史报表id
        String id = (String) paramsMap.get("id");
        return null;
    }

    public Map getReportDetail(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        //报表id
        String id = (String) paramsMap.get("id");
        ReportDesign reportDesign = domain.selectById(id);
        if (reportDesign == null) {
            return null;
        }
        Map map = JacksonUtil.readValue(reportDesign.getConfig(), Map.class);
        map.put("id", reportDesign.getId());
        return map;
    }

    /**
     * 解析sql,然后执行
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public Map searchReport(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        //报表id
        String id = (String) paramsMap.get("id");
        Integer page = (Integer) paramsMap.get("page");
        if (page == null) {
            page = 1;
        }
        Integer pageSize = (Integer) paramsMap.get("pageSize");

        //类型,如果是export则代表导出全部
        String type = (String) paramsMap.get("type");
        ReportDesign reportDesign = domain.selectById(id);
        if (reportDesign == null) {
            throw new RuntimeException("报表不存在");
        }
        //获取报表配置
        Map<String, Object> config = JacksonUtil.readValue(reportDesign.getConfig(), Map.class);
        //获取sql
        String sql = reportDesign.getSql();
        //条件数组
        List<Map<String, Object>> paramList = (List<Map<String, Object>>) paramsMap.get("paramList");
        //条件配置
        List<Map<String, Object>> queryConditionList = (List<Map<String, Object>>) config.get("queryConditionList");
        //排序和分页配置
        Map<String, Object> reportForm = (Map<String, Object>) config.get("reportForm");
        //该sql对应的所有列
        List<Map<String, Object>> columnList = (List<Map<String, Object>>) config.get("columns");


        //解析sql
        String veiwSql = decodeSql(sql, paramList, queryConditionList, reportForm);
        //用来计算总条数
        String totalVeiwSql = veiwSql;
        if (pageSize == null) {
            Object obj = reportForm.get("pageSize");
            if (obj != "") {
                pageSize = (Integer) obj;
            }
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //如果是导出,则查全部结果
        if ("export".equals(type)) {
            pageSize = 500000;
        }

        Integer startRow = (page - 1) * pageSize;

        if (veiwSql.indexOf("order by") == -1) {
            veiwSql = veiwSql + " order by " + columnList.get(0).get("columnName") + " offset " + startRow + " row fetch next " + pageSize + " rows only";
            totalVeiwSql = totalVeiwSql + " order by " + columnList.get(0).get("columnName") + " offset " + 0 + " row fetch next " + 500000 + " rows only";
        } else {
            veiwSql = veiwSql + " offset " + startRow + " row fetch next " + pageSize + " rows only";
            totalVeiwSql = totalVeiwSql + " offset " + 0 + " row fetch next " + 500000 + " rows only";
        }
        log.error("sql拼接:" + veiwSql);


        //过滤条件
        String filterCondition = (String) paramsMap.get("filterCondition");
        //检查过滤条件
        QueryParamHelper.checkFilterCondition(filterCondition);
        if (!StringUtil.isEmpty(filterCondition)) {
            //将过滤条件拼到sql中
            veiwSql = "select * from (" + veiwSql + ") as b where " + filterCondition;
            totalVeiwSql = "select * from (" + totalVeiwSql + ") as b where " + filterCondition;
        }
        log.error("sql拼接:" + totalVeiwSql);

        String totalVeiwSqlBoss = "select count(1) as total from (" + totalVeiwSql + ") as a";
        log.error("sql拼接:" + totalVeiwSqlBoss);
        Map<String, Object> veiwMap = ExecuteSql(totalVeiwSqlBoss);
        //数据集
        List<Map<String, Object>> veiwlist = (List<Map<String, Object>>) veiwMap.get("list");

        Integer total = (Integer) veiwlist.get(0).get("total");

        Map<String, Object> map = ExecuteSql(veiwSql);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");


        List<Map> sumList = new ArrayList<>();
        //计算合计列
        for (Map<String, Object> columnDetail : columnList) {
            Map sumMap = new HashMap();
            String sumSql = "";
            //合计函数
            String columnSumFun = (String) columnDetail.get("columnSumFun");
            if (StringUtil.isEmpty(columnSumFun)) {
                continue;
            }
            //合计列
            String columnName = (String) columnDetail.get("columnName");
            String columnSumTitle = (String) columnDetail.get("columnSumTitle");
            if (StringUtil.isEmpty(columnSumTitle)) {
                columnSumTitle = columnName;
            }
            //合计函数
            switch (columnSumFun) {
                case "sum":
                    sumSql = "select sum(" + columnName + ") as sumFun from (" + totalVeiwSql + ") as a";
                    break;
                case "max":
                    sumSql = "select max(" + columnName + ") as sumFun from (" + totalVeiwSql + ") as a";
                    break;
                case "min":
                    sumSql = "select min(" + columnName + ") as sumFun from (" + totalVeiwSql + ") as a";
                    break;
                case "average":
                    sumSql = "select avg(" + columnName + ") as sumFun from (" + totalVeiwSql + ") as a";
                    break;
                case "count":
                    sumSql = "select count(" + columnName + ") as sumFun from (" + totalVeiwSql + ") as a";
                    break;
                case "distinct-count":
                    sumSql = "select count( distinct " + columnName + ") as sumFun from (" + totalVeiwSql + ") as a";
                    break;
            }
            Map<String, Object> sumFun = ExecuteSql(sumSql);
            List<Map<String, Object>> sumFunList = (List<Map<String, Object>>) sumFun.get("list");
            String value = (String) sumFunList.get(0).get("sumFun");
            if (value == null) {
                value = "0";
            }
            sumMap.put("columnSumTitle", columnSumTitle);
            sumMap.put("value", value);
            sumList.add(sumMap);


        }


        //封装返回参数
        Map value = new HashMap();
        value.put("list", list);
        value.put("total", total);
        value.put("columnSumFunList", sumList);

        return value;
    }


    /**
     * 解析sql,将带有的系统变量给替换成用户输入的具体的值
     *
     * @param sql
     * @param paramList          用户输入的条件数据
     * @param queryConditionList 配置信息
     * @param reportForm         分页和排序参数
     * @return
     */
    public String decodeSql(String sql, List<Map<String, Object>> paramList, List<Map<String, Object>> queryConditionList, Map<String, Object> reportForm) {
        String viewSql = sql;
        if (StringUtil.isEmpty(sql)) {
            return viewSql;
        }


        if (paramList == null) {
            paramList = new ArrayList<>();
        }
        if (queryConditionList == null) {
            queryConditionList = new ArrayList<>();
        }

        //将sql中的时间变量给转换成真实值
        viewSql = decodeDate(sql);
        //将空格和回车符号给删除
        viewSql = viewSql.replaceAll("\r|\n", "");
        //将变量给切割出来
        String[] sqlArr = viewSql.split("#");

        if (paramList.size() == 0) {
            for (int i = 1; i < sqlArr.length; i += 2) {
                if (sqlArr[i].contains("{order by}")) {
                    //排序字段
                    String sortName = (String) reportForm.get("sortName");
                    //升序:up  降序:down
                    String sortType = (String) reportForm.get("sortType");
                    if (StringUtil.isEmpty(sortName)) {
                        viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                    } else {
                        if ("up".equals(sortType)) {
                            viewSql = viewSql.replace("#" + sqlArr[i] + "#", " order by " + sortName);
                        } else if ("down".equals(sortType)) {
                            viewSql = viewSql.replace("#" + sqlArr[i] + "#", " order by " + sortName + " desc");
                        }

                    }
                    continue;
                }
            }
            return decodeSql(viewSql, "testExecute");
        }

        for (int i = 1; i < sqlArr.length; i += 2) {
            if (sqlArr[i].contains("{queryCondition}")) {
                //组装要替换的动态变量值
                String replaceBoss = "";
                for (Map<String, Object> map : queryConditionList) {
                    //disable为null,则代表是动态参数
                    if (map.get("disabled") == null) {
                        for (Map<String, Object> paramsMap : paramList) {
                            //获取到的列名
                            String colunmName = (String) map.get("queryName");
                            Object obj = paramsMap.get(colunmName);
                            if (obj == null) {
                                continue;
                            }
                            //多选sql
                            String optionalSQLValue = (String) map.get("optionalSQLValue");
                            //多选数组
                            List<Map> optionalHandleList = (List<Map>) map.get("optionalHandleList");
                            //是否多选
                            Boolean isMultiply = (Boolean) map.get("isMultiply");

                            //用户的参数,预防多选情况
                            List<String> queryNameList = new ArrayList<>();
                            if ((!StringUtil.isEmpty(optionalSQLValue) || optionalHandleList.size() > 0) && isMultiply == true) {
                                //获取到的列的值,为数组
                                queryNameList = (List<String>) paramsMap.get(colunmName);
                            } else {
                                String queryName = (String) paramsMap.get("colunmName");
                                if (!StringUtil.isEmpty(queryName)) {
                                    queryNameList.add(queryName);
                                }

                            }

                            //如果为空,则该动态条件不生效
                            if (queryNameList.size() == 0) {
                                continue;
                            }

                            String replace = "";
                            if (queryNameList.size() == 1) {
                                replace = " and " + colunmName + " = '" + queryNameList.get(0) + "'";
                            } else {
                                for (int k = 0; k < queryNameList.size(); k++) {
                                    if (k == 0) {
                                        replace += " and (" + colunmName + " = '" + queryNameList.get(k) + "'";
                                        continue;
                                    } else if (k > 0 && k < queryNameList.size() - 1) {
                                        replace += " or " + colunmName + " = '" + queryNameList.get(k) + "'";
                                        continue;
                                    } else if (k == queryNameList.size() - 1) {
                                        replace += " or " + colunmName + " = '" + queryNameList.get(k) + "')";
                                        continue;
                                    }

                                }
                            }


                            //获取操作符
                            String operateParam = (String) map.get("operateParam");

                            switch (operateParam) {
                                case "eq":
                                    break;
                                case "gt":
                                    replace = replace.replace("=", ">");
                                    break;
                                case "egt":
                                    replace = replace.replace("=", ">=");
                                    break;
                                case "lt":
                                    replace = replace.replace("=", "<");
                                    break;
                                case "elt":
                                    replace = replace.replace("=", "<=");
                                    break;
                                case "neq":
                                    replace = replace.replace("=", "!=");
                                    break;
                                case "like":
                                    if (queryNameList.size() == 1) {
                                        replace = " and " + colunmName + " like '%" + queryNameList.get(0) + "%'";
                                    } else {
                                        for (int k = 0; k < queryNameList.size(); k++) {
                                            if (k == 0) {
                                                replace += " and (" + colunmName + " like '%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k > 0 && k < queryNameList.size() - 1) {
                                                replace += " or " + colunmName + " like '%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k == queryNameList.size() - 1) {
                                                replace += " or " + colunmName + " like '%" + queryNameList.get(k) + "%')";
                                                continue;
                                            }

                                        }
                                    }
                                    break;
                                case "notlike":
                                    if (queryNameList.size() == 1) {
                                        replace = " and " + colunmName + " not like '%" + queryNameList.get(0) + "%'";
                                    } else {
                                        for (int k = 0; k < queryNameList.size(); k++) {
                                            if (k == 0) {
                                                replace += " and (" + colunmName + " not like '%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k > 0 && k < queryNameList.size() - 1) {
                                                replace += " or " + colunmName + " not like '%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k == queryNameList.size() - 1) {
                                                replace += " or " + colunmName + " not like '%" + queryNameList.get(k) + "%')";
                                                continue;
                                            }

                                        }
                                    }
                                    break;
                            }

                            //将获取到的动态条件给拼成一个大的字符串
                            replaceBoss += replace;


                        }
                    }


                }
                //将动态变量给替换成拼接好的sql
                viewSql = viewSql.replace("#" + sqlArr[i] + "#", replaceBoss);
                continue;
            }
            if (sqlArr[i].contains("{order by}")) {
                //排序字段
                String sortName = (String) reportForm.get("sortName");
                //升序:up  降序:down
                String sortType = (String) reportForm.get("sortType");
                if (StringUtil.isEmpty(sortName)) {
                    viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                } else {
                    if ("up".equals(sortType)) {
                        viewSql = viewSql.replace("#" + sqlArr[i] + "#", " order by " + sortName);
                    } else if ("down".equals(sortType)) {
                        viewSql = viewSql.replace("#" + sqlArr[i] + "#", " order by " + sortName + " desc");
                    }

                }
                continue;
            }
            if (sqlArr[i].startsWith("<")) {
                String param = sqlArr[i].substring(sqlArr[i].indexOf("<") + 1, sqlArr[i].lastIndexOf(">")).trim();
                boolean flag = false;
                String replace = "";
                for (Map<String, Object> map : paramList) {
                    //不解释代码
                    for (Map<String, Object> queryMap : queryConditionList) {
                        String queryName = (String) queryMap.get("queryName");
                        if (queryName.equals(param)) {
                            //多选sql
                            String optionalSQLValue = (String) queryMap.get("optionalSQLValue");
                            //多选数组
                            List<Map> optionalHandleList = (List<Map>) queryMap.get("optionalHandleList");
                            //是否多选
                            Boolean isMultiply = (Boolean) queryMap.get("isMultiply");

                            if ((!StringUtil.isEmpty(optionalSQLValue) || optionalHandleList.size() > 0) && isMultiply == true) {
                                //获取到的列的值,为数组
                                List<String> queryNameList = (List<String>) map.get(param);
                                for (String str : queryNameList) {
                                    replace += str;
                                }
                            } else {
                                //用户输入的参数
                                String str = (String) map.get(param);
                                if (!StringUtil.isEmpty(str)) {
                                    flag = true;
                                    replace = str;
                                }

                            }
                        }
                    }

                }

                if (flag) {
                    viewSql = viewSql.replace("#" + sqlArr[i] + "#", replace);
                } else {
                    viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                }
                continue;
            }
            if (sqlArr[i].contains(":")) {
                //列名
                String param = sqlArr[i].substring(sqlArr[i].indexOf(":") + 1, sqlArr[i].lastIndexOf("}")).trim();
                for (Map<String, Object> map : paramList) {

                    boolean flag = false;
                    Object value = map.get(param);
                    if (value == null) {
                        continue;
                    }
                    for (Map<String, Object> pMap : queryConditionList) {
                        //列名
                        String colum = (String) pMap.get("queryName");

                        if (param.equals(colum)) {

                            //多选sql
                            String optionalSQLValue = (String) pMap.get("optionalSQLValue");
                            //多选数组
                            List<Map> optionalHandleList = (List<Map>) pMap.get("optionalHandleList");
                            //是否多选
                            Boolean isMultiply = (Boolean) pMap.get("isMultiply");
                            //用户输入的参数转换成数组,因为有多选
                            List<String> queryNameList = new ArrayList<>();
                            //判断是否是数组还是字符串
                            if ((!StringUtil.isEmpty(optionalSQLValue) || optionalHandleList.size() > 0) && isMultiply == true) {
                                //获取到的列的值,为数组
                                queryNameList = (List<String>) value;
                            } else {
                                //用户输入的参数
                                String str = (String) value;
                                if (!StringUtil.isEmpty(str)) {
                                    queryNameList.add(str);
                                }

                            }

                            //为空,则条件不生效
                            if (queryNameList.size() == 0) {
                                viewSql = viewSql.replace("#" + sqlArr[i] + "#", "");
                                break;
                            }


                            //操作符
                            String operateParam = (String) pMap.get("operateParam");

                            //条件
                            String condition = sqlArr[i].substring(sqlArr[i].indexOf("{") + 1, sqlArr[i].indexOf(":"));
                            //要替换的参数
                            String replace = "";
                            if (queryNameList.size() == 1) {
                                replace = " and " + condition + " = '" + queryNameList.get(0) + "'";
                            } else {
                                for (int k = 0; k < queryNameList.size(); k++) {
                                    if (k == 0) {
                                        replace += " and (" + condition + " = " + "'" + queryNameList.get(k) + "'";
                                        continue;
                                    } else if (k > 0 && k < queryNameList.size() - 1) {
                                        replace += " or " + condition + " = " + "'" + queryNameList.get(k) + "'";
                                        continue;
                                    } else if (k == queryNameList.size() - 1) {
                                        replace += " or " + condition + " = " + "'" + queryNameList.get(k) + "')";
                                        continue;
                                    }

                                }
                            }


                            switch (operateParam) {
                                case "eq":
                                    break;
                                case "gt":
                                    replace = replace.replace("=", ">");
                                    break;
                                case "egt":
                                    replace = replace.replace("=", ">=");
                                    break;
                                case "lt":
                                    replace = replace.replace("=", "<");
                                    break;
                                case "elt":
                                    replace = replace.replace("=", "<=");
                                    break;
                                case "neq":
                                    replace = replace.replace("=", "!=");
                                    break;
                                case "like":
                                    if (queryNameList.size() == 1) {
                                        replace = " and " + condition + " like " + "'%" + queryNameList.get(0) + "%'";
                                    } else {
                                        for (int k = 0; k < queryNameList.size(); k++) {
                                            if (k == 0) {
                                                replace += " and (" + condition + " like " + "'%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k > 0 && k < queryNameList.size() - 1) {
                                                replace += " or " + condition + " like " + "'%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k == queryNameList.size() - 1) {
                                                replace += " or " + condition + " like " + "'%" + queryNameList.get(k) + "%')";
                                                continue;
                                            }

                                        }
                                    }
                                    break;
                                case "notlike":
                                    if (queryNameList.size() == 1) {
                                        replace = " and " + condition + " not like " + "'%" + queryNameList.get(0) + "%'";
                                    } else {
                                        for (int k = 0; k < queryNameList.size(); k++) {
                                            if (k == 0) {
                                                replace += " and (" + condition + " not like " + "'%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k > 0 && k < queryNameList.size() - 1) {
                                                replace += " or " + condition + " not like " + "'%" + queryNameList.get(k) + "%'";
                                                continue;
                                            } else if (k == queryNameList.size() - 1) {
                                                replace += " or " + condition + " not like " + "'%" + queryNameList.get(k) + "%')";
                                                continue;
                                            }

                                        }
                                    }
                                    break;
                            }

                            //将拼好的字符串替换掉系统变量
                            viewSql = viewSql.replace("#" + sqlArr[i] + "#", replace);
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }

                }

            }

        }

        return viewSql;
    }


    public Set<String> getSysVarById(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        //报表id
        String id = (String) paramsMap.get("id");
        ReportDesign reportDesign = domain.selectById(id);
        if (reportDesign == null) {
            return null;
        }
        paramsMap.put("sql", reportDesign.getSql());
        return getSqlParams(paramsMap);
    }

    public List<Map> getReportListByName(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);
        //报表名称
        String reportName = (String) paramsMap.get("reportName");
        //类型
        String type = (String) paramsMap.get("type");
        if (!StringUtil.isEmpty(reportName)) {
            reportName = "%" + reportName + "%";
        }

        List<Map> list = new ArrayList<>();

        if ("chain".equals(type)) {
            list = mapper.getReportListByName(reportName, "", "");
        } else if ("parent".equals(type)) {
            list = mapper.getReportListByName(reportName, "aaa", "");
        } else if ("combinParent".equals(type)) {
            list = mapper.getReportListByName(reportName, "", "aaa");
        }

        for (int i = 0; i < list.size(); i++) {
            paramsMap.put("sql", list.get(i).get("sql"));
            list.get(i).put("params", getSqlParams(paramsMap));
        }


        return list;
    }

    public List<Map> getReportListByIdArr(Map<String, Object> paramsMap) {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        //报表id数组
        List<String> ids = (List<String>) paramsMap.get("ids");
        //封装返回数据
        List<Map> list = new ArrayList<>();
        for (String id : ids) {
            Map map = mapper.getReportById(id);
            list.add(map);
        }

        return list;
    }

    public Map getCharts(Map<String, Object> paramsMap) throws Exception {
        // 通用参数
        String langId = (String) paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String) paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String storeId = (String) paramsMap.get(GeneralParam.LOGIN_STOREID_KEY);

        String id = (String) paramsMap.get("id");
        ReportDesign reportDesign = domain.selectById(id);
        if (reportDesign == null) {
            return null;
        }

        //获取报表配置
        Map<String, Object> config = JacksonUtil.readValue(reportDesign.getConfig(), Map.class);
        //获取sql
        String sql = reportDesign.getSql();
        //条件数组
        List<Map<String, Object>> paramList = (List<Map<String, Object>>) paramsMap.get("paramList");
        //条件配置
        List<Map<String, Object>> queryConditionList = (List<Map<String, Object>>) config.get("queryConditionList");
        //排序和分页配置
        Map<String, Object> reportForm = (Map<String, Object>) config.get("reportForm");
        //该sql对应的所有列
        List<Map<String, Object>> columnList = (List<Map<String, Object>>) config.get("columns");
        //获取图表配置
        List<Map<String, Object>> charts = (List<Map<String, Object>>) config.get("charts");

        Integer page = 1;
        Integer pageSize = 100;
        if (charts.size() > 0) {
            String scope = (String) config.get("scope");
            if ("all".equals(scope)) {
                pageSize = 500000;
            }

        }
        //解析sql
        String veiwSql = decodeSql(sql, paramList, queryConditionList, reportForm);


        Integer startRow = (page - 1) * pageSize;

        if (veiwSql.indexOf("order by") == -1) {
            veiwSql = veiwSql + " order by " + columnList.get(0).get("columnName") + " offset " + startRow + " row fetch next " + pageSize + " rows only";
        } else {
            veiwSql = veiwSql + " offset " + startRow + " row fetch next " + pageSize + " rows only";
        }
        log.error("sql拼接:" + veiwSql);


        //过滤条件
        String filterCondition = (String) paramsMap.get("filterCondition");
        //检查过滤条件
        QueryParamHelper.checkFilterCondition(filterCondition);
        if (!StringUtil.isEmpty(filterCondition)) {
            //将过滤条件拼到sql中
            veiwSql = "select * from (" + veiwSql + ") as b where " + filterCondition;
        }

        //封装返回数据
        Map result = new HashMap();

        for (Map<String, Object> chartsMap : charts) {
            //图表类型
            String type = (String) chartsMap.get("type");
            //该图形对应的id
            String chartId = chartsMap.get("id") + "";
            if (StringUtil.isEmpty(chartId)) {
                continue;
            }
            //封装返回参数
            List<Map<String, Object>> resultList = new ArrayList<>();


            if ("line".equals(type)) {
                //折线图
                resultList = lineOrColumnNormalChart(chartsMap, veiwSql, resultList);
            } else if ("column_normal".equals(type)) {
                //堆叠柱状图
                resultList = lineOrColumnNormalChart(chartsMap, veiwSql, resultList);
            } else if ("column_percent".equals(type)) {
                //百分比柱状图
                resultList = columnPercentChart(chartsMap, veiwSql, resultList);
            } else if ("column_contrast".equals(type)) {
                //对比柱状图
                resultList = lineOrColumnNormalChart(chartsMap, veiwSql, resultList);
            } else if ("pie".equals(type)) {
                //饼状图
                resultList = columnPercentChart(chartsMap, veiwSql, resultList);
            } else if ("area_normal".equals(type)) {
                //堆叠面积图
                resultList = lineOrColumnNormalChart(chartsMap, veiwSql, resultList);
            } else if ("area_percent".equals(type)) {
                //百分比堆叠面积图
                resultList = columnPercentChart(chartsMap, veiwSql, resultList);
            } else if ("scatter".equals(type)) {
                //分散图
                resultList = columnPercentChart(chartsMap, veiwSql, resultList);
            } else if ("combin".equals(type)) {
                //联合折线图
                resultList = lineOrColumnNormalChart(chartsMap, veiwSql, resultList);
            }

            result.put(chartId, resultList);

        }


        return result;
    }


    public List<Map<String, Object>> lineOrColumnNormalChart(Map<String, Object> chartsMap, String veiwSql, List<Map<String, Object>> resultList) throws Exception {
        //获取配置的数据
        List<Map<String, Object>> dataModels = (List<Map<String, Object>>) chartsMap.get("dataModels");

        for (Map<String, Object> dataModel : dataModels) {
            //封装返回参数
            Map<String, Object> returnMap = new HashMap<>();

            //获取每个数据里的类别
            Map<String, Object> category = (Map<String, Object>) dataModel.get("category");
            if (category == null || category.isEmpty()) {
                break;
            }
            //获取类别中的运算公式
            String expressionCa = (String) category.get("expression");
            //获取类别中列名
            String columnCa = (String) category.get("column");
            //获取类别中升降序
            String directionCa = (String) category.get("direction");
            //公式或者列名
            String exOrColumnCa = columnCa;
            if (!StringUtil.isEmpty(expressionCa)) {
                exOrColumnCa = expressionCa;
            }
            //拼接sql
            String groupBySql = "";
            groupBySql = "select (" + exOrColumnCa + ") as " + columnCa;

            //获取系列
            Map<String, Object> series = (Map<String, Object>) dataModel.get("series");
            String directionSe = "";
            String exOrColumnSe = "";
            String columnSe = "";
            if (series != null && !series.isEmpty()) {
                //获取系列中列名
                columnSe = (String) series.get("column");
                if (!StringUtil.isEmpty(columnSe) && !columnSe.equals(columnCa)) {
                    //获取系列中运算公式
                    String expressionSe = (String) series.get("expression");

                    //获取系列中升降序
                    directionSe = (String) series.get("direction");
                    //公式或者列名
                    exOrColumnSe = columnSe;
                    if (!StringUtil.isEmpty(expressionSe)) {
                        exOrColumnSe = expressionSe;
                    }
                    groupBySql = groupBySql + ",(" + exOrColumnSe + ") as " + columnSe;
                }


            }

            //纵坐标
            Map map = new HashMap();
            String columnL = "";
            //左值轴
            Map<String, Object> leftAxis = (Map<String, Object>) dataModel.get("leftAxis");
            if (leftAxis != null && !leftAxis.isEmpty()) {

                //获取系列中列名
                columnL = (String) leftAxis.get("column");
                if (!StringUtil.isEmpty(columnL)) {
                    //获取系列中运算公式
                    String expressionSe = (String) leftAxis.get("expression");
                    String aggFn = (String) leftAxis.get("aggFn");
                    if ("sum".equals(aggFn)) {
                        map.put("leftAxis", columnL + "(求和)");
                    } else if ("max".equals(aggFn)) {
                        map.put("leftAxis", columnL + "(最大值)");
                    } else if ("min".equals(aggFn)) {
                        map.put("leftAxis", columnL + "(最小值)");
                    } else if ("avg".equals(aggFn)) {
                        map.put("leftAxis", columnL + "(平均值)");
                    } else if ("count".equals(aggFn)) {
                        map.put("leftAxis", columnL + "(计数)");
                    }
                    //公式或者列名
                    String exOrColumnL = columnL;
                    if (!StringUtil.isEmpty(expressionSe)) {
                        exOrColumnL = expressionSe;
                    }
                    groupBySql = groupBySql + "," + aggFn + "(" + exOrColumnL + ") as lefts";
                }

            }
            String columnR = "";
            //右值轴
            Map<String, Object> rightAxis = (Map<String, Object>) dataModel.get("rightAxis");
            if (rightAxis != null && !rightAxis.isEmpty()) {
                //获取系列中列名
                columnR = (String) rightAxis.get("column");
                if (!StringUtil.isEmpty(columnR)) {
                    //获取系列中运算公式
                    String expressionSe = (String) rightAxis.get("expression");

                    String aggFn = (String) rightAxis.get("aggFn");
                    if ("sum".equals(aggFn)) {
                        map.put("rightAxis", columnR + "(求和)");
                    } else if ("max".equals(aggFn)) {
                        map.put("rightAxis", columnR + "(最大值)");
                    } else if ("min".equals(aggFn)) {
                        map.put("rightAxis", columnR + "(最小值)");
                    } else if ("avg".equals(aggFn)) {
                        map.put("rightAxis", columnR + "(平均值)");
                    } else if ("count".equals(aggFn)) {
                        map.put("rightAxis", columnR + "(计数)");
                    }
                    //公式或者列名
                    String exOrColumnR = columnR;
                    if (!StringUtil.isEmpty(expressionSe)) {
                        exOrColumnR = expressionSe;
                    }
                    groupBySql = groupBySql + "," + aggFn + "(" + exOrColumnR + ") as rights";
                }

            }
            if (series != null && !series.isEmpty() && !StringUtil.isEmpty(columnSe) && !columnSe.equals(columnCa)) {

                groupBySql = groupBySql + ", count(" + exOrColumnSe + ") as counts from (" + veiwSql + ") as c" +
                        " group by (" + exOrColumnCa + "),(" + exOrColumnSe + ") order by (" + exOrColumnCa + ") " + directionCa + ", (" + exOrColumnSe + ") " + directionSe;


            } else {
                groupBySql = groupBySql + ", count(" + exOrColumnCa + ") as counts from (" + veiwSql + ") as c" +
                        " group by (" + exOrColumnCa + ") order by (" + exOrColumnCa + ") " + directionCa;
            }

            log.error(groupBySql);
            Map<String, Object> valuesMap = ExecuteSql(groupBySql);
            List<Map<String, Object>> valueList = (List<Map<String, Object>>) valuesMap.get("list");
            //封装返回参数
            List<String> orders = new ArrayList<>();
            Map<String, Object> resultMap = new HashMap<>();
            for (Map<String, Object> valueMap : valueList) {
                //x轴
                String orderName = "";
                if (series != null && !series.isEmpty() && !StringUtil.isEmpty(columnSe) && !columnSe.equals(columnCa)) {

                    orderName = valueMap.get(columnCa) + "@" + valueMap.get(columnSe);
                    orders.add(orderName);


                } else {
                    orderName = valueMap.get(columnCa) + "";
                    orders.add(orderName);
                }
                Map<String, Object> leftOrRight = new HashMap<>();
                //左值轴
                if (leftAxis != null && !leftAxis.isEmpty() && !StringUtil.isEmpty(columnL)) {

                    leftOrRight.put("leftAxis", valueMap.get("lefts"));
                    leftOrRight.put("leftCount", valueMap.get("counts"));


                }
                //右值轴
                if (rightAxis != null && !rightAxis.isEmpty() && !StringUtil.isEmpty(columnR)) {

                    leftOrRight.put("rightAxis", valueMap.get("rights"));
                    leftOrRight.put("rightCount", valueMap.get("counts"));


                }

                resultMap.put(orderName, leftOrRight);
            }


            returnMap.put("orders", orders);
            returnMap.put("result", resultMap);
            //纵轴
            returnMap.put("lly", map);
            resultList.add(returnMap);
        }

        return resultList;
    }


    public List<Map<String, Object>> columnPercentChart(Map<String, Object> chartsMap, String veiwSql, List<Map<String, Object>> resultList) throws Exception {
        //获取配置的数据
        List<Map<String, Object>> dataModels = (List<Map<String, Object>>) chartsMap.get("dataModels");

        for (Map<String, Object> dataModel : dataModels) {
            //封装返回参数
            Map<String, Object> returnMap = new HashMap<>();

            //获取每个数据里的类别
            Map<String, Object> category = (Map<String, Object>) dataModel.get("category");
            if (category == null || category.isEmpty()) {
                break;
            }
            //获取类别中的运算公式
            String expressionCa = (String) category.get("expression");
            //获取类别中列名
            String columnCa = (String) category.get("column");
            //获取类别中升降序
            String directionCa = (String) category.get("direction");
            //公式或者列名
            String exOrColumnCa = columnCa;
            if (!StringUtil.isEmpty(expressionCa)) {
                exOrColumnCa = expressionCa;
            }
            //拼接sql
            String groupBySql = "";
            groupBySql = "select (" + exOrColumnCa + ") as " + columnCa;

            //获取系列
            Map<String, Object> series = (Map<String, Object>) dataModel.get("series");
            String directionSe = "";
            String exOrColumnSe = "";
            String columnSe = "";
            if (series != null && !series.isEmpty()) {
                //获取系列中列名
                columnSe = (String) series.get("column");
                if (!StringUtil.isEmpty(columnSe) && !columnSe.equals(columnCa)) {
                    //获取系列中运算公式
                    String expressionSe = (String) series.get("expression");

                    //获取系列中升降序
                    directionSe = (String) series.get("direction");
                    //公式或者列名
                    exOrColumnSe = columnSe;
                    if (!StringUtil.isEmpty(expressionSe)) {
                        exOrColumnSe = expressionSe;
                    }
                    groupBySql = groupBySql + ",(" + exOrColumnSe + ") as " + columnSe;
                }


            }

            String columnL = "";
            //值轴
            Map<String, Object> axis = (Map<String, Object>) dataModel.get("axis");
            if (axis != null && !axis.isEmpty()) {
                //获取系列中列名
                columnL = (String) axis.get("column");
                if (!StringUtil.isEmpty(columnL)) {
                    //获取系列中运算公式
                    String expressionSe = (String) axis.get("expression");

                    String aggFn = (String) axis.get("aggFn");
                    //公式或者列名
                    String exOrColumnL = columnL;
                    if (!StringUtil.isEmpty(expressionSe)) {
                        exOrColumnL = expressionSe;
                    }
                    groupBySql = groupBySql + "," + aggFn + "(" + exOrColumnL + ") as lefts";
                }

            }

            if (series != null && !series.isEmpty() && !StringUtil.isEmpty(columnSe) && !columnSe.equals(columnCa)) {

                groupBySql = groupBySql + ", count(" + exOrColumnSe + ") as counts from (" + veiwSql + ") as c" +
                        " group by (" + exOrColumnCa + "),(" + exOrColumnSe + ") order by (" + exOrColumnCa + ") " + directionCa + ", (" + exOrColumnSe + ") " + directionSe;


            } else {
                groupBySql = groupBySql + ", count(" + exOrColumnCa + ") as counts from (" + veiwSql + ") as c" +
                        " group by (" + exOrColumnCa + ") order by (" + exOrColumnCa + ") " + directionCa;
            }

            log.error(groupBySql);
            Map<String, Object> valuesMap = ExecuteSql(groupBySql);
            List<Map<String, Object>> valueList = (List<Map<String, Object>>) valuesMap.get("list");
            //封装返回参数
            List<String> orders = new ArrayList<>();
            Map<String, Object> resultMap = new HashMap<>();
            for (Map<String, Object> valueMap : valueList) {
                //x轴
                String orderName = "";
                if (series != null && !series.isEmpty() && !StringUtil.isEmpty(columnSe) && !columnSe.equals(columnCa)) {

                    orderName = valueMap.get(columnCa) + "@" + valueMap.get(columnSe);
                    orders.add(orderName);


                } else {
                    orderName = valueMap.get(columnCa) + "";
                    orders.add(orderName);
                }
                Map<String, Object> leftOrRight = new HashMap<>();
                //值轴
                if (axis != null && !axis.isEmpty() && !StringUtil.isEmpty(columnL)) {

                    leftOrRight.put("axis", valueMap.get("lefts"));
                    leftOrRight.put("count", valueMap.get("counts"));


                }
                resultMap.put(orderName, leftOrRight);
            }

            returnMap.put("orders", orders);
            returnMap.put("result", resultMap);
            resultList.add(returnMap);
        }

        return resultList;
    }


    public String getTimeVar(Map<String, Object> paramsMap) {
        String var = decodeDate(paramsMap.get("timeVar") + "").substring(1);
        return var.substring(0, var.length() - 1);
    }
}
