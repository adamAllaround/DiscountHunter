package com.allaroundjava.price.extract;

import com.allaroundjava.model.WebPage;
import org.springframework.stereotype.Component;

@Component
public class PriceExtractingService {
    private final HtmlElementFinder htmlElementFinder;

    public PriceExtractingService() {
        this.htmlElementFinder = new ValidNumberElementFinderDecorator(
                new CurrencySymbolElementFinderDecorator(
                        new CssClassElementFinder()));
    }

    public WebPage extractPrices(WebPage webPage) {
        return null;
    }
}
