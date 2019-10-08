package com.allaroundjava.price.extract

import spock.lang.Specification
import spock.lang.Unroll

class RegexNumberFinderTest extends Specification {
    private final RegexNumberFinder regexNumberFinder = new RegexNumberFinder()

    @Unroll
    def "Find number within currency String - #currencyString"() {
        when: "Finding a number within a currency string"
        def foundNumber = regexNumberFinder.findValidNumber(currencyString)
        then:
        foundNumber == expectedNumberString
        where:
        currencyString | expectedNumberString
        "\$123"        | "123"
        "\$ 123"       | "123"
        "123\$"        | "123"
        "123 \$"       | "123"
        "123PLN"       | "123"
        "123.99PLN"    | "123.99"
        "GBP 10,80"    | "10,80"
    }
}
