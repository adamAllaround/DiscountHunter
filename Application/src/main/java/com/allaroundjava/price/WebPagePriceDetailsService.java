package com.allaroundjava.price;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.model.WebPagePriceDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebPagePriceDetailsService {
    private final WebPagePriceDetailsRepository priceDetailsRepository;

    public List<WebPagePriceDetails> findByWebPage(WebPage webPage) {
        return priceDetailsRepository.findByPage(webPage);
    }
}
