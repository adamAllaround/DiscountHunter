package com.allaroundjava.batch;

import com.allaroundjava.model.WebPagePriceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

interface WebPagePriceDetailsRepository extends JpaRepository<WebPagePriceDetails, Long> {
}
