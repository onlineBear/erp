package org.zmqy.erp.model.mis.vo;

import lombok.Getter;
import lombok.Setter;
import org.zmqy.erp.constract.mis.enums.ResponseCodeEnum;

/**
 * 响应格式
 */
public class Response {
    /**
     * 状态码
     */
    @Getter @Setter
    private String code;

    /**
     * 信息
     */
    @Getter @Setter
    private String msg;

    @Getter @Setter
    private Object data;

    /**
     * 成功
     * @return
     */
    public static Response ok(){
        return new Response(ResponseCodeEnum.OK, null, null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static Response ok(Object data){
        return new Response(ResponseCodeEnum.OK, data, null);
    }

    /**
     * 客户端错误
     * @param msg
     * @return
     */
    public static Response clientErr(String msg){
        return new Response(ResponseCodeEnum.CLIENT_ERROR, null, msg);
    }

    /**
     * 服务端错误
     * @param msg
     * @return
     */
    public static Response serverErr(String msg){
        return new Response(ResponseCodeEnum.SERVER_ERROR, null, msg);
    }

    public static Response notFound(){
        return new Response(ResponseCodeEnum.NOT_FOUND, null, "url error");
    }

    public Response(ResponseCodeEnum code, Object data, String msg){
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
    }

    public Response(){

    }
}
