package org.anson.miniProject.framework.res;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @ClassName ResHelper
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 13:55
 * @Version 1.0
 **/
public class ResHelper {
    public static Response ok(Object data, Date reqTime){
        return new Response(HttpStatus.OK, data, null, null, reqTime);
    }

    public static Response ok(Date reqTime){
        return ok(null, reqTime);
    }

    public static Response badRequest(Object data, String msg, String debugMsg, Date reqTime){
        return new Response(HttpStatus.BAD_REQUEST, data, msg, debugMsg, reqTime);
    }

    public static Response badRequest(String msg, String debugMsg, Date reqTime){
        return badRequest(null, msg, debugMsg, reqTime);
    }

    public static Response badRequest(String msg, Date reqTime){
        return badRequest(msg, null, reqTime);
    }

    public static Response unauthorized(String msg, Date reqTime){
        return new Response(HttpStatus.UNAUTHORIZED, null, msg, null, reqTime);
    }

    public static Response internalServerErr(String msg, String debugMsg, Date reqTime){
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR, null, msg, debugMsg, reqTime);
    }

    public static Response notFound(String msg, Date reqTime){
        return new Response(HttpStatus.NOT_FOUND, null, msg, null, reqTime);
    }
}
