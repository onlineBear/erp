package org.zmqy.erp.service.mis.pc.module.sys.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.ICommonDomain;
import org.zmqy.erp.domain.mis.sys.base.ICommonTypeDomain;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonLangBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeLangBo;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommonModuleService {
    @Autowired
    private ICommonDomain domain;
    @Autowired
    private ICommonTypeDomain commonTypeDomain;

    public void add(Map<String, Object> pamarsMap) throws Exception{
        String operUserId = (String)pamarsMap.get("loginUserId");
        String langId = (String)pamarsMap.get("loginLangId");
        // comTypeNo canAdd canMdf canDel langId comTypeName comTypeDesc

        String comTypeNo = (String)pamarsMap.get("comTypeNo");
        Boolean canAdd = (Boolean)pamarsMap.get("canAdd");
        Boolean canMdf = (Boolean)pamarsMap.get("canMdf");
        Boolean canDel = (Boolean)pamarsMap.get("canDel");
        String comTypeName = (String)pamarsMap.get("comTypeName");
        String comTypeDesc = (String)pamarsMap.get("comTypeDesc");

        CommonTypeBo bo = new CommonTypeBo();
        bo.setComTypeNo(comTypeNo);
        bo.setCanAdd(canAdd);
        bo.setCanMdf(canMdf);
        bo.setCanDel(canDel);

        List<CommonTypeLangBo> commonTypeLangBoList = new ArrayList<>();

        CommonTypeLangBo commonTypeLangBo = new CommonTypeLangBo();
        commonTypeLangBo.setLangId(langId);
        commonTypeLangBo.setComTypeName(comTypeName);
        commonTypeLangBo.setComTypeDesc(comTypeDesc);

        commonTypeLangBoList.add(commonTypeLangBo);

        bo.setCommonTypeLangBoList(commonTypeLangBoList);
        Date nowTime = new Date();

        // comNo num1 text1 comName comDesc
        List<Map> comList = (List<Map>)pamarsMap.get("comList");
        List<CommonBo> commonBoList = new ArrayList<>();

        if(comList != null && comList.size() > 0){
            for(Map comMap : comList){
                String comNo = (String)pamarsMap.get("comNo");
                Double num1 = (Double) pamarsMap.get("num1");
                String text1 = (String)pamarsMap.get("text1");
                String comName = (String)pamarsMap.get("comName");
                String comDesc = (String)pamarsMap.get("comDesc");

                // 加前缀
                if(StringUtil.isNotEmpty(comName)){
                    String pre = bo.getComTypeNo() + "-";
                    if(comName.startsWith(pre)){
                        comName = pre + comName;
                    }
                }

                CommonBo commonBo = new CommonBo();
                commonBo.setComNo(comNo);
                commonBo.setNum1(num1);
                commonBo.setText1(text1);

                List<CommonLangBo> commonLangBoList = new ArrayList<>();
                CommonLangBo commonLangBo = new CommonLangBo();
                commonLangBo.setLangId(langId);
                commonLangBo.setComName(comName);
                commonLangBo.setComDesc(comDesc);
                commonLangBoList.add(commonLangBo);

                commonBo.setCommonLangBoList(commonLangBoList);

                commonBoList.add(commonBo);
            }
        }

        bo.setCommonBoList(commonBoList);

        this.commonTypeDomain.add(bo, operUserId, nowTime);
    }

    public void mdf(Map<String, Object> pamarsMap) throws Exception{
        String operUserId = (String)pamarsMap.get("loginUserId");
        String langId = (String)pamarsMap.get("loginLangId");
        // comTypeNo canAdd canMdf canDel langId comTypeName comTypeDesc

        String comTypeId = (String)pamarsMap.get("comTypeId");
        String comTypeNo = (String)pamarsMap.get("comTypeNo");
        Boolean canAdd = (Boolean)pamarsMap.get("canAdd");
        Boolean canMdf = (Boolean)pamarsMap.get("canMdf");
        Boolean canDel = (Boolean)pamarsMap.get("canDel");
        String comTypeName = (String)pamarsMap.get("comTypeName");
        String comTypeDesc = (String)pamarsMap.get("comTypeDesc");

        CommonTypeBo bo = new CommonTypeBo();
        bo.setId(comTypeId);
        bo.setComTypeNo(comTypeNo);
        bo.setCanAdd(canAdd);
        bo.setCanMdf(canMdf);
        bo.setCanDel(canDel);

        List<CommonTypeLangBo> commonTypeLangBoList = new ArrayList<>();

        CommonTypeLangBo commonTypeLangBo = new CommonTypeLangBo();
        commonTypeLangBo.setLangId(langId);
        commonTypeLangBo.setComTypeName(comTypeName);
        commonTypeLangBo.setComTypeDesc(comTypeDesc);

        commonTypeLangBoList.add(commonTypeLangBo);

        bo.setCommonTypeLangBoList(commonTypeLangBoList);
        Date nowTime = new Date();

        // comNo num1 text1 comName comDesc
        List<Map> comList = (List<Map>)pamarsMap.get("comList");
        List<CommonBo> commonBoList = new ArrayList<>();

        if(comList != null && comList.size() > 0){
            for(Map comMap : comList){
                String comId = (String)pamarsMap.get("comId");
                String comNo = (String)pamarsMap.get("comNo");
                Double num1 = (Double) pamarsMap.get("num1");
                String text1 = (String)pamarsMap.get("text1");
                String comName = (String)pamarsMap.get("comName");
                String comDesc = (String)pamarsMap.get("comDesc");

                // 加前缀
                if(StringUtil.isNotEmpty(comName)){
                    String pre = bo.getComTypeNo() + "-";
                    if(comName.startsWith(pre)){
                        comName = pre + comName;
                    }
                }

                CommonBo commonBo = new CommonBo();
                commonBo.setComNo(comNo);
                commonBo.setNum1(num1);
                commonBo.setText1(text1);

                List<CommonLangBo> commonLangBoList = new ArrayList<>();
                CommonLangBo commonLangBo = new CommonLangBo();
                commonLangBo.setLangId(langId);
                commonLangBo.setComName(comName);
                commonLangBo.setComDesc(comDesc);
                commonLangBoList.add(commonLangBo);

                commonBo.setCommonLangBoList(commonLangBoList);

                commonBoList.add(commonBo);
            }
        }

        bo.setCommonBoList(commonBoList);

        this.commonTypeDomain.save(bo, operUserId, nowTime);
    }
}
