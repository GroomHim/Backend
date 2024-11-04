package groom.him.common.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import groom.him.common.page.PageMeta;
import lombok.Getter;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestPageResponse<T> extends RestSuccessResponse {
    PageMeta meta;

    private RestPageResponse(T result, PageMeta meta) {
        this.statusCode = 200;
        this.meta = meta;
        this.data = result;
    }

    public static <T> RestPageResponse<T> newInstance(T result, PageMeta meta) {
        return new RestPageResponse<>(result, meta);
    }

}
