package org.zmqy.erp.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;
import org.zmqy.erp.constract.mis.enums.ResponseCodeEnum;
import org.zmqy.erp.tool.helper.jwt.JWTHelper;
import org.zmqy.erp.tool.util.http.WebUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * JWTFilter
 */
@Slf4j
public class JWTFilter extends AccessControlFilter {

    public static final String JWT_HEADER = "Authorization";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (getSubject(request, response) != null && getSubject(request, response).isAuthenticated()) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("onAccessDenied");
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", ResponseCodeEnum.UNLOGIN.getCode());
        map.put("msg", "没有登录!");
        if (isJwtSubmission(request)) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authHeader = httpRequest.getHeader(JWT_HEADER);
            String token = "";
            if (authHeader == null || !authHeader.startsWith("bearer ")) {
                WebUtil.writeJson(map, (HttpServletResponse) response);
            } else {
                token = authHeader.substring(6).trim();
            }
            boolean flag = JWTHelper.verify(token);

            String userId = JWTHelper.getUserId(token);

            Subject currentUser = SecurityUtils.getSubject();

            AuthenticationToken authenticationToken = new JWTAuthenticationToken(userId, "");

            currentUser.login(authenticationToken);

            if (!flag) {
                WebUtil.writeJson(map, (HttpServletResponse) response);
            }
            return flag;
        }
        WebUtil.writeJson(map, (HttpServletResponse) response);
        return false;
    }

    /**
     * 是否是 jwt 请求
     *
     * @param request
     * @return
     */
    protected Boolean isJwtSubmission(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = httpRequest.getHeader(JWT_HEADER);

            return !StringUtils.isEmpty(token);
        }

        return false;
    }
}
