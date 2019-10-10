package com.allaroundjava.batch;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.price.extract.PriceExtractingService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
class WebPageProcessor implements ItemProcessor<WebPage, WebPage> {
    private final PriceExtractingService priceExtractingService;

    @Override
    public WebPage process(WebPage webPage) throws Exception {
        return priceExtractingService.extractPrices(webPage);
    }
}

