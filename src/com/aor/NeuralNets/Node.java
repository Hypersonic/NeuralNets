package com.aor.NeuralNets;

import java.util.ArrayList;
import java.util.Random;

public class Node {

    private ArrayList<Link> _links;     // Links this node will trigger
    private NodeOps _op;
    private double _threshold;
    private double _totalInput;
    private boolean _ready;
 
    public Node (NodeOps operation) {
        _op = operation;
        _links = new ArrayList<Link>();
        _threshold = NeuralNets.generator.nextDouble();
        _totalInput = 0;
        _ready = false;
    }
    public Node (NodeOps operation, ArrayList<Link> destinations, double threshold) {
        _op = operation;
        _links = destinations;
        _threshold = threshold;
        _totalInput = 0;
        _ready = false;
    }
    
    @Override
    public String toString() {
        String ans = "";
        ans += "\tOperation: " + _op.name() + "\n";
        ans += "\tThreshold: " + _threshold + "\n";
        return ans;
    }
    public void addLink(Link newLink) {
        _links.add(newLink);
    }

    public void setReady(boolean ready) {
        _ready = ready;
    }
    public boolean getReady() {
        return _ready;
    }

    private boolean doOp (NodeOps op, double threshold, double input) {
        switch (op) {
            case GREATER_THAN:
                return input > threshold;
            case LESS_THAN:
                return input < threshold;
            case EQUALS_TO:
                return input == threshold;
            default:
                return false;
        }
    }

    /*
     * Recieve the trigger, do something with it
     */
    public void recieveTrigger (double power) {
        _totalInput = (_totalInput + power) / 2; // Really ugly way of "averaging" the terms, I should be shot for this
        setReady(true);
        //System.out.println("\tTrigger recieved: " + power);
        //System.out.println("\tTotal input is now: " + _totalInput);

    }

    public void sendTrigger () {
        double value;
        if (doOp(_op, _threshold, _totalInput)) {
            value = _totalInput;
        } else {
            value = 0;
        }

        for (Link link : _links) {
            link.trigger(_totalInput);
        }
        _totalInput = 0;    //reset input counter
        setReady(false);
    }


}
