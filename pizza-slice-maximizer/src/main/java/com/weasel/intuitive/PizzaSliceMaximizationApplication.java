package com.weasel.intuitive;

import com.weasel.intuitive.domain.PizzaSliceMaximizer;
import com.weasel.intuitive.domain.PizzaSliceResult;
import com.weasel.intuitive.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PizzaSliceMaximizationApplication {

    public static final String OUTPUT_FILENAME = Long.toString(System.currentTimeMillis()) + ".txt";
    private static PizzaSliceMaximizer maximizer;

    public static void main(String... args) {
        maximizer = new PizzaSliceMaximizer();
        long start = System.currentTimeMillis();
        PizzaSliceResult result = maximizer.maximizePizzaSlices(
                10000,
                100
        );
        long end = System.currentTimeMillis();
        double benchmark = (end - start) / 1000.00;
        log.info(result.toString());
        log.info("Time taken for algorithm to run: {}s", benchmark);
        FileUtils fileUtils = new FileUtils();
        fileUtils.writeResultToFile(
                OUTPUT_FILENAME,
                result
        );
    }

}
