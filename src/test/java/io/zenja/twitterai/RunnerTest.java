package io.zenja.twitterai;

import io.zenja.twitterai.twitter.TwitterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RunnerTest {


    @Autowired
    public TwitterService twitterService;

    @Autowired
    public TriggerService triggerService;

    @Autowired
    public DataGeneratorService generatorService;

    @Autowired
    public TensorflowService tensorflowService;

    @Test
    public void dataGenerationTest() {
        generatorService.generateDataSet();
    }

    @Test
    public void tweetTest() {
        triggerService.tweet();
    }

}
