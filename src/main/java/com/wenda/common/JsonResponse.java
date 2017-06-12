package com.wenda.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * Created by chen on 2017/6/12.
 */
public class JsonResponse<T> implements Serializable {

    @JSONField(ordinal = 1)
    private int status;
    @JSONField(ordinal = 2)
    private String message;
    @JSONField(ordinal = 3)
    private  T data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullStringAsEmpty )
    public T getData() {
        return data;
    }

    /**
     * 状态吗、消息
     * @param status
     * @param message
     */

    private JsonResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    private JsonResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    /**
     * 状态吗、消息、数据
     * @param status
     * @param message
     * @param data
     */
    private JsonResponse(int status,String message,T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonResponse<T> returnSuccessMessage(String message){
        return new JsonResponse<T>(0,message);
    }

    public static <T> JsonResponse<T> returnSuccessMessageWithDate(String message,T data){
        return new JsonResponse<T>(0,message,data);
    }

    public static <T> JsonResponse <T> returnErrorMessage(String message){
        return new JsonResponse<T>(1,message);
    }

    public static <T> JsonResponse<T> returnErrorMessageWithData(String message,T data){
        return new JsonResponse<T>(1,message,data);
    }


}
