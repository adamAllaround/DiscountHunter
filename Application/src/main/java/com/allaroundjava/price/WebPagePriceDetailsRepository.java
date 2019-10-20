package com.allaroundjava.price;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.model.WebPagePriceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface WebPagePriceDetailsRepository extends JpaRepository<WebPagePriceDetails, Long> {
    List<WebPagePriceDetails> findByPageId(Long webPageId);
}
