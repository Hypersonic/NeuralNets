package com.aor.NeuralNets;

import java.util.Comparator;


public class NodeComparator implements Comparator {

    /*
     * Compares two nodes, yo.
     */
    public int compare (Object first, Object second) {
        Node a = (Node) first;
        Node b = (Node) second;
        if (a.getId() < b.getId()) return -1;
        if (a.getId() > b.getId()) return 1;
        return 0;
    }

}
