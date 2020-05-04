package io.zenja.twitterai.twitter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterServiceImpl implements TwitterService {

    private final Twitter twitter;


    @Override
    @SneakyThrows
    public Stream<Status> getUserTimeline(String username, int length) {
        int stepsize = 100;
        int page = 1;
        Paging paging;
        Set<Status> data = new HashSet<>();

        while (page < length) {
            if (page >= length / stepsize) {
                break;
            }
            paging = new Paging(page, stepsize);
            data.addAll(twitter.getUserTimeline(username, paging));
            page++;
        }
        log.info("collected {} tweets from {}", data.size(), username);
        return data.stream();
    }

    @Override
    @SneakyThrows
    public Stream<Status> getHashtag(String hashtag, int length) {
        Query query = new Query(hashtag);
        query.setCount(length);
        query.setResultType(Query.POPULAR);
        QueryResult result = twitter.search(query);
        return result.getTweets().stream();
    }

    @SneakyThrows
    @Override
    public void tweet(String message) {
        Status status = twitter.updateStatus(message);
        log.info("Successfully tweeted[ {} ].", status.getText());
    }


}
