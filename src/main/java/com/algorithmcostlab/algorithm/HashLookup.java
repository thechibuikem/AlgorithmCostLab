package com.algorithmcostlab.algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashLookup {
    /**
     * Builds the HashSet once. This build cost is O(n) and is intentionally
     * excluded from the per-lookup benchmark, since a real service would
     * build the set once at startup, not on every request.
     */
    public static  Set<String> buildIndex(List<String> ids){
        return new HashSet<>(ids);
    }
    
    /**
     * O(1) average time, O(n) space for the backing set.
     */
    public static boolean exists(Set<String> index, String target){
        return index.contains(target);
    }
}
