package com.allaroundjava.webpage;

import com.allaroundjava.model.WebPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class WebPageService {
    private final WebPageRepository webPageRepository;

    public WebPage save(WebPage webPage) {
        return webPageRepository.save(webPage);
    }

    public Optional<WebPage> findById(Long webPageId) {
        return webPageRepository.findById(webPageId);
    }

    public void saveAll(List<? extends WebPage> list) {
        webPageRepository.saveAll(list);
    }

    public Optional<WebPage> getPageValidToSave(WebPage webPage) {
        return Optional.empty();
    }
}
