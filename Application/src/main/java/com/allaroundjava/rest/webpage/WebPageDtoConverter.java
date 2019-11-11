package com.allaroundjava.rest.webpage;

import com.allaroundjava.model.User;
import com.allaroundjava.model.WebPage;
import com.allaroundjava.rest.dto.WebPageDto;

class WebPageDtoConverter {

    static WebPage fromDto(WebPageDto webPageDto, User user) {
        WebPage webPage = new WebPage();
        webPage.setUrl(webPageDto.getUrl());
        webPage.setUserOwner(user);
        return webPage;
    }

    static WebPageDto toDto(WebPage webPage) {
        WebPageDto webPageDto = new WebPageDto();
        webPageDto.setUrl(webPage.getUrl());
        webPageDto.setId(webPage.getId());
        return webPageDto;
    }
}
