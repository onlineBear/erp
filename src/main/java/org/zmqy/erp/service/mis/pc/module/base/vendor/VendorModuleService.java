package org.zmqy.erp.service.mis.pc.module.base.vendor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.constract.GeneralParam;
import org.zmqy.erp.constract.mis.enums.ExceptionMsgEnum;
import org.zmqy.erp.constract.mis.enums.OperationLogEmum;
import org.zmqy.erp.constract.mis.enums.PageTypeNoEnum;
import org.zmqy.erp.domain.mis.base.vendor.IVendorDomain;
import org.zmqy.erp.domain.mis.flow.ICheckBillDomain;
import org.zmqy.erp.domain.mis.sys.columnInfo.IUserColumnDomain;
import org.zmqy.erp.framework.property.ExportProperty;
import org.zmqy.erp.mapper.mis.pc.module.base.vendor.VendorModuleMapper;
import org.zmqy.erp.framework.exception.model.i18n.none.NonePHException;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorBo;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorCertifBo;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorLangBo;
import org.zmqy.erp.service.mis.biz.base.VendorService;
import org.zmqy.erp.service.mis.pc.log.OperationLogService;
import org.zmqy.erp.tool.helper.zmqy.param.BizParamHelper;
import org.zmqy.erp.tool.helper.zmqy.param.QueryParamHelper;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class VendorModuleService {
    @Autowired
    private VendorModuleMapper mapper;
    @Autowired
    private IUserColumnDomain userColumnDomain;
    @Autowired
    private VendorService service;

    private static final String menuNo = "pc010501";

    /**
     * 首页查询
     * @param paramsMap
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getVendorList(Map paramsMap){
        return this.getVendorByPage(paramsMap, null);
    }

    /**
     * 查询明细
     * @param paramsMap
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getVendorById(Map paramsMap){
        String langId = (String)paramsMap.get("loginLangId");
        String vendorId = (String)paramsMap.get("vendorId");

        Map rsMap = this.mapper.getById(langId, vendorId);

        if(rsMap != null){
            rsMap.put("dtl1", this.mapper.getCertifById(vendorId));
        }

        return rsMap;
    }

    /**
     * 新增
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public Map add(Map paramsMap) throws Exception{
        // 操作时间
        Date operTime = new Date();

        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);

        // 参数预检查
        List<Map<String, String>> requiredList = this.userColumnDomain.getUserRequiredColumn(langId, operUserId, menuNo, PageTypeNoEnum.PAGETYPE_CHILD);
        BizParamHelper.preHandleParam(paramsMap, requiredList, 1);

        // 构造 bo
        String[] strKey = {"vendorNo", "legalPerson", "salesManager", "tel", "nationNo",
                            "provinceNo", "cityNo", "address", "postalcode", "email",
                            "vendorTypeNo", "vendorNatureNo", "taxTypeNo", "socialCreditCode", "subjectTypeNo",
                            "issueBankNo", "issueBankAccount", "zfbAccount", "wechatAccount",
                            "interfaceNo", "accountSubject", "settleStoreId",
                            "payModeNo",
                            "contactPerson1", "contractEmail1", "contractMobile1", "contractQQ1", "contractWechat1",
                            "contactPerson2", "contractEmail2", "contractMobile2", "contractQQ2", "contractWechat2",
                            "contactPerson3", "contractEmail3", "contractMobile3", "contractQQ3", "contractWechat3",
                            "memo1", "memo2", "memo3", "memo4", "memo5"};
        String[] boolKey = {"areInvoiceNotInOrder", "areOnlineSettle", "hasUpload2B2B"};
        String[] dateKey = {"foundDate", "enterDate", "exeuntDate"};
        String[] intKey = {"firstDay"};
        String[] doubleKey = {"registeredCapital"};

        VendorBo bo = BizParamHelper.map2Bean(paramsMap, strKey, dateKey, boolKey, intKey, doubleKey, VendorBo.class);

        // 国际化
        String[] langStrKey = {"langId", "vendorName", "vendorShortName"};
        paramsMap.put("langId", langId);
        bo.setVendorLangBoList(BizParamHelper.map2BeanList(paramsMap, langStrKey, null, null, null, null, VendorLangBo.class));

        // 证书
        List<Map<String, Object>> dtlList = (List<Map<String, Object>>)paramsMap.get("dtl1");

        if(ListUtil.isNotEmpty(dtlList)){
            String[] certifStrKey = {"certifTypeNo", "certifNo", "picUrl"};
            String[] certifDateKey = {"expirationDate"};
            bo.setVendorCertifBoList(BizParamHelper.map2BeanList(dtlList, certifStrKey, certifDateKey, null, null, null, VendorCertifBo.class));
        }

        // 新增
        String vendorId = this.service.add(bo, menuNo, operUserId, operTime);

        Map rsMap = new HashMap();
        rsMap.put("vendorId", vendorId);

        return rsMap;
    }

    public Map mdf(Map paramsMap) throws Exception{
        // 操作时间
        Date operTime = new Date();

        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);

        String vendorId = (String)paramsMap.get("vendorId");

        // 参数预检查
        List<Map<String, String>> requiredList = this.userColumnDomain.getUserRequiredColumn(langId, operUserId, menuNo, PageTypeNoEnum.PAGETYPE_CHILD);
        BizParamHelper.preHandleParam(paramsMap, requiredList, 1);

        // 构造 bo
        String[] strKey = {"id", "vendorNo", "legalPerson", "salesManager", "tel", "nationNo",
                                    "provinceNo", "cityNo", "address", "postalcode", "email",
                                    "vendorTypeNo", "vendorNatureNo", "taxTypeNo", "socialCreditCode", "subjectTypeNo",
                                    "issueBankNo", "bankAccount", "zfbAccount", "wechatAccount", "areInvoiceNotInOrder",
                                    "areOnlineSettle", "interfaceNo", "accountSubject", "settleStoreId",
                                    "payModeNo", "firstDay",
                                    "contactPerson1", "contractEmail1", "contractMobile1", "contractQQ1", "contractWechat1",
                                    "contactPerson2", "contractEmail2", "contractMobile2", "contractQQ2", "contractWechat2",
                                    "contactPerson3", "contractEmail3", "contractMobile3", "contractQQ3", "contractWechat3",
                                    "memo1", "memo2", "memo3", "memo4", "memo5"};
        String[] boolKey = {"areInvoiceNotInOrder", "areOnlineSettle", "hasUpload2B2B"};
        String[] dateKey = {"foundDate", "enterDate", "exeuntDate"};
        String[] intKey = {"firstDay"};
        String[] doubleKey = {"registeredCapital"};
        paramsMap.put("id", vendorId);
        VendorBo bo = BizParamHelper.map2Bean(paramsMap, strKey, dateKey, boolKey, intKey, doubleKey, VendorBo.class);

        // 国际化
        String[] vendorLangBoParamsKey = {"langId", "vendorName", "vendorShortName"};
        paramsMap.put("langId", langId);
        bo.setVendorLangBoList(BizParamHelper.map2BeanList(paramsMap, vendorLangBoParamsKey, null, null, null, null, VendorLangBo.class));

        // 证书
        List<Map<String, Object>> dtlList = (List<Map<String, Object>>)paramsMap.get("dtl1");

        if(ListUtil.isNotEmpty(dtlList)){
            String[] vendorCertifParamsKey = {"id", "certifNo", "certifTypeNo", "picUrl"};
            String[] certifDateKey = {"expirationDate"};
            bo.setVendorCertifBoList(BizParamHelper.map2BeanList(dtlList, vendorCertifParamsKey, certifDateKey, null, null, null, VendorCertifBo.class));
        }

        // 删除证书列表
        List<String> delVendorCertifIdList = (List<String>)paramsMap.get("delVendorCertifIdList");

        // 修改
        this.service.mdfById(bo, delVendorCertifIdList, menuNo, operUserId, operTime);

        Map rsMap = new HashMap();
        rsMap.put("vendorId", vendorId);

        return rsMap;
    }

    public Map check(Map paramsMap) throws Exception{
        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);

        String vendorId = (String)paramsMap.get("vendorId");
        Integer nextLevel = (Integer)paramsMap.get("nextLevel");

        Date operTime = new Date();

        this.service.check(vendorId, menuNo, nextLevel, operUserId, operTime);

        Map rsMap = new HashMap();
        rsMap.put("vendorId", vendorId);

        return rsMap;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED) // 不需要事务
    public Map bhCheck(Map paramsMap) throws Exception{
        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);

        List<String> vendorIdList = (List) paramsMap.get("vendorIdList");
        Integer nextLevel = (Integer)paramsMap.get("nextLevel");

        Date operTime = new Date();

        List<String> errVendorIdList = new ArrayList<>();

        if(ListUtil.isNotEmpty(vendorIdList)){
            for(String vendorId : vendorIdList){
                try{
                    this.service.check(vendorId, menuNo, nextLevel, operUserId, operTime);
                }catch (Exception e){
                    errVendorIdList.add(vendorId);
                }
            }
        }

        Map rsMap = new HashMap();

        if (ListUtil.isNotEmpty(errVendorIdList)) {
            rsMap.put("failedVendorNo", errVendorIdList);
        }

        return rsMap;
    }

    public Map back(Map paramsMap) throws Exception{
        // 通用参数
        String operUserId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);

        String vendorId = (String)paramsMap.get("vendorId");
        Integer currentLevel = (Integer)paramsMap.get("currentLevel");

        Date operTime = new Date();

        this.service.back(vendorId, menuNo, currentLevel, operUserId, operTime);

        Map rsMap = new HashMap();
        rsMap.put("vendorId", vendorId);

        return rsMap;
    }

    /**
     * 导出
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map export(Map paramsMap){
        return this.getVendorByPage(paramsMap, ExportProperty.getMaxTotal());
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Map getVendorByPage(@NotNull Map paramsMap, Long maxTotal){
        // 通用参数
        String langId = (String)paramsMap.get(GeneralParam.LOGIN_LANGID_KEY);
        String userId = (String)paramsMap.get(GeneralParam.LOGIN_USERID_KEY);
        Integer page = (Integer) paramsMap.get(GeneralParam.PAGE_PAGE_KEY);
        Integer pageSize = (Integer) paramsMap.get(GeneralParam.PAGE_PAGESIZE_KEY);
        String filterCondition = (String)paramsMap.get(GeneralParam.QUERY_FILTERCONDITION_KEY);

        // 参数预处理
        // 必填
        List<Map<String, String>> requiredList = this.userColumnDomain.getUserRequiredColumn(langId, userId, menuNo, PageTypeNoEnum.PAGETYPE_PARENT);
        // 模糊查询参数列表
        String[] fuzzyKeyList = {"vendorNo", "vendorName"};

        QueryParamHelper.preHandleParam(paramsMap, requiredList, fuzzyKeyList);

        String vendorNo = (String)paramsMap.get("vendorNo");
        String vendorName = (String)paramsMap.get("vendorName");

        Long total = this.mapper.getVendorListTotal(langId, vendorNo, vendorName, filterCondition);
        Map rsMap = new HashMap();

        // 若有查询限制, 表明是导出
        if(maxTotal != null){
            if(total > maxTotal){
                throw new NonePHException(ExceptionMsgEnum.EXPORT_LIMIT);
            }

            pageSize = total.intValue();
            page = 1;
            Integer startRow = (page-1) * pageSize;
            // 数据
            List<Map> list = this.mapper.export(langId, startRow, pageSize, vendorNo, vendorName, filterCondition);
            // 合计列
            list.add(this.mapper.getVendorListSum(langId, startRow, pageSize, vendorNo, vendorName, filterCondition));
            rsMap.put(GeneralParam.PAGE_LIST_KEY, list);
            return rsMap;
        }

        if(total > 0){
            Integer startRow = (page-1) * pageSize;
            // 数据
            List<Map> list = this.mapper.getVendorList(langId, startRow, pageSize, vendorNo, vendorName, filterCondition);
            // 合计列
            list.add(this.mapper.getVendorListSum(langId, startRow, pageSize, vendorNo, vendorName, filterCondition));
            rsMap.put(GeneralParam.PAGE_LIST_KEY, list);
        }

        rsMap.put(GeneralParam.PAGE_TOTAL_KEY, total);
        return rsMap;
    }
}
