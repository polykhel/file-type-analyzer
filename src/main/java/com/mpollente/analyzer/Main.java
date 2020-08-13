package com.mpollente.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        try {
            Search search = chooseSearchAlgorithm("--RK");
            File fileDirectory = new File(args[0]);
            SearchWorker searchWorker = new SearchWorker(args[1]);
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            File[] files = fileDirectory.listFiles();
            List<Future<String>> callables = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    callables.add(executorService.submit(() -> searchWorker.search(file, search)));
                }
            }

            for (Future<String> callable : callables) {
                System.out.println(callable.get());
            }

            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Search chooseSearchAlgorithm(String arg) {
        switch (arg) {
            case "--naive":
                return new NaiveSearch();
            case "--KMP":
                return new KMPSearch();
            case "--RK":
                return new RabinKarpSearch();
            default:
                throw new IllegalArgumentException();
        }
    }

}
