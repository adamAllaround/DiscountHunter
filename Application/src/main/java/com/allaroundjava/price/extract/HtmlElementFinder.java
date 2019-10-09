package com.allaroundjava.price.extract;

import org.jsoup.select.Elements;

interface HtmlElementFinder {
    Elements findHtmlElements(Elements html);
}
