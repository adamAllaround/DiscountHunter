package com.allaroundjava.price.extract;

import org.jsoup.nodes.Document;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceExtractionStrategy {
    Optional<BigDecimal> findPrice(Document html);
}
