package com.mpollente.analyzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchWorker {

    List<String[]> patterns;

    public SearchWorker(String patternDb) throws IOException {
        patterns = Files.readAllLines(Path.of(patternDb))
                .stream()
                .map(line -> line.replaceAll("\"", "").split(";"))
                .sorted(Comparator.comparing(pattern -> pattern[0], Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public String search(File file, Search search) {
        String result = "Unknown file type";
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            byte[] text = inputStream.readAllBytes();
            for (String[] pattern : patterns) {
                if (search.contains(text, pattern[1])) {
                    result = pattern[2];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName() + ": " + result;
    }

}
