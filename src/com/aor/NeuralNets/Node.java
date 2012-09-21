package com.aor.NeuralNets;

import java.util.ArrayList;

public class Node {

    private ArrayList<Link> _links;     // Links this node will trigger
    private NodeOps _op;
    private double _threshold;
 
    public void Node (NodeOps operation) {
        _op = operation;
        _links = new ArrayList<Link>();
        _threshold = Math.random();
    }
    public void Node (NodeOps operation, ArrayList<Link> destinations, double threshold) {
        _op = operation;
        _links = destinations;
        _threshold = threshold;
    }

    private boolean doOp (NodeOps op, double threshold, double input) {
    
        switch (op) {
            case GREATER_THAN:
                return input > threshold;
            case LESS_THAN:
                return input < threshold;
            case EQUALS_TO:
                return input == threshold;
        }
    
        return false; // default to a fail.

    }

    /*
     * Recieve the trigger, do something with it
     */
    public void recieveTrigger (double power) {
        
        



        for (Link link : _links) {
            link.trigger(power);
        }

    }

}
