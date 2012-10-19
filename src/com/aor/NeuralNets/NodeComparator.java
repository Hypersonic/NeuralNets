package com.aor.NeuralNets;

import java.util.Comparator;


public class NodeComparator implements Comparator {

    /*
     * Compares two nodes, yo.
     */
    public int compare (Node a, Node b) {
        if (a.getId() < b.getId()) return -1;
        if (a.getId() == b.getId()) return 0;
        if (a.getId() > b.getId()) return 1;
    }

}
