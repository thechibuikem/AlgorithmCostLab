package com.algorithmcostlab.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatasetGeneratorTest {

    @Test
    void generatesCorrectCount() {
        assertEquals(1000, DataSetGenerator.generate(1000).size());
    }

    @Test
    void generatesNoDuplicates() {
        List<String> ids = DataSetGenerator.generate(1000);
        assertEquals(ids.size(), new HashSet<>(ids).size());
    }

    @Test
    void generatesSortedIds() {
        List<String> ids = DataSetGenerator.generate(1000);
        List<String> sorted = new ArrayList<>(ids);
        sorted.sort(String::compareTo);
        assertEquals(sorted, ids);
    }

    @Test
    void rejectsNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> DataSetGenerator.generate(0));
    }
}