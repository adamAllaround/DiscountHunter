package com.allaroundjava.price.extract;

public class PriceExtractingService {
    private final HtmlElementFinder htmlElementFinder;

    public PriceExtractingService() {
        this.htmlElementFinder = new ValidNumberElementFinderDecorator(
                new CurrencySymbolElementFinderDecorator(
                        new CssClassElementFinder()));
    }
}
