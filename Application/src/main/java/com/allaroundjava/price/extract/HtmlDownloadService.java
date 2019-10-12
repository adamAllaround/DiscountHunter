package com.allaroundjava.price.extract;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Component
@Log4j2
class HtmlDownloadService {
    private final int timeout;

    public HtmlDownloadService(@Value("${fetch.page.timeout.ms:10000}") Integer timeout) {
        this.timeout = timeout;
    }

    public Optional<Document> downloadDocument(String url) {
        try {
            log.debug("Attempting to download HTML page at {}", url);
            return Optional.of(Jsoup.parse(new URL(url), timeout));
        } catch (MalformedURLException e) {
            log.error("Provided URL={} is incorrect", url);
        } catch (IOException e) {
            log.error("Could not parse HTML document at url {}", url);
        }
        return Optional.empty();
    }
}
