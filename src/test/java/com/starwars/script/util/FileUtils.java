package com.starwars.script.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtils {

    public static String convertFileToString(String pathStr) {
        Path path = Paths.get(pathStr);

        StringBuilder sb = new StringBuilder();

        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(s -> sb.append(s).append("\n"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sb.toString();
    }
}
