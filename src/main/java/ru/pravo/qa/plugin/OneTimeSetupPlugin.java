package ru.pravo.qa.plugin;

import io.qameta.allure.Aggregator;
import io.qameta.allure.Widget;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.entity.Status;
import io.qameta.allure.entity.TestResult;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OneTimeSetupPlugin implements Aggregator, Widget{

    @Override
    public void aggregate(Configuration configuration, List<LaunchResults> launches, Path path) throws IOException {
        {
            final JacksonContext jacksonContext = configuration
                    .requireContext(JacksonContext.class);
            final Path dataFolder = Files.createDirectories(path.resolve("data"));
            final Path dataFile = dataFolder.resolve("onetimesetup.json");
            final Stream<TestResult> resultsStream = launches.stream()
                    .flatMap(launch -> launch.getAllResults().stream());
            try (OutputStream os = Files.newOutputStream(dataFile)) {
                jacksonContext.getValue().writeValue(os, extractData(resultsStream));
            }
        }
    }

    private List<String> extractData(final Stream<TestResult> testResults){
        return testResults.map(map -> map.getName()).filter(s -> s.contains("SetUp")).collect(Collectors.toList());
    }

    @Override
    public Object getData(Configuration configuration, List<LaunchResults> launches){
        Stream<TestResult> filteredResults = launches.stream().flatMap(launch -> launch.getAllResults().stream())
                .filter(result -> result.getStatus().equals(Status.FAILED));
        return extractData(filteredResults);
    }

    @Override
    public String getName(){
        return "mywidget";
    }
}
