package com.allaroundjava.batch;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.price.extract.PriceExtractingService;
import com.allaroundjava.webpage.WebPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
class ExamineRecentPricesJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PriceExtractingService priceExtractingService;
    private final EntityManagerFactory entityManagerFactory;
    private final WebPageService webPageService;

    @Bean
    public Job examineRecentPricesJob(Step fetchPricesStep) {
        return jobBuilderFactory.get("examineRecentPricesJob")
                .incrementer(new RunIdIncrementer())
                .flow(fetchPricesStep)
                .end()
                .build();
    }

    @Bean
    public Step fetchPricesStep() {
        return stepBuilderFactory.get("fetchPricesStep")
                .<WebPage, WebPage> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(webPageItemWriter())
                .build();
    }

    private ItemReader<WebPage> reader() {
        return new JpaPagingItemReaderBuilder<WebPage>()
                .queryString("select w from WebPage w left join fetch w.priceDetails")
                .entityManagerFactory(entityManagerFactory)
                .name("someName")
                .build();
    }

    private ItemProcessor<WebPage, WebPage> processor() {
        return new WebPageProcessor(priceExtractingService);
    }

    private ItemWriter<WebPage> webPageItemWriter() {
        return new WebPageWriter(webPageService);
    }
}
