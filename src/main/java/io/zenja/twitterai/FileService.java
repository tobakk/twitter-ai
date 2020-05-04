package io.zenja.twitterai;

import com.mchange.v2.io.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final TwitterAiProperties config;

    @SneakyThrows
    public void writeTestData(Stream<String> data) {
        PrintWriter writer = new PrintWriter(config.getDataPath(), StandardCharsets.UTF_8);
        data.forEachOrdered(writer::println);
        writer.close();
    }

    @SneakyThrows
    public Stream<String> readTestData() {
        return null;
    }

    @SneakyThrows
    public void createFile() {
        FileUtils.touch(new File(config.getDataPath()));
    }
}
