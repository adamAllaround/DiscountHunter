package com.allaroundjava.price.extract;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ValidNumberElementFinderDecorator implements HtmlElementFinder {
    private static final String VALID_AMOUNT_NUMBER = "^\\d+([.,]?\\d{0,2})?$";
    private final HtmlElementFinder htmlElementFinder;

    @Override
    public Elements findHtmlElements(Elements html) {
        Elements htmlElements = htmlElementFinder.findHtmlElements(html);
        Pattern numberPattern = Pattern.compile(VALID_AMOUNT_NUMBER);
        List<Element> elementsWithNumber = htmlElements.stream()
                .map(element -> element.getElementsMatchingOwnText(numberPattern))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        if(!elementsWithNumber.isEmpty()) {
            return new Elements(elementsWithNumber);
        }
        return htmlElements;
    }
}
