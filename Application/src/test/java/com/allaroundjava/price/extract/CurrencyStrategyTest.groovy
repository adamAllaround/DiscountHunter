package com.allaroundjava.price.extract

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import spock.lang.Specification

class CurrencyStrategyTest extends Specification {
    private final NumberFinder numberFinder = Mock()
    private final CurrencyStrategy currencyStrategy = new CurrencyStrategy(numberFinder)

    def "Extracting Price out of html having currency sign"() {
        given: "A simple html"
        String html = "<html><span>123PLN</span></html>"
        Document document = Jsoup.parse(html)
        when: "Extracting the amount"
        numberFinder.findValidNumber("123PLN") >> ["123"]
        def result = currencyStrategy.findPrice(document)
        then: "The result contains the value"
        result.isPresent()
        result.get() == BigDecimal.valueOf(123L)
    }
}
