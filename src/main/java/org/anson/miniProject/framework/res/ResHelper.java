package org.anson.miniProject.framework.res;

import org.springframework.http.HttpStatus;

/**
 * @ClassName ResHelper
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 13:55
 * @Version 1.0
 **/
public class ResHelper {
    public static Response ok(Object data){
        return new Response(HttpStatus.OK, data, null, null);
    }

    public static Response ok(){
        return ok(null);
    }

    public static Response badRequest(Object data, String msg, String debugMsg){
        return new Response(HttpStatus.BAD_REQUEST, null, msg, debugMsg);
    }

    public static Response badRequest(String msg, String debugMsg){
        return badRequest(null, msg, debugMsg);
    }

    public static Response badRequest(String msg){
        return badRequest(msg, null);
    }

    public static Response unauthorized(String msg){
        return new Response(HttpStatus.UNAUTHORIZED, null, msg, null);
    }

    public static Response internalServerErr(String msg, String debugMsg){
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR, null, msg, debugMsg);
    }
}
