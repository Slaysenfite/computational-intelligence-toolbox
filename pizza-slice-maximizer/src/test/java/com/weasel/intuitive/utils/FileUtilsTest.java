package com.weasel.intuitive.utils;

import com.weasel.intuitive.domain.PizzaProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilsTest {

    private FileUtils fileUtils;

    @Before
    public void setUp() throws Exception {
        fileUtils = new FileUtils();
    }

    @Test
    public void readFileInTest() throws IOException, URISyntaxException {
        PizzaProperties pizzaProperties = fileUtils.readDataset("d_quite_big.in");

        assertThat(pizzaProperties).isNotNull();
    }
}