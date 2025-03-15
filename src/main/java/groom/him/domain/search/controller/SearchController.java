package groom.him.domain.search.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {
  private final SearchService searchService;

  @DeleteMapping("/{searchId}")
  public Response deleteSearch(@PathVariable Long searchId){
    searchService.deleteSearch(searchId);
    return Response.success(HttpStatus.OK.value());
  }
}
