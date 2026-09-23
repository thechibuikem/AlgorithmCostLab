package com.algorithmcostlab.algorithm;

import java.util.List;

public class LinearSearch {
       /**
     * Scans the list sequentially until a match is found or the list ends.
     * Worst case touches every element: O(n) time, O(1) extra space.
     */
    public static boolean exists(List<String> ids, String target){
        for (String id : ids){
            if (id.equals(target)){
                return true;
            }
        }
        return false;
    }
}
