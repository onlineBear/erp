package org.zmqy.erp.tool.helper.zmqy.param;

import org.zmqy.erp.tool.util.common.dataType.ListUtil;
import org.zmqy.erp.tool.util.common.dataType.MapUtil;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用参数助手类
 */
public class ParamHelper {
    private static final String DEFAULT_COLUMNNOKEY = "columnNo";
    private static final String DEFAULT_COLUMNNAMEKEY = "columnName";
    private static final String DEFAULT_ARRAYNOKEY = "arrayNo";

    static List<Map<String, String>> getHeadRequiredList(List<Map<String, String>> requiredList){
        return getRequiredListByArrayNo(requiredList, DEFAULT_ARRAYNOKEY, "");
    }

    static List<Map<String, String>> getDtlRequiredList(List<Map<String, String>> requiredList, Integer dtlArrayIdx){
        StringBuffer arrayNo = new StringBuffer("dtl").append(dtlArrayIdx);
        return getRequiredListByArrayNo(requiredList, DEFAULT_ARRAYNOKEY, arrayNo.toString());
    }

    /**
     * 检查必填
     * @param inputParamMap 输入参数 map
     * @param requiredList 必填列表, 格式 [{"columnNo": "vendorId", "columnName": "供应商id"}]
     */
    static void requiredCheck(Map<String, Object> inputParamMap, List<Map<String, String>> requiredList){
        requiredCheck(inputParamMap, requiredList, DEFAULT_COLUMNNOKEY, DEFAULT_COLUMNNAMEKEY);
    }

    /**
     * 检查必填
     * @param inputParam 输入参数 map
     * @param requiredList 必填列表, 格式 [{"columnNo": "vendorId", "columnName": "供应商id"}]
     * @param columnNoKey 列编码key, 默认 columnNo
     * @param columnNameKey 列名称key, 默认 colmunName
     */
    static void requiredCheck(Map<String, Object> inputParam, List<Map<String, String>> requiredList,
                                     @NotNull String columnNoKey, @NotNull String columnNameKey){

        if(ListUtil.isNotEmpty(requiredList)){
            // 若输入全为空
            if(MapUtil.isEmpty(inputParam)){
                StringBuffer errMsgSb = new StringBuffer("请输入");

                for(Map<String, String> requiredMap : requiredList){
                    errMsgSb.append(requiredMap.get(columnNameKey)).append(";");
                }

                throw new RuntimeException(errMsgSb.toString());
            }

            // 逐个检查必填项
            StringBuffer errMsgSb = new StringBuffer("请输入");

            Boolean hasError = false;

            for(Map<String, String> requiredMap : requiredList){
                Object inputValue = inputParam.get(requiredMap.get(columnNoKey));

                if(inputValue == null){
                    errMsgSb.append(requiredMap.get(columnNameKey)).append(";");
                    hasError = true;
                }else if(inputValue instanceof String){
                    String inputValueStr = (String)inputValue;

                    if(inputValueStr.trim().equals("")){
                        errMsgSb.append(requiredMap.get(columnNameKey)).append(";");
                        hasError = true;
                    }
                }
            }

            if(hasError){
                throw new RuntimeException(errMsgSb.toString());
            }
        }
    }

    private static List<Map<String, String>> getRequiredListByArrayNo(List<Map<String, String>> requiredList, @NotNull String arrayNoKey, @NotNull String arrayNo){
        if(ListUtil.isEmpty(requiredList)){
            return null;
        }

        List<Map<String, String>> rsList = new ArrayList<>();

        for(Map<String, String> map : requiredList){
            if(map.get(DEFAULT_ARRAYNOKEY).equals(arrayNo)){
                rsList.add(map);
            }
        }

        return rsList;
    }
}
