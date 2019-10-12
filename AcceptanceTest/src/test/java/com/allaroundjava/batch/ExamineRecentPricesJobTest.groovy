package com.allaroundjava.batch

import com.allaroundjava.config.AppConfig
import com.allaroundjava.test.config.TestJpaConfig
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBatchTest
@ContextConfiguration(classes = [TestJpaConfig, AppConfig, ExamineRecentPricesJobConfiguration])
class ExamineRecentPricesJobTest extends Specification {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils

    def "Launching the job"() {
        when: "Launching job"
        jobLauncherTestUtils.launchJob()
        then: "No Exception"
        noExceptionThrown()
    }
}
