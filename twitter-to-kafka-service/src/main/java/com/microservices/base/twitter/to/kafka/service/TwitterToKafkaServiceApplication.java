package com.microservices.base.twitter.to.kafka.service;

import com.microservices.base.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.base.twitter.to.kafka.service.runner.TwitterKafkaStreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.base")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    private final TwitterToKafkaServiceConfigData twitterToKafkaSerConfigData;

    private final TwitterKafkaStreamRunner twitterKafkaStreamRunner;

    public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData config, TwitterKafkaStreamRunner twitterKafkaStreamRunner) {
        this.twitterToKafkaSerConfigData = config;
        this.twitterKafkaStreamRunner = twitterKafkaStreamRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        LOG.info("App starting.........");

        LOG.info(Arrays.toString(twitterToKafkaSerConfigData.getTwitterKeywords().toArray(new String[] {})));

        LOG.info(twitterToKafkaSerConfigData.getWelcomeMessage());

        twitterKafkaStreamRunner.start();

    }
}
