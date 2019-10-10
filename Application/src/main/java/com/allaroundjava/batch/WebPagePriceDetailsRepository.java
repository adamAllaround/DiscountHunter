package com.allaroundjava.batch;

import com.allaroundjava.model.WebPagePriceDetails;
import org.springframework.data.repository.CrudRepository;

interface WebPagePriceDetailsRepository extends CrudRepository<WebPagePriceDetails, Long> {
}
