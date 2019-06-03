package org.anson.miniProject.tool.util.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-11-27 10:08
 */
public class WebUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     * @author SHANHY
     * @create 2017年4月4日
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 输出JSON
     *
     * @param response
     * @author SHANHY
     * @create 2017年4月4日
     */
    public static void writeJson(Map<String, Object> map, HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(objectMapper.writeValueAsString(map));
        } catch (IOException e) {
            throw new IOException();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
