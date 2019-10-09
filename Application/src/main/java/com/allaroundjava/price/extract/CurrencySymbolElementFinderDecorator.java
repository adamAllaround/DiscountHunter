package com.allaroundjava.price.extract;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CurrencySymbolElementFinderDecorator implements HtmlElementFinder {
    private static final String CURRENCY_SYMBOL = "[(\\p{Sc})(eur)(pln)(usd)(dkk)(gbp)]";
    private final HtmlElementFinder htmlElementFinder;
    @Override
    public Elements findHtmlElements(Elements html) {
        Elements htmlElements = htmlElementFinder.findHtmlElements(html);
        Pattern currencyPattern = Pattern.compile(CURRENCY_SYMBOL, Pattern.CASE_INSENSITIVE);
        List<Element> elementsWithCurrency = htmlElements.stream()
                .map(element -> element.getElementsMatchingOwnText(currencyPattern))
                .flatMap(elements -> elements.stream())
                .distinct()
                .collect(Collectors.toList());

        if(!elementsWithCurrency.isEmpty()) {
            return new Elements(elementsWithCurrency);
        }
        return htmlElements;
    }
}
