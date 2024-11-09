package groom.him.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.data.domain.Page;

@JsonInclude(Include.NON_NULL)
public record Response<T>(
    Integer statusCode,
    Meta meta,
    T data
) {

    private static final int SUCCESS_HTTP_STATUS = 200;

    public Response(Integer statusCode, T data) {
        this(statusCode, null, data);
    }

    public static <T> Response<T> success(T data) {
        return success(SUCCESS_HTTP_STATUS, data);
    }

    public static <T> Response<T> success(int httpStatus, T data) {
        if (data instanceof Page<?> page) {

            Meta meta = new Meta(page.getPageable().getOffset(), page.getNumberOfElements(),
                page.isLast(), page.getSort().isSorted());
            return new Response<>(httpStatus, meta, (T) page.getContent());
        }
        return new Response<>(httpStatus, data);
    }

    public record Meta(
        long offset,
        int numOfElements,
        boolean last,
        boolean sorted
    ) {

    }
}
