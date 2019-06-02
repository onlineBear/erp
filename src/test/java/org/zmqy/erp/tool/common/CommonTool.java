package org.zmqy.erp.tool.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.sys.base.impl.CommonDomain;
import org.zmqy.erp.domain.mis.sys.base.impl.CommonTypeDomain;
import org.zmqy.erp.mapper.mis.biz.sys.base.CommonTypeMapper;
import org.zmqy.erp.model.mis.bo.sys.base.CommonBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonLangBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeBo;
import org.zmqy.erp.model.mis.bo.sys.base.CommonTypeLangBo;
import org.zmqy.erp.model.mis.entity.sys.base.CommonType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CommonTool {
    @Autowired
    private CommonDomain commonDomain;
    @Autowired
    private CommonTypeDomain commonTypeDomain;
    @Autowired
    private CommonTypeMapper commonTypeMapper;

    @Test
    @Transactional
    @Rollback(false)
    public void addCommon() throws Exception{
        String comTypeNo = "certifType", comTypeName = "供应商证书类型",
                langId = "1073471954843422721";

        String comNo = "certifType-02", comName = "税务登记证(地)";

        List<CommonTypeLangBo> commonTypeLangBoList = new ArrayList<>();

        CommonTypeLangBo commonTypeLangBo = new CommonTypeLangBo();
        commonTypeLangBo.setLangId(langId);
        commonTypeLangBo.setComTypeName(comTypeName);

        commonTypeLangBoList.add(commonTypeLangBo);

        CommonTypeBo commonTypeBo = new CommonTypeBo();
        commonTypeBo.setComTypeNo(comTypeNo);
        commonTypeBo.setCommonTypeLangBoList(new ArrayList<>(commonTypeLangBoList));

        Date nowTime = new Date();
        String operUserId = "1073481048329289730";

        QueryWrapper<CommonType> commonTypeQueryWrapper = new QueryWrapper<>();
        commonTypeQueryWrapper.eq(CommonType.COMTYPENO, comTypeNo);

        CommonType commonType = this.commonTypeMapper.selectOne(commonTypeQueryWrapper);

        if(commonType == null){
            String commonTypeId = this.commonTypeDomain.add(commonTypeBo, operUserId, nowTime);

            List<CommonLangBo> commonLangBoList = new ArrayList<>();

            CommonLangBo commonLangBo = new CommonLangBo();
            commonLangBo.setLangId(langId);
            commonLangBo.setComName(comName);

            commonLangBoList.add(commonLangBo);

            CommonBo commonBo = new CommonBo();
            commonBo.setComNo(comNo);
            commonBo.setComTypeId(commonTypeId);
            commonBo.setCommonLangBoList(commonLangBoList);

            this.commonDomain.add(commonBo, operUserId, nowTime);
        }else {

            List<CommonLangBo> commonLangBoList = new ArrayList<>();

            CommonLangBo commonLangBo = new CommonLangBo();
            commonLangBo.setLangId(langId);
            commonLangBo.setComName(comName);

            commonLangBoList.add(commonLangBo);

            CommonBo commonBo = new CommonBo();
            commonBo.setComNo(comNo);
            commonBo.setComTypeId(commonType.getId());
            commonBo.setCommonLangBoList(commonLangBoList);

            this.commonDomain.add(commonBo, operUserId, nowTime);
        }

    }
}
