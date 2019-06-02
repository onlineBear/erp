package org.zmqy.erp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorBo;
import org.zmqy.erp.tool.helper.zmqy.param.ParamHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RandomTest {

    private static final BeanCopier copier = BeanCopier.create(HashMap.class, VendorBo.class, false);

    public static void main(String[] args){
        /*
        HashMap map = new HashMap();
        map.put("vendorNo", "10");
        map.put("id", 1);


        VendorBo bo = new VendorBo();

        copier.copy(map, bo, null);

        log.info(bo.parseString());
        */

        Map<String, Object> paramsMap = new HashMap<>();

        //paramsMap.put("vendorNo", "10");

        List<Map<String, String>> requiredList = new ArrayList<>();
        Map<String, String> one = new HashMap<>();
        one.put("columnNo", "vendorNo");
        one.put("columnName", "供应商编码");

        Map<String, String> two = new HashMap<>();
        two.put("columnNo", "vendorId");
        two.put("columnName", "供应商id");

        requiredList.add(one);
        requiredList.add(two);

        //ParamHelper.requiredCheck(paramsMap, requiredList);

    }
}
