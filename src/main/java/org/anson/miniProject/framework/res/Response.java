package org.anson.miniProject.framework.res;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @ClassName Response
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 10:16
 * @Version 1.0
 **/
@Data
public class Response {
    private Integer code;
    private Object data;
    private String msg;
    private String debugMsg;
    /**
     * 请求时间
     */
    private Date reqTime;

    public Response(HttpStatus httpStatus, Object data, String msg, String debugMsg){
        this.code = httpStatus.value();
        this.data = data;
        this.msg = msg;
        this.debugMsg = debugMsg;
    }
}
