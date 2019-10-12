package com.allaroundjava.price.extract;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.model.WebPagePriceDetails;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class PriceExtractingService {
    private static final String BODY_TAG = "body";
    private final HtmlElementFinder htmlElementFinder;
    private final HtmlDownloadService htmlDownloadService;
    private final int maxNumOfFoundPriceElements;

    public PriceExtractingService(HtmlDownloadService htmlDownloadService, @Value("${max.price.tags.found:3}") int maxNumOfFoundPriceElements) {
        this.maxNumOfFoundPriceElements = maxNumOfFoundPriceElements;
        this.htmlDownloadService = htmlDownloadService;
        this.htmlElementFinder = new ValidNumberElementFinderDecorator(
                new CurrencySymbolElementFinderDecorator(
                        new CssClassElementFinder()));
    }

    public WebPage extractPrices(WebPage webPage) {
        log.debug("Extracting prices for web page with ID={}", webPage.getId());
        Optional<Document> document = htmlDownloadService.downloadDocument(webPage.getUrl());
        Set<WebPagePriceDetails> webPagePriceDetails = document.map(doc -> htmlElementFinder.findHtmlElements(doc.getElementsByTag(BODY_TAG)))
                .filter(this::lessThanMaxNumElements)
                .map(elements -> mapToWebPagePriceDetails(elements, webPage))
                .orElse(Collections.emptySet());
        log.debug("Web page with id={} has {} price details", webPage.getId(), webPagePriceDetails.size());
        webPage.setPriceDetails(webPagePriceDetails);
        return webPage;
    }

    private boolean lessThanMaxNumElements(Elements elements) {
        return elements.size() > 0 && elements.size() <= maxNumOfFoundPriceElements;
    }

    private Set<WebPagePriceDetails> mapToWebPagePriceDetails(Elements elements, WebPage webPage) {
        return elements.stream().map(element -> toWebPagePriceDetails(element, webPage)).collect(Collectors.toSet());
    }

    private WebPagePriceDetails toWebPagePriceDetails(Element element, WebPage webPage) {
        WebPagePriceDetails webPagePriceDetails = new WebPagePriceDetails();
        webPagePriceDetails.setPage(webPage);
        webPagePriceDetails.setPrice(element.ownText());
        return webPagePriceDetails;
    }
}
