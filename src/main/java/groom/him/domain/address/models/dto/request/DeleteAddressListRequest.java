package groom.him.domain.address.models.dto.request;

import java.util.List;

public record DeleteAddressListRequest(
    List<Integer> addressIdList
) {
}