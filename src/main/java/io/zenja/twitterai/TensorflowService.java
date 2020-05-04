package io.zenja.twitterai;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@Slf4j
public class TensorflowService {

    @Getter
    private boolean isTrained;

    @SneakyThrows
    public void trainModel() {
        Runtime rt = Runtime.getRuntime();
        URL url = getClass().getClassLoader().getResource("continue_model.py");
        String[] commands = {"python3", url.getPath()};
        Process proc = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String outputString = null;
        while ((outputString = stdInput.readLine()) != null) {
            if (!outputString.isBlank())
                log.info(outputString);
        }
        while ((outputString = stdError.readLine()) != null) {
            if (!outputString.isBlank())
                log.error(outputString);
        }
        this.isTrained = true;
        log.info("training was a success!");
    }

    @SneakyThrows
    public String generateTweet() {
        Runtime rt = Runtime.getRuntime();
        URL url = getClass().getClassLoader().getResource("generate_tweet.py");
        String[] commands = {"python3", url.getPath()};
        Process proc = rt.exec(commands);
        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(System.getProperty("user.home")));

        String tweet = null;

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        String outputString = null;
        while ((outputString = stdInput.readLine()) != null) {
            if (!outputString.isBlank()) {
                tweet = outputString;
            }
        }

        log.info(tweet);
        return tweet;
    }
}


