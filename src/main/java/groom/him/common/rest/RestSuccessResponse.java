package groom.him.common.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestSuccessResponse<T> {
    int statusCode;
    T data;

    private RestSuccessResponse(int statusCode, T data){
        statusCode = statusCode;
        data = data;
    }

    RestSuccessResponse(){ this.statusCode = 200; }

    public static <T> RestSuccessResponse<T> newInstance(int statusCode, T data) {
        return new RestSuccessResponse<>(statusCode, data);
    }

    public static <T> RestSuccessResponse<T> emptyInstance() {
        return new RestSuccessResponse<>();
    }
}
