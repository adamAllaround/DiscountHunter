package com.allaroundjava.rest

import com.allaroundjava.config.AppConfig
import com.allaroundjava.config.SecurityConfig
import com.allaroundjava.model.User
import com.allaroundjava.model.WebPage
import com.allaroundjava.rest.dto.WebPageDto
import com.allaroundjava.test.config.TestJpaConfig
import com.allaroundjava.user.UserService
import com.allaroundjava.webpage.WebPageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCrypt
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [TestJpaConfig, AppConfig, SecurityConfig])
@EnableAutoConfiguration
class WebPageControllerIntegrationTest extends Specification {
    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private UserService userService

    @Autowired
    private WebPageService webPageService

    def "Posting for a new Web Page"() {
        def url = "http://someurl.com"
        def webPageDtoHttpEntity = createWebPageDtoHttpEntity(url)
        User user = new User(enabled: true, email: "adam@allaroundjava.com", password: BCrypt.hashpw("password", BCrypt.gensalt()))
        userService.save(user)
        when: "Posting new Web Page"
        def responseEntity = restTemplate.withBasicAuth("adam@allaroundjava.com", "passaaword")
        .postForEntity("/webPages", webPageDtoHttpEntity, WebPageDto, [:])
        then: "Status is unauthorized"
        responseEntity.statusCode == HttpStatus.CREATED
    }

    private static HttpEntity<WebPageDto> createWebPageDtoHttpEntity(String url) {
        def webPage = new WebPageDto(url: url)
        return new HttpEntity<WebPageDto>(webPage, new HttpHeaders(contentType: MediaType.APPLICATION_JSON))
    }

    def "Getting a Web Page"() {
        User user = new User(enabled: true, email: "adam@allaroundjava.com", password: "password")
        WebPage webPage = new WebPage(userOwner: user, url: "some.com")
        userService.save(user)
        webPageService.save(webPage)

        when: "Posting new Web Page"
        def responseEntity = restTemplate
                .getForEntity("/webPages/${webPage.id}", WebPageDto, [:])
        then: "Status is unauthorized"
        responseEntity.statusCode == HttpStatus.OK
    }
}