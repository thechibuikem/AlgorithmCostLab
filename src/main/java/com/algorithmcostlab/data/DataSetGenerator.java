package com.algorithmcostlab.data;

import java.util.ArrayList;
import java.util.List;

public class DataSetGenerator {

    private static final String PREFIX = "user-";

    public static List<String> generate(int size) {
    if (size <= 0) {
        throw new IllegalArgumentException("size must be positive");
    }
    int width = Math.max(6, String.valueOf(size).length());
    List<String> ids = new ArrayList<>(size);
    for (int i = 1; i <= size; i++) {
        ids.add(PREFIX + String.format("%0" + width + "d", i));
    }
    // Sequential + fixed-width padding means this list is already
    // lexicographically sorted. Binary search can use it as-is.
    return ids;
}

}