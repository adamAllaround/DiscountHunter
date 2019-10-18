package com.allaroundjava.batch;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.webpage.WebPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class WebPageWriter implements ItemWriter<WebPage> {
    private final WebPageService webPageService;
    @Override
    public void write(List<? extends WebPage> list) throws Exception {
        webPageService.saveAll(list);
    }
}
