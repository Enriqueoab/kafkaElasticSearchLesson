package com.microservices.base.twitter.to.kafka.service.runner.impl;

import com.microservices.base.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.base.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.base.twitter.to.kafka.service.runner.TwitterKafkaStreamRunner;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "false", matchIfMissing = true)
public class TwitterKafkaStreamRunnerImp implements TwitterKafkaStreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunnerImp.class);

    private final TwitterToKafkaServiceConfigData kafkaServiceConfigData;

    private final TwitterKafkaStatusListener kafkaStatusListener;

    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunnerImp(TwitterToKafkaServiceConfigData kafkaServiceConfigData,
                                       TwitterKafkaStatusListener kafkaStatusListener) {
        this.kafkaServiceConfigData = kafkaServiceConfigData;
        this.kafkaStatusListener = kafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(kafkaStatusListener);
        addFilter();
    }
    // This method will be called before the bean destroyed, that is before the application shutdown so that
    // we will be sure that stream connection will be closed prior to application close.
    // Spring will not call methods with PreDestroy annotations
    //When a bean is in prototype scope (More than one bean).
    @PreDestroy
    private void shutdown() {

        if (twitterStream != null) {
            LOG.info("Closing twitterStream instance..");
            twitterStream.shutdown();
        }

    }

    private void addFilter() {
        var keywords = kafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        var filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOG.info("Filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }

}
