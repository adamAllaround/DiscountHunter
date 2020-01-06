package com.allaroundjava.price.extract;

import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;

@RequiredArgsConstructor
public class ExactValueFinder implements HtmlElementFinder {
    private final HtmlElementFinder htmlElementFinder;
    @Override
    public Elements findHtmlElements(Elements html) {
        return htmlElementFinder.findHtmlElements(html);
    }
}
