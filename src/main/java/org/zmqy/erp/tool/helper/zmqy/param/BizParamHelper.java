package org.zmqy.erp.tool.helper.zmqy.param;

import lombok.extern.slf4j.Slf4j;
import org.zmqy.erp.tool.util.bean.BeanConvertorUtil;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.*;

import java.util.*;

/**
 * 业务参数助手类
 */
@Slf4j
public class BizParamHelper {

    /**
     * 参数预处理
     * 1. 检查必填
     * @param paramsMap
     * @param requiredList
     * @param dtlArrayCount 明细数组数量
     */
    public static void preHandleParam(Map<String, Object> paramsMap, List<Map<String, String>> requiredList,
                                      Integer dtlArrayCount){
        // 这里应该捕捉参数必填的错误, 将所有参数必填的错误一起向外抛出
        // 检查单头必填项
        List<Map<String, String>> headRequiredList = ParamHelper.getHeadRequiredList(requiredList);
        ParamHelper.requiredCheck(paramsMap, headRequiredList);

        // 检查单体必填项
        if(dtlArrayCount != null && dtlArrayCount >= 1){
            for(int i=1;i<=dtlArrayCount;i++){
                StringBuffer arrayNo = new StringBuffer("dtl").append(i);
                if(paramsMap.containsValue(arrayNo.toString())){
                    List<Map<String, String>> dtlRequiredList = ParamHelper.getDtlRequiredList(requiredList, i);
                    ParamHelper.requiredCheck(paramsMap, dtlRequiredList);
                }
            }
        }
    }

    public static <T> T map2Bean(Map<String, Object> inputParamsMap,
                               String[] strKey, String[] dateKey, String[] boolKey, String[] intKey, String[] doubleKey,
                               Class<T> targetClazz) throws InstantiationException, IllegalAccessException{
        if(MapUtil.isEmpty(inputParamsMap)){
            return null;
        }

        // 需要转换成 bean 的属性
        Map<String, Object> paramsMap = new HashMap<>();

        // 字符类型
        if(ArrayUtil.isNotEmpty(strKey)){
            for(String key : strKey){
                Object value = inputParamsMap.get(key);
                paramsMap.put(key, StringUtil.parseString(value));
            }
        }

        // 日期类型
        if(ArrayUtil.isNotEmpty(dateKey)){
            for(String key : dateKey){
                Object value = inputParamsMap.get(key);
                paramsMap.put(key, DateUtil.parseDate(value));
            }
        }

        // 布尔类型
        if(ArrayUtil.isNotEmpty(boolKey)){
            for(String key : boolKey){
                Object value = inputParamsMap.get(key);
                paramsMap.put(key, BooleanUtil.parseBoolean(value));
            }
        }

        // 整型
        if(ArrayUtil.isNotEmpty(intKey)){
            for(String key : intKey){
                Object value = inputParamsMap.get(key);
                paramsMap.put(key, IntegerUtil.parseInteger(value));
            }
        }

        // 浮点型
        if(ArrayUtil.isNotEmpty(doubleKey)){
            for(String key : doubleKey){
                Object value = inputParamsMap.get(key);
                paramsMap.put(key, DoubleUtil.parseDouble(value));
            }
        }

        return BeanConvertorUtil.map2Bean(paramsMap, targetClazz);
    }

    public static <T> List<T> map2BeanList(Map<String, Object> inputParamsMap, String[] strKey, String[] dateKey, String[] boolKey, String[] intKey, String[] doubleKey,
                                         Class<T> targetClazz)
            throws InstantiationException, IllegalAccessException {

        if(MapUtil.isEmpty(inputParamsMap)){
            return null;
        }

        List<T> targetList = new ArrayList<T>();

        targetList.add(map2Bean(inputParamsMap, strKey, dateKey, boolKey, intKey, doubleKey, targetClazz));

        return targetList;
    }

    public static <T> List<T> map2BeanList(List<Map<String, Object>> inputParamsMapList, String[] strKey, String[] dateKey, String[] boolKey, String[] intKey, String[] doubleKey,
                                           Class<T> targetClazz)
            throws InstantiationException, IllegalAccessException {
        if( ListUtil.isEmpty(inputParamsMapList)){
            return null;
        }

        List<T> targetList = new ArrayList<>();

        for(Map<String, Object> inputParamsMap : inputParamsMapList){
            T target = map2Bean(inputParamsMap, strKey, dateKey, boolKey, intKey, doubleKey, targetClazz);
            targetList.add(target);
        }

        return targetList;
    }

    public static void main(String[] args){
        String str = "";
        log.info(str.length() + "");
    }
}
