package com.allaroundjava.webpage;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.model.WebPagePriceDetails;
import com.allaroundjava.price.extract.PriceExtractingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
@RequiredArgsConstructor
public class WebPageService {
    private final WebPageRepository webPageRepository;
    private final PriceExtractingService priceExtractingService;

    public WebPage save(WebPage webPage) {
        return webPageRepository.save(webPage);
    }

    public Optional<WebPage> findById(Long webPageId) {
        return webPageRepository.findById(webPageId);
    }

    public void saveAll(List<? extends WebPage> list) {
        webPageRepository.saveAll(list);
    }

    public Optional<WebPage> addWebPageCandidate(WebPage webPage) {
        Set<WebPagePriceDetails> priceDetails = priceExtractingService.findPriceDetailsMatchingPrice(webPage);
        if (priceDetails.isEmpty()) {
            return Optional.empty();
        }

        webPage.setPriceDetails(priceDetails);
        webPageRepository.save(webPage);
        return Optional.of(webPage);
    }
}
