package com.allaroundjava.price.extract

import org.jsoup.Jsoup
import spock.lang.Specification

class CssClassElementFinderTest extends Specification {
    def cssClassElementFinder = new CssClassElementFinder()

    def "Finding HTML elements by their CSS class name"() {
        given: "A Html Document from Jsoup"
        def html = '<span class="price joint big">300</span>'
        def htmlJsoup = Jsoup.parse(html)
        when: "Finding by CSS Class name"
        def foundElements = cssClassElementFinder.findHtmlElements(htmlJsoup.getAllElements())
        then: "An element is found"
        foundElements.size() == 1
        foundElements.get(0).text() == "300"
    }

    def "Finding Html elements by their CSS class name when there are no elements with searched class"() {
        given: "A Html Document from Jsoup without a price class"
        def html = '<span class="joint big">300</span>'
        def htmlJsoup = Jsoup.parse(html)
        when: "Finding by CSS Class name"
        def foundElements = cssClassElementFinder.findHtmlElements(htmlJsoup.getAllElements())
        then: "The same set of elements is returned"
        foundElements == htmlJsoup.getAllElements()
    }
}
