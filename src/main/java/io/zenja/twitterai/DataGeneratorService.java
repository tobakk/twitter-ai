package io.zenja.twitterai;

import io.zenja.twitterai.twitter.TwitterService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;


@Service
@Slf4j
@RequiredArgsConstructor
public class DataGeneratorService {

    private final TwitterService twitterService;

    private final TwitterAiProperties config;

    private final FileService fileService;

    private final Pattern regexFilter;

    @SneakyThrows
    public void generateDataSet() {
        var hashtagTweets = Arrays.stream(config.getHashtags())
                .flatMap(hashtag -> {
                    log.info("fetching tweets of hashtag: {}", hashtag);
                    return twitterService.getHashtag(hashtag, config.getSamplesPerTopic());
                });

        var userTweets = Arrays.stream(config.getUsers())
                .flatMap(username -> {
                    log.info("fetching tweets of user: {}", username);
                    return twitterService.getUserTimeline(username, config.getSamplesPerTopic());
                });

        var data = Stream.concat(hashtagTweets, userTweets)
                .filter(status -> !status.isRetweet())
                .map(Status::getText)
                .map(status -> {
                    var matcher = regexFilter.matcher(status);
                    StringBuilder sb = new StringBuilder();
                    while (matcher.find()) {
                        matcher.appendReplacement(sb, "");
                    }
                    matcher.appendTail(sb);
                    return sb.toString();
                })
                .filter(text -> !text.isBlank());


        fileService.createFile();
        fileService.writeTestData(data);
    }

}
