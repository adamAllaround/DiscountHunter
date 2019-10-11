package com.allaroundjava.price.extract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Component
class HtmlDownloadService {
    private final int timeout;

    public HtmlDownloadService(@Value("fetch.page.timeout.ms") int timeout) {
        this.timeout = timeout;
    }

    public Optional<Document> downloadDocument(String url) {
        try {
            return Optional.of(Jsoup.parse(new URL(url), timeout));
        } catch (MalformedURLException e) {
            //TODO: log error message
        } catch (IOException e) {
            //TODO: log error message
        }
        return Optional.empty();
    }
}
