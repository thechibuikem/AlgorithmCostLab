// HashLookupTest.java
package com.algorithmcostlab.algorithm;

import com.algorithmcostlab.data.DataSetGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashLookupTest {

    @Test
    void findsExistingId() {
        List<String> ids = DataSetGenerator.generate(1000);
        Set<String> index = HashLookup.buildIndex(ids);
        assertTrue(HashLookup.exists(index, ids.get(500)));
    }

    @Test
    void reportsMissingId() {
        List<String> ids = DataSetGenerator.generate(1000);
        Set<String> index = HashLookup.buildIndex(ids);
        assertFalse(HashLookup.exists(index, "user-999999"));
    }

    @Test
    void handlesEmptyIndex() {
        Set<String> index = HashLookup.buildIndex(List.of());
        assertFalse(HashLookup.exists(index, "user-000001"));
    }
}