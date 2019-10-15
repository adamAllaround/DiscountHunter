package com.allaroundjava.batch

import com.allaroundjava.config.AppConfig
import com.allaroundjava.model.User
import com.allaroundjava.model.WebPage
import com.allaroundjava.price.WebPagePriceDetailsService
import com.allaroundjava.price.extract.HtmlDownloadService
import com.allaroundjava.price.extract.PriceExtractingService
import com.allaroundjava.test.config.TestJpaConfig
import com.allaroundjava.user.UserService
import com.allaroundjava.webpage.WebPageService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.spockframework.spring.SpringBean
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = [AppConfig, TestJpaConfig, ExamineRecentPricesJobConfiguration])
class ExamineRecentPricesJobTest extends Specification {
    private final HtmlDownloadService htmlDownloadService = Mock()
    @SpringBean
    private PriceExtractingService priceExtractingService = new PriceExtractingService(htmlDownloadService, 3)
    @Autowired
    private final UserService userService
    @Autowired
    private final WebPageService webPageService
    @Autowired
    private final WebPagePriceDetailsService priceDetailsService
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils

    def "Launching the job with two WebPages records"() {
        given: "Two web pages in the database"
        def user = new User(email: "test", password: "test", enabled: true)
        def webPage1 = new WebPage(userOwner: user, url: "http://www.example.com" )
        def webPage2 = new WebPage(userOwner: user, url: "http://www.example2.com" )
        userService.save(user)
        webPageService.saveAndFlush(webPage1)
        webPageService.saveAndFlush(webPage2)

        and: "Html download Service returning Html with two prices in it"
        htmlDownloadService.downloadDocument(_) >> createPageHtml()
        when: "Launching job"
        jobLauncherTestUtils.launchJob()
        then: "Web Page Details Exist for both pages"
        priceDetailsService.findByWebPage(webPage1).size() == 2
        priceDetailsService.findByWebPage(webPage2).size() == 2
    }

    private static Optional<Document> createPageHtml() {
        def htmlString =
                '''
                <html>
                <head>Some Head</head>
                <body>
                <p class="products">
                <p class="discount price">299.99</p>
                <p class="price">399.99</p>
                </p>
                </body>                
                </html>
                '''
        return Optional.of(Jsoup.parse(htmlString))
    }
}
