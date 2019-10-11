package com.allaroundjava.price.extract

import com.allaroundjava.model.WebPage
import org.jsoup.Jsoup
import spock.lang.Specification

class PriceExtractingServiceTest extends Specification {
    private HtmlDownloadService htmlDownloadService = Mock()
    private def priceExtractingService = new PriceExtractingService(htmlDownloadService, 3)

    def "Extracting prices out of WebPage entity object"() {
        given: "A WebPage Object with no previous Price Details"
        def pageUrl = "http://allaroundjava.com"
        def webPage = new WebPage(url: pageUrl)

        and: "Web page html code with two prices"
        htmlDownloadService.downloadDocument(pageUrl) >> Optional.of(Jsoup.parse(getPageHtml()))

        when: "Extracting prices"
        def webPageExtracted = priceExtractingService.extractPrices(webPage)

        then: "Two WebPagePriceDetails exist and are of correct value"
        webPageExtracted.priceDetails.size() == 2
        webPageExtracted.priceDetails*.price.containsAll(["100 Eur", "89 Eur"])
    }

    private static String getPageHtml() {
                '''
                <html>
                <head>Some head info</head>
                <body>
                <div class="product">
                <p class="price before">100 Eur</p>
                <span class="price after">89 Eur</span>
                <span class="price note">Some note</span>
                </div>
                </body>
                </html>'''
    }

    def "Extracting prices out of WebPage when no or too many tags with prices found"() {
        given: "A WebPage object with no previous price details"
        def webPage = new WebPage(url: "anyUrl")
        and: "Web page html code with four price tags on it"
        htmlDownloadService.downloadDocument(_) >> Optional.of(Jsoup.parse(getTooManyPricesHtml()))
        when: "Extracting prices"
        def webPageExtracted = priceExtractingService.extractPrices(webPage)
        then:
        webPageExtracted.priceDetails.isEmpty()
    }

    private static String getTooManyPricesHtml() {
        '''
                <html>
                <head>Some head info</head>
                <body>
                <div class="product">
                <p class="price before">100 Eur</p>
                <span class="price after">89.99Eur</span>
                <span class="price note">Some note<p class="price someOther">$1200</p></span>
                <div class="price">11.99PLN</div>                
                </div>
                </body>
                </html>
        '''
    }
}
