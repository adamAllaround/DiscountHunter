package com.allaroundjava.price.extract

import org.jsoup.Jsoup
import spock.lang.Specification

class ValidNumberElementFinderDecoratorTest extends Specification {
    HtmlElementFinder htmlElementFinder = Mock()
    def validNumberFinder = new ValidNumberElementFinderDecorator(htmlElementFinder)

    def "Finding HTML Elements containing valid numbers for price"() {
        given: "An html of several tags with numbers"
        def htmlString = '<p class="product"><span class="price">150.99</span><span class="discount">10000</span></p>'
        def document = Jsoup.parse(htmlString)
        htmlElementFinder.findHtmlElements(_) >> document.getAllElements()
        when: "Finding html elements with numbers"
        def foundElements = validNumberFinder.findHtmlElements(document.getAllElements())
        then:
        foundElements.size() == 2
        foundElements.get(0).text() == "150.99"
        foundElements.get(1).text() == "10000"
    }

    def "Finding HTML Elements containinig number when no numbers present"() {
        given: "An html of several tags with random data"
        def htmlString = '<p class="product price"><span class="price">Discounted</span><span class="price discount">200.000</span></p>'
        def document = Jsoup.parse(htmlString)
        htmlElementFinder.findHtmlElements(_) >> document.getAllElements()
        when: "Finding html elements with numbers"
        def foundElements = validNumberFinder.findHtmlElements(document.getAllElements())
        then: "We're returning all the elements originally passed"
        foundElements == document.getAllElements()
    }

}
