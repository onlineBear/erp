package org.zmqy.erp.tool.helper.zmqy.param;

import lombok.extern.slf4j.Slf4j;
import org.zmqy.erp.constract.mis.constract.GeneralParam;
import org.zmqy.erp.constract.mis.keyword.db.mssql.MssqlKeywordEnum;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.MapUtil;

import java.util.List;
import java.util.Map;

/**
 * 查询参数助手类
 */
@Slf4j
public class QueryParamHelper {

    /**
     * 参数预处理 (必填检查 / 模糊参数添加 % / 过滤条件检查)
     * @param paramsMap 参数map
     * @param requiredList 必填key
     * @param fuzzyKeyList 模糊查询key
     */
    public static void preHandleParam(Map<String, Object> paramsMap, List<Map<String, String>> requiredList,
                                      String[] fuzzyKeyList){
        // 检查必填
        // 取头部的必填项
        List<Map<String, String>> headRequiredList = ParamHelper.getHeadRequiredList(requiredList);
        ParamHelper.requiredCheck(paramsMap, headRequiredList);

        // 模糊查询参数
        handleFuzzy(paramsMap, fuzzyKeyList);

        // 过滤条件检查
        if(MapUtil.isNotEmpty(paramsMap) && StringUtil.isNotEmpty(GeneralParam.QUERY_FILTERCONDITION_KEY)){
            String filterCondition = (String) paramsMap.get(GeneralParam.QUERY_FILTERCONDITION_KEY);
            checkFilterCondition(filterCondition);
        }
    }

    /**
     * 处理模糊查询的参数
     * 给模糊查询参数 前后加上 %
     * @param paramsMap
     * @param fuzzyKeyList
     */
    public static void handleFuzzy(Map<String, Object> paramsMap, String[] fuzzyKeyList){
        if(MapUtil.isEmpty(paramsMap)){
            return;
        }

        if(fuzzyKeyList == null || fuzzyKeyList.length <= 0){
            return;
        }

        StringBuffer errMsg = new StringBuffer();

        for(String fuzzyKey: fuzzyKeyList){
            Object fuzzyValue = paramsMap.get(fuzzyKey);

            if(fuzzyValue == null){
                continue;
            }

            if(fuzzyValue instanceof String){
                String fuzzyVauleStr = (String)fuzzyValue;
                if(StringUtil.isNotEmpty(fuzzyVauleStr)){
                    StringBuffer sb = new StringBuffer("%").append(fuzzyVauleStr).append("%");
                    paramsMap.put(fuzzyKey, sb.toString());
                }
            }else {
                errMsg = errMsg.append(fuzzyKey).append(";");
            }
        }

        if(errMsg != null && errMsg.length() > 0){
            throw new RuntimeException(errMsg.toString());
        }
    }

    /**
     * 检查过滤条件
     * 主要担心 sql 注入攻击, 在这里做关键字的检查, 不允许存在关键字
     * @param filterCondition
     */
    public static void checkFilterCondition(String filterCondition){
        if(StringUtil.isEmpty(filterCondition)){
            return;
        }

        StringBuffer errMsg = new StringBuffer();

        for(MssqlKeywordEnum keywordEnum : MssqlKeywordEnum.values()){
            StringBuffer upperKeyWord = new StringBuffer(" ").append(keywordEnum.getKeyword().toUpperCase()).append(" ");
            if(filterCondition.toUpperCase().indexOf(upperKeyWord.toString()) != -1){
                errMsg.append(upperKeyWord);
            }
        }

        if(errMsg != null && errMsg.length() > 0){
            throw new RuntimeException(errMsg.toString());
        }
    }
}
