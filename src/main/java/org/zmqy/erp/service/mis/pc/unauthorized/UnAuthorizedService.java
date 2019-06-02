package org.zmqy.erp.service.mis.pc.unauthorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zmqy.erp.constract.mis.enums.LoginClientNoEnum;
import org.zmqy.erp.domain.mis.sys.base.impl.UserDomain;
import org.zmqy.erp.mapper.mis.pc.unauthorized.UnAuthorizedMapper;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.service.mis.pc.log.LoginLogService;
import org.zmqy.erp.tool.helper.jwt.JWTHelper;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.List;
import java.util.Map;

@Service("org.zmqy.erp.service.mis.pc.unAuthorized.UnAuthorizedService")
public class UnAuthorizedService {
    @Autowired
    private UnAuthorizedMapper mapper;

    @Autowired
    private UserDomain userDomain;

    @Autowired
    private LoginLogService loginLogService;


    public List<Map> getLanguageList() {
        return this.mapper.getLanguageList();
    }

    public List<Map> getStoreList(String langNo, String userNo) {
        return this.mapper.getStoreList(langNo, userNo);
    }

    public Map login(String userNo, String password, String langId, String storeId) throws Exception {
        String id = mapper.getUserIdByNo(userNo);
        if (StringUtil.isEmpty(id)) {
            id = userNo;
        }

        String logId = loginLogService.beginLoginLog(id, LoginClientNoEnum.LOGINCLIENT_PC);

        if (StringUtils.isEmpty(userNo)) {
            throw new InternalException("请输入用户编码");
        }

        if (StringUtils.isEmpty(password)) {
            throw new InternalException("请输入密码");
        }

        // 验证用户密码
        String userId = this.userDomain.verify(userNo, password);

        String token = JWTHelper.createToken(userId);
        Map map = this.mapper.loginSuccess(userNo, langId, storeId, token);
        loginLogService.successLoginLog(logId);
        return map;
    }

}
