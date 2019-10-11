package com.allaroundjava.price.extract

import org.jsoup.Jsoup
import spock.lang.Specification

class CurrencySymbolElementFinderDecoratorTest extends Specification {
    HtmlElementFinder htmlElementFinder = Mock()
    def currencySymbolFinder = new CurrencySymbolElementFinderDecorator(htmlElementFinder)

    def "Finding html elements containing currency symbols"() {
        given: "An html of several tags with price CSS class"
        def htmlString = '<p class="product price"><span class="price">150EUR</span><span class="price discount">200EUR</span></p>'
        def document = Jsoup.parse(htmlString)
        htmlElementFinder.findHtmlElements(_) >> document.getAllElements()
        when: "Finding html elements with currency"
        def foundElements = currencySymbolFinder.findHtmlElements(document.getAllElements())
        then:
        foundElements.size() == 2
        foundElements.get(0).text() == "150EUR"
        foundElements.get(1).text() == "200EUR"
    }

    def "Finding html elements when none contain currency symbols"() {
        given: "An html of several tags with price CSS class"
        def htmlString = '<p class="product price"><span class="price">150</span><span class="price discount">200</span><span>some price</p>'
        def document = Jsoup.parse(htmlString)
        htmlElementFinder.findHtmlElements(_) >> document.getAllElements()
        when: "Finding html elements with currency"
        def foundElements = currencySymbolFinder.findHtmlElements(document.getAllElements())
        then: "We're returning all the elements originally passed"
        foundElements == document.getAllElements()
    }
}
