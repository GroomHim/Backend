package groom.him.common.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestFailResponse {
    int statusCode;
    String message;

    private RestFailResponse(){

    }
}
