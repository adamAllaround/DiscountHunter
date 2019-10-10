package com.allaroundjava.batch;

import com.allaroundjava.model.WebPage;
import com.allaroundjava.price.extract.PriceExtractingService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PriceExtractingService priceExtractingService;

    @Bean
    public ItemReader<WebPage> reader() {
        return new JpaPagingItemReaderBuilder<WebPage>()
                .queryString("select w from WebPage w join fetch w.priceDetails")
                .build();
    }

    @Bean
    public ItemProcessor<WebPage, WebPage> processor() {
        return new WebPageProcessor(priceExtractingService);
    }

    @Bean
    public ItemWriter<WebPage> webPageItemWriter(DataSource dataSource) {
        return new RepositoryItemWriterBuilder<WebPage>().build();
    }

    @Bean
    public Job examineRecentPrices(Step fetchPricesStep) {
        return jobBuilderFactory.get("examineRecentPrices")
                .incrementer(new RunIdIncrementer())
                .flow(fetchPricesStep)
                .end()
                .build();
    }

    @Bean
    public Step fetchPricesStep(ItemWriter<WebPage> webPageItemWriter) {
        return stepBuilderFactory.get("fetchPrices")
                .<WebPage, WebPage> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(webPageItemWriter)
                .build();
    }
}
