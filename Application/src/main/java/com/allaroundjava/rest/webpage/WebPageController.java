package com.allaroundjava.rest.webpage;

import com.allaroundjava.authentication.PrincipalFinder;
import com.allaroundjava.model.User;
import com.allaroundjava.model.WebPage;
import com.allaroundjava.model.WebPagePriceDetails;
import com.allaroundjava.price.WebPagePriceDetailsService;
import com.allaroundjava.rest.NotFoundException;
import com.allaroundjava.rest.WebPagesApi;
import com.allaroundjava.rest.dto.WebPageDto;
import com.allaroundjava.rest.dto.WebPagePriceDetailsDto;
import com.allaroundjava.webpage.WebPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
class WebPageController implements WebPagesApi {
    private final WebPageService webPageService;
    private final WebPagePriceDetailsService priceDetailsService;
    private final PrincipalFinder principalFinder;

    @Override
    public ResponseEntity<WebPageDto> addWebPage(@Valid WebPageDto webPageDto) {
        User authenticatedUser = principalFinder.getAuthenticatedUser();
        WebPage result = webPageService.save(WebPageDtoConverter.fromDto(webPageDto, authenticatedUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(WebPageDtoConverter.toDto(result));
    }

    @Override
    public ResponseEntity<WebPageDto> getWebPage(Long id) {
        WebPage webPage = webPageService.findById(id)
                .orElseThrow(() -> new NotFoundException("Web Page with this id could not be found"));
        return ResponseEntity.ok(WebPageDtoConverter.toDto(webPage));
    }

    @Override
    public ResponseEntity<List<WebPagePriceDetailsDto>> getPriceDetails(Long webPageId) {
        WebPage webPage = webPageService.findById(webPageId)
                .orElseThrow(() -> new NotFoundException("Web Page with this id could not be found"));
        List<WebPagePriceDetails> priceDetails = priceDetailsService.findByWebPageId(webPage.getId());
        return ResponseEntity.ok(PriceDetailsDtoConverter.toDtoList(priceDetails));
    }
}

