package com.weasel.intuitive.utils;

import com.weasel.intuitive.domain.PizzaProperties;
import com.weasel.intuitive.domain.PizzaSliceResult;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    public PizzaProperties readDataset(String filename) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(filename).toURI());
        String[] data = Files.lines(path).collect(Collectors.joining("\n")).split("\n");

        String[] sliceData = data[0].split(" ");
        List<Integer> values = getIntegerSliceValues(data[1]);

        return new PizzaProperties(
                Integer.parseInt(sliceData[0]),
                Integer.parseInt(sliceData[1]),
                values
        );
    }

    private List<Integer> getIntegerSliceValues(String datum) {
        String[] sliceValues = datum.split(" ");

        List<Integer> values = new ArrayList<>();

        for (String sliceValue : sliceValues)
            values.add(Integer.parseInt(sliceValue));
        return values;
    }

    public void writeResultToFile(String filename, PizzaSliceResult result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(result.toBasicString());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
