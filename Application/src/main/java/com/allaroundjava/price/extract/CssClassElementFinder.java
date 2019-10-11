package com.allaroundjava.price.extract;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class CssClassElementFinder implements HtmlElementFinder {

    @Override
    public Elements findHtmlElements(Elements html) {
        List<Element> elementsWithPriceClass = html.stream()
                .map(element -> element.getElementsByClass("price"))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        if(!elementsWithPriceClass.isEmpty()) {
            return new Elements(elementsWithPriceClass);
        }
        return html;
    }
}
