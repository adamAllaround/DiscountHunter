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
        def foundElements = cssClassElementFinder.findHtmlElements(htmlJsoup.getElementsByTag("body"))
        then: "An element is found"
        foundElements.size() == 1
        foundElements.get(0).text() == "300"
    }

    def "Finding HTML elements by CSS class name - complex Html String"() {
        given: "A Html Document from Jsoup"
        def html = '''<html>
                <head>Some head info</head>
                <body>
                <div class="product">
                <p class="price before">100 Eur</p>
                <span class="price after">89 Eur</span>
                <span class="price note">Some note</span>
                </div>
                </body>
                </html>'''
        def htmlJsoup = Jsoup.parse(html)
        when: "Finding by CSS Class name"
        def foundElements = cssClassElementFinder.findHtmlElements(htmlJsoup.getElementsByTag("body"))
        then: "An element is found"
        foundElements.size() == 3
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
