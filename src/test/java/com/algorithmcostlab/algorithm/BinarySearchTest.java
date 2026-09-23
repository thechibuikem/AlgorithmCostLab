// BinarySearchTest.java
package com.algorithmcostlab.algorithm;

import com.algorithmcostlab.data.DataSetGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinarySearchTest {

    @Test
    void findsExistingId() {
        List<String> ids = DataSetGenerator.generate(1000);
        assertTrue(BinarySearch.exists(ids, ids.get(500)));
    }

    @Test
    void findsFirstAndLastId() {
        List<String> ids = DataSetGenerator.generate(1000);
        assertTrue(BinarySearch.exists(ids, ids.get(0)));
        assertTrue(BinarySearch.exists(ids, ids.get(ids.size() - 1)));
    }

    @Test
    void reportsMissingId() {
        List<String> ids = DataSetGenerator.generate(1000);
        assertFalse(BinarySearch.exists(ids, "user-999999"));
    }

    @Test
    void handlesEmptyList() {
        assertFalse(BinarySearch.exists(List.of(), "user-000001"));
    }
}