package io.zenja.twitterai.twitter;


import twitter4j.Status;

import java.util.stream.Stream;

public interface TwitterService {

    Stream<Status> getUserTimeline(String username, int length);

    Stream<Status> getHashtag(String hashtag, int length);

    void tweet(String message);
}

