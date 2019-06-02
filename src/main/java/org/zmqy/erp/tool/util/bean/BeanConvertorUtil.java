package org.zmqy.erp.tool.util.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorBo;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;
import org.zmqy.erp.tool.util.common.dataType.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BeanConvertorUtil {

    public static <T> T map2Bean(Map<String, Object> map, Class<T> targetClazz) throws IllegalAccessException, InstantiationException {
        if(MapUtil.isEmpty(map)){
            return null;
        }

        T target = targetClazz.newInstance();
        BeanMap beanMap = BeanMap.create(target);
        beanMap.putAll(map);
        return target;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     * @param mapList
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> map2Bean(List<Map<String, Object>> mapList, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        if(ListUtil.isEmpty(mapList)){
            return null;
        }

        List<T> list = new ArrayList();

        for (Map<String, Object> map : mapList) {
            list.add(map2Bean(map, clazz));
        }

        return list;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException{
        Map map = new HashMap();
        map.put("vendorNo", null);

        VendorBo bo = BeanConvertorUtil.map2Bean(map, VendorBo.class);

        log.info(bo.toString());
    }
}
