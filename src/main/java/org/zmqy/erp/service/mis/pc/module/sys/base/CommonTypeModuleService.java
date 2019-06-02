package org.zmqy.erp.service.mis.pc.module.sys.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.constract.GeneralParam;
import org.zmqy.erp.constract.mis.enums.OperationLogEmum;
import org.zmqy.erp.constract.mis.enums.PageTypeNoEnum;
import org.zmqy.erp.domain.mis.sys.base.ICommonTypeDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IUserColumnDomain;
import org.zmqy.erp.mapper.mis.pc.module.sys.base.CommonTypeModuleMapper;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonLangBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeLangBo;
import org.zmqy.erp.service.mis.pc.log.OperationLogService;
import org.zmqy.erp.tool.helper.zmqy.param.BizParamHelper;
import org.zmqy.erp.tool.helper.zmqy.param.QueryParamHelper;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;
import org.zmqy.erp.tool.util.common.dataType.MapUtil;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommonTypeModuleService {
    @Autowired
    private CommonTypeModuleMapper mapper;
    @Autowired
    private IUserColumnDomain userColumnDomain;
    @Autowired
    private ICommonTypeDomain domain;
    @Autowired
    private OperationLogService logService;

    public static final String menuNo = "pc010208";

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getList(Map paramsMap){
        // 通用参数
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        Integer page = (Integer) paramsMap.get(GeneralParam.PAGE_PAGE_KEY);
        Integer pageSize = (Integer) paramsMap.get(GeneralParam.PAGE_PAGESIZE_KEY);

        // 参数预处理
        // 必填
        List<Map<String, String>> requiredList = this.userColumnDomain.getUserRequiredColumn(langId, userId, menuNo, PageTypeNoEnum.PAGETYPE_PARENT);
        // 模糊查询参数列表
        String[] fuzzyKeyList = {"comTypeNo", "comTypeName", "comTypeDesc", "comNo", "comName", "comDesc"};

        QueryParamHelper.preHandleParam(paramsMap, requiredList, fuzzyKeyList);

        // 语言
        paramsMap.put("langId", langId);

        Long total = this.mapper.getListTotal(paramsMap);
        Map rsMap = new HashMap();

        if(total > 0){
            paramsMap.put("startRow", (page-1) * pageSize);
            // 数据
            List<Map> list = this.mapper.getList(paramsMap);
            // 合计列
            list.add(new HashMap());
            rsMap.put(GeneralParam.PAGE_LIST_KEY, list);
        }

        rsMap.put(GeneralParam.PAGE_TOTAL_KEY, total);

        return rsMap;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getOne(Map paramsMap){
        // 通用参数
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);

        String comTypeId = (String)paramsMap.get("comTypeId");

        Map rsMap = this.mapper.getCommonType(comTypeId, langId);

        if(MapUtil.isNotEmpty(rsMap)){
            rsMap.put("dtl1", this.mapper.getCommonList(comTypeId, langId));
        }

        return rsMap;
    }

    public Map add(Map paramsMap) throws Exception{
        // 操作时间
        Date operTime = new Date();

        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);

        String comTypeNo = (String)paramsMap.get("comTypeNo");

        // 参数预检查
        List<Map<String, String>> requiredList = this.userColumnDomain.getUserRequiredColumn(langId, operUserId, menuNo, PageTypeNoEnum.PAGETYPE_CHILD);
        BizParamHelper.preHandleParam(paramsMap, requiredList, 1);

        // 构造 bo
        String[] strKey = {"comTypeNo"};
        String[] boolKey = {"canAdd", "canMdf", "canDel"};
        CommonTypeBo bo = BizParamHelper.map2Bean(paramsMap, strKey, null, boolKey, null, null, CommonTypeBo.class);

        // 数据字典类型国际化
        String[] langStrKey = {"langId", "comTypeName", "comTypeDesc"};
        paramsMap.put("langId", langId);
        bo.setCommonTypeLangBoList(BizParamHelper.map2BeanList(paramsMap, langStrKey, null, null, null, null, CommonTypeLangBo.class));

        // 构造 数据字典bo
        List<CommonBo> commonBoList = new ArrayList<>();

        List<Map<String, Object>> commonMap = (List<Map<String, Object>>)paramsMap.get("dtl1");

        if(ListUtil.isNotEmpty(commonMap)){
            for(Map<String, Object> map : commonMap){
                String comNo = (String)map.get("comNo");

                if(StringUtil.isNotEmpty(comNo)){
                    if(!comNo.startsWith(comTypeNo + "-")){
                        comNo = comTypeNo + "-" + comNo;
                        map.put("comNo", comNo);
                    }
                }

                String[] comStrKey = {"comNo", "text1"};
                String[] comDoubleKey = {"num1"};
                CommonBo commonBo = BizParamHelper.map2Bean(map, comStrKey, null, null, null, comDoubleKey, CommonBo.class);

                // 数据字典国际化
                map.put("langId", langId);
                String[] comLangStrKey = {"langId", "comName", "comDesc"};
                commonBo.setCommonLangBoList(BizParamHelper.map2BeanList(map, comLangStrKey, null, null, null, null, CommonLangBo.class));

                commonBoList.add(commonBo);
            }
        }

        bo.setCommonBoList(commonBoList);
        // end of 构造 数据字典bo

        String comTypeId = this.domain.add(bo, operUserId, operTime);

        // 记录日志
        this.logService.logging(comTypeId, operTime, operUserId, OperationLogEmum.OPERTYPE_ADD, this.menuNo);

        Map rsMap = new HashMap();
        rsMap.put("comTypeId", comTypeId);

        return rsMap;
    }

    public Map mdf(Map paramsMap) throws Exception{
        // 操作时间
        Date operTime = new Date();

        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);

        String comTypeId = (String)paramsMap.get("comTypeId");
        String comTypeNo = (String)paramsMap.get("comTypeNo");

        // 参数预检查
        List<Map<String, String>> requiredList = this.userColumnDomain.getUserRequiredColumn(langId, operUserId, menuNo, PageTypeNoEnum.PAGETYPE_CHILD);
        BizParamHelper.preHandleParam(paramsMap, requiredList, 1);

        // 构造 bo
        // 头
        paramsMap.put("id", comTypeId);
        String[] strKey = {"id", "comTypeNo"};
        String[] boolKey = {"canAdd", "canMdf", "canDel"};
        CommonTypeBo bo = BizParamHelper.map2Bean(paramsMap, strKey, null, boolKey, null, null, CommonTypeBo.class);

        // 数据字典类型国际化
        paramsMap.put("langId", langId);
        String[] comTypeLangBoParamsKey = {"langId", "comTypeName", "comTypeDesc"};
        bo.setCommonTypeLangBoList(BizParamHelper.map2BeanList(paramsMap, comTypeLangBoParamsKey, null, null, null, null, CommonTypeLangBo.class));

        // 构造 数据字典bo
        List<CommonBo> commonBoList = new ArrayList<>();

        List<Map<String, Object>> commonMap = (List<Map<String, Object>>)paramsMap.get("dtl1");

        if(ListUtil.isNotEmpty(commonMap)){
            for(Map<String, Object> map : commonMap){
                String comNo = (String)map.get("comNo");

                if(StringUtil.isNotEmpty(comNo)){
                    if(!comNo.startsWith(comTypeNo + "-")){
                        comNo = comTypeNo + "-" + comNo;
                        map.put("comNo", comNo);
                    }
                }

                map.put("id", map.get("comId"));
                String[] comStrKey = {"id", "comNo", "text1"};
                String[] comDoubleKey = {"num1"};
                CommonBo commonBo = BizParamHelper.map2Bean(map, comStrKey, null, null, null, comDoubleKey, CommonBo.class);

                // 数据字典国际化
                map.put("langId", langId);
                String[] commonLangParamsKey = {"langId", "comId", "comName", "comDesc"};
                commonBo.setCommonLangBoList(BizParamHelper.map2BeanList(map, commonLangParamsKey, null, null, null, null, CommonLangBo.class));

                commonBoList.add(commonBo);
            }
        }

        bo.setCommonBoList(commonBoList);
        // end of 构造 数据字典bo

        // 删除
        List<String> delDtl1IdList = (List<String>) paramsMap.get("delDtl1IdList");
        this.domain.delCommon(comTypeId, delDtl1IdList, operUserId, operTime);

        // 修改
        String id = this.domain.mdfById(bo, operUserId, operTime);

        // 记录日志
        this.logService.logging(comTypeId, operTime, operUserId, OperationLogEmum.OPERTYPE_MDF, this.menuNo);

        Map rsMap = new HashMap();
        rsMap.put("comTypeId", id);

        return rsMap;
    }
}
