package io.zenja.twitterai;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
public class TwitterAiConfiguration {

    private final TwitterAiProperties properties;

    @Bean
    public Twitter getTwitter() {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setTrimUserEnabled(false);
        cb.setTweetModeExtended(true);
        return new TwitterFactory(cb.build()).getInstance();
    }

    @Bean
    public Pattern getRegexMatcher() {
        return Pattern.compile("(?i)^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?$");
    }

    @Bean
    public File getTensorflowModelFile() {
//        ClassLoader classLoader = getClass().getClassLoader();
//        return new File(Objects.requireNonNull(classLoader.getResource("model.tflite")).getFile());
        return new File("");
    }

}
