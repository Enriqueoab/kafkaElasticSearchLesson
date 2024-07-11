package com.microservices.base.twitter.to.kafka.service.runner;

import twitter4j.TwitterException;

public interface TwitterKafkaStreamRunner {

    void start() throws TwitterException;
}
