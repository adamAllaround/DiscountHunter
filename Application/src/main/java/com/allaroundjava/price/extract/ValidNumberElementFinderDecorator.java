package com.allaroundjava.price.extract;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ValidNumberElementFinderDecorator implements HtmlElementFinder {
    private static final String VALID_AMOUNT_NUMBER = "^\\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)([\\.,][0-9][0-9])?$";
    private final HtmlElementFinder htmlElementFinder;

    @Override
    public Elements findHtmlElements(Elements html) {
        Elements htmlElements = htmlElementFinder.findHtmlElements(html);
        Pattern numberPattern = Pattern.compile(VALID_AMOUNT_NUMBER);
        List<Element> elementsWithNumber = htmlElements.stream()
                .map(element -> element.getElementsMatchingOwnText(numberPattern))
                .flatMap(elements -> elements.stream())
                .distinct()
                .collect(Collectors.toList());

        if(!elementsWithNumber.isEmpty()) {
            return new Elements(elementsWithNumber);
        }
        return htmlElements;
    }
}
