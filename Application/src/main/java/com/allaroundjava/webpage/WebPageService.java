package com.allaroundjava.webpage;

import com.allaroundjava.model.WebPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class WebPageService {
    private final WebPageRepository webPageRepository;
    public WebPage saveAndFlush(WebPage webPage) {
        return webPageRepository.saveAndFlush(webPage);
    }

    public Optional<WebPage> findById(Long webPageId) {
        return webPageRepository.findById(webPageId);
    }
}
