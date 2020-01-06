package com.allaroundjava.webpage

import com.allaroundjava.model.WebPage
import com.allaroundjava.model.WebPagePriceDetails
import com.allaroundjava.price.extract.PriceExtractingService
import spock.lang.Specification

class WebPageServiceTest extends Specification {
    private final PriceExtractingService priceExtractingService = Mock()
    private final WebPageRepository webPageRepository = Mock()
    private final WebPageService webPageService = new WebPageService(webPageRepository, priceExtractingService)

    def "Getting Page and not finding price inside HTML"() {
        WebPage webPage = new WebPage(url: "http://example.com", priceProposal: 600)
        priceExtractingService.findPriceDetailsMatchingPrice(webPage) >> [].toSet()

        when: "Trying to find price on webPage"
        def result = webPageService.addWebPageCandidate(webPage)

        then:
        !result.isPresent()
        0 * webPageRepository.save(_ as WebPage)
    }

    def "Getting Page and finding price inside HTML"() {
        WebPage webPage = new WebPage(url: "http://example.com", priceProposal: 600)
        WebPagePriceDetails priceDetail = new WebPagePriceDetails(page: webPage, price: 600)
        priceExtractingService.findPriceDetailsMatchingPrice(webPage) >> [priceDetail]

        when: "Trying to find price on webPage"
        def result = webPageService.addWebPageCandidate(webPage)

        then:
        result.isPresent()
        1 * webPageRepository.save(_ as WebPage)
    }

}
