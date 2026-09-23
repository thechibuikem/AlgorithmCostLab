package com.algorithmcostlab.algorithm;

import java.util.List;

public class BinarySearch {
   
    /**
     * Requires ids to be sorted ascending. Halves the search space each
     * step: O(log n) time, O(1) extra space (iterative, no recursion stack).
     */
    public static  boolean exists(List<String> sortedIds, String target){
        int low = 0;
        int high = sortedIds.size() - 1;

        while (low<=high){
            int mid = low + (high -low)/2;
            int comparison = sortedIds.get(mid).compareTo(target);

            if (comparison == 0){
                return true;
            } else if (comparison < 0){
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
