package com.allaroundjava.rest.webpage

import com.allaroundjava.authentication.PrincipalFinder
import com.allaroundjava.model.User
import com.allaroundjava.model.WebPage
import com.allaroundjava.model.WebPagePriceDetails
import com.allaroundjava.price.WebPagePriceDetailsService
import com.allaroundjava.webpage.WebPageService
import org.hamcrest.CoreMatchers
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class WebPageControllerTest extends Specification {
    private WebPageService webPageService = Mock()
    private WebPagePriceDetailsService priceDetailsService = Mock()
    private PrincipalFinder principalFinder = Mock()
    private WebPageController webPageController = new WebPageController(webPageService, priceDetailsService, principalFinder)
    private MockMvc mockMvc

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(webPageController).build()
    }

    def "Getting a non-existent resource"() {
        when: "Requested resource not exists"
        webPageService.findById(1L) >> Optional.empty()
        then: "Requesting such resource results in not found"
        mockMvc.perform(MockMvcRequestBuilders.get("/webPages/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "Getting an existing resource"() {
        def url = "http://example.com"
        WebPage webPage = new WebPage(url: url)
        when: "Requested resource exists"
        webPageService.findById(1L) >> Optional.of(webPage)
        then: "Requesting such resource results in ok"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/webPages/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString(url)))
                .andReturn()
        println response.getResponse().contentAsString
    }

    def "Posting a new web page resource"() {
        def url = "http://example.com"
        def request = "{\"url\":\"${url}\"}"
        WebPage webPage = new WebPage(url: url)
        principalFinder.getAuthenticatedUser() >> new User(email: "some@email.com",password: "password", enabled: true )
        when: "Persisting Web Page"
        webPageService.save(_) >> webPage
        then: "Status is 201 and Web Page is returned"
        mockMvc.perform(MockMvcRequestBuilders.post("/webPages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString(url)))
    }

    def "Getting Non existent Web Page Price Details"() {
        when: "Web Page with Given Id not Exists"
        webPageService.findById(1L) >> Optional.empty()
        then: "Status is 404 not found"
        mockMvc.perform(MockMvcRequestBuilders.get("/webPages/priceDetails/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "Getting existing price details"() {
        def webPage = new WebPage(url: "http://example.com")
        def priceDetails = [new WebPagePriceDetails(price: "120USD", page: webPage),
                            new WebPagePriceDetails(price: "7PLN", page: webPage)]
        when: "Web Page with Given Id exists"
        webPageService.findById(1L) >> Optional.of(webPage)
        priceDetailsService.findByWebPageId(_) >> priceDetails
        then: "Requesting such resource results in ok"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/webPages/priceDetails/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("120USD")))
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("7PLN")))
                .andReturn()
        println response.getResponse().contentAsString
    }
}
