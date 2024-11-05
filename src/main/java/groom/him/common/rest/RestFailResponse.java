package groom.him.common.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestFailResponse<T> {
    int statusCode;
    String message;
    private RestFailResponse(int statusCode, String message){
        statusCode = statusCode;
        message = message;
    }

    public static <T> RestFailResponse<T> newInstance(int statusCode, String message) {
        return new RestFailResponse<>(statusCode, message);
    }
}
