package io.zenja.twitterai;

import io.zenja.twitterai.twitter.TwitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TriggerService {

    private final DataGeneratorService dataGeneratorService;

    private final TensorflowService tensorflowService;

    private final TwitterService twitterService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void onApplicationEvent() {
        log.info("train initial model");
        log.info("generating dataset");
        dataGeneratorService.generateDataSet();
        log.info("training model..");
        tensorflowService.trainModel();
    }


    @Scheduled(cron = "0 0 * * * *")
    public void tweet() {
        log.info("generating a tweet");
        var tweet = tensorflowService.generateTweet();
        twitterService.tweet(tweet);
        log.info("successfully tweeted");
    }
}
