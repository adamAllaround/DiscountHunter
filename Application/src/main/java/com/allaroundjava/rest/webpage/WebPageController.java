package com.allaroundjava.rest.webpage;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.rest.NotFoundException;
import com.allaroundjava.rest.WebPagesApi;
import com.allaroundjava.rest.dto.WebPageDto;
import com.allaroundjava.webpage.WebPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class WebPageController implements WebPagesApi {
    private final WebPageService webPageService;
    @Override
    public ResponseEntity<WebPageDto> addWebPage(@Valid WebPageDto webPageDto) {
        WebPage result = webPageService.save(WebPageDtoConverter.fromDto(webPageDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(WebPageDtoConverter.toDto(result));
    }

    @Override
    public ResponseEntity<WebPageDto> getWebPage(Long id) {
        WebPage webPage = webPageService.findById(id)
                .orElseThrow(() -> new NotFoundException("Web Page with this id could not be found"));
        return ResponseEntity.ok(WebPageDtoConverter.toDto(webPage));
    }
}

