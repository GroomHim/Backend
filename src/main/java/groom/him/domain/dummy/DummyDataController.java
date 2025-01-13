package groom.him.domain.dummy;

import groom.him.domain.member.models.entity.MemberEntity;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/test")
@RequiredArgsConstructor
@RestController
public class DummyDataController {

    private final DummyDataService dummyDataService;

    @PostMapping
    public String test(
        @AuthenticationPrincipal MemberEntity member,
        @RequestPart("productName") String productName,
        @RequestPart("categoryName") String categoryName,
        @RequestPart("brandName") String brandName,
        @RequestPart("price") String price,
        @RequestPart("discountRate") String discountRate,
        @RequestPart("images") List<MultipartFile> images
    ) throws IOException {
        CreateDummyDataRequest request = new CreateDummyDataRequest(
            productName, categoryName, brandName, Integer.parseInt(price),
            Float.parseFloat(discountRate), "2500원 (20000원 이상 무료배송), 평균 3일 이내 도착",
            images.getFirst(), images.get(1), images.get(2), images.getLast());

        dummyDataService.createDummyData(member.getMemberId(), request);
        return "success";
    }
}
