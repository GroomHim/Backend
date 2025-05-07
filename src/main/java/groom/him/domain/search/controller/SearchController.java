package groom.him.domain.search.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.service.ProductService;
import groom.him.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {
    private final SearchService searchService;
    private final ProductService productService;

    @GetMapping("/products")
    public Response<List<ProductWithWishResponse>> searchProductsByWordAndSaveWord(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam String word) {
        searchService.saveSearch(member.getMemberId(), word);

        List<ProductWithWishResponse> data = productService.findProductListByWord(
            member.getMemberId(), word);
        return Response.success(data);
    }

    @GetMapping("/recent")
    public Response<List<String>> findMemberSearchWords(
        @AuthenticationPrincipal MemberEntity member) {
        List<String> data = searchService.findMemberSearchWordTop5(member.getMemberId());
        return Response.success(data);
    }

    @DeleteMapping("/{searchId}")
    public Response<Integer> deleteSearch(@PathVariable Long searchId) {
        searchService.deleteSearch(searchId);
        return Response.success(HttpStatus.NO_CONTENT.value());
    }
}
