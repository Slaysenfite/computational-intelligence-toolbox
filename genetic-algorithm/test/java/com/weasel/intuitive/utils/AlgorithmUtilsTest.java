package com.weasel.intuitive.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AlgorithmUtilsTest {

    private AlgorithmUtils algorithmUtils;

    @Before
    public void setUp() {
        algorithmUtils = new AlgorithmUtils();
    }

    @Test
    public void testRandomIntGenerator() {
        for (int i = 0; i < 100000; i++) {
            assertIntegerIsInRange(
                    algorithmUtils.generateRandomBoundedInt(0, 100),
                    0,
                    100
            );
        }
    }

    @Test
    public void testRandomIntListGenerator() {
        List<Integer> intList = algorithmUtils.generateListOfRandomInts(12, 0, 99);
        assertThat(intList.size()).isEqualTo(12);
        assertThat(intList).doesNotHaveDuplicates();
        for (Integer integer : intList) {
            assertIntegerIsInRange(integer, 0, 99);
        }
    }

    private void assertIntegerIsInRange(Integer number, Integer min, Integer max) {
        assertThat(number).isInstanceOf(Integer.class);
        assertThat(number).isLessThanOrEqualTo(max);
        assertThat(number).isGreaterThanOrEqualTo(min);
    }

}