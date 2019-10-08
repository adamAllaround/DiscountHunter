package com.allaroundjava.price.extract;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CurrencyStrategy implements PriceExtractionStrategy {
    private final NumberFinder numberFinder;

    private static final String CURRENCY_SYMBOL = "[(\\p{Sc})(eur)(pln)(usd)(dkk)(gbp)]";
    private static final String VALID_AMOUNT_NUMBER = "^[-]?([1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|\\.[0-9]{1,2})$";

    @Override
    public Optional<BigDecimal> findPrice(Document html) {
        Pattern currencyPattern = Pattern.compile(CURRENCY_SYMBOL, Pattern.CASE_INSENSITIVE);
        Elements elementsMatchingText = html.getElementsMatchingOwnText(currencyPattern);

        Set<BigDecimal> priceEntries = elementsMatchingText.stream()
                .map(element -> numberFinder.findValidNumber(element.text()))
                .filter(element -> matchesPriceFormat(element))
                .map(BigDecimal::new)
                .collect(Collectors.toSet());

        if(priceEntries.size() > 1) {
            return Optional.empty();
        }

        return priceEntries.stream().findFirst();
    }

    private boolean matchesPriceFormat(String element) {
        return element.matches(VALID_AMOUNT_NUMBER);
    }
}
