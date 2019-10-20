package com.allaroundjava.rest.webpage;

import com.allaroundjava.model.WebPagePriceDetails;
import com.allaroundjava.rest.dto.WebPagePriceDetailsDto;

import java.util.List;
import java.util.stream.Collectors;

class PriceDetailsDtoConverter {
    static List<WebPagePriceDetailsDto> toDtoList(List<WebPagePriceDetails> priceDetails) {
        return priceDetails.stream().map(PriceDetailsDtoConverter::toDto).collect(Collectors.toList());
    }

    static WebPagePriceDetailsDto toDto(WebPagePriceDetails priceDetails) {
        WebPagePriceDetailsDto priceDetailsDto = new WebPagePriceDetailsDto();
        priceDetailsDto.setPrice(priceDetails.getPrice());
        priceDetailsDto.setPageId(priceDetails.getPage().getId());
        priceDetailsDto.setId(priceDetails.getId());
        return priceDetailsDto;
    }
}
