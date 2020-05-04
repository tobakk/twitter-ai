package io.zenja.twitterai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class TwitterAiProperties {

    private String[] hashtags;

    private String[] users;

    private int samplesPerTopic;

    private String dataPath;

    private String tweetFilterRegex;

    public String getDataPath() {
        return System.getProperty("user.home") + dataPath;
    }
}
