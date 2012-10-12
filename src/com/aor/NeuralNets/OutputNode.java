package com.aor.NeuralNets;

import java.util.ArrayList;

public class OutputNode extends Node {
    private int _triggersRecieved;

    public OutputNode () {
        _totalInput = 0;
        _ready = false;
        _links = null;
        _inputs = new ArrayList<Link>();
        _id = 0;
        _triggersRecieved = 0;
    }
    
    public OutputNode (int id) {
        this();
        _id = id;
    }

    public OutputNode clone () {
        return new OutputNode(getId());
    }
    
    @Override
    public String toString () {
        String ans = "";
        ans += "\tID: " + _id + "\n";
        ans += "\tOperation: Output \n";
        return ans;
    }

    public void recieveTrigger (double power) {
        _totalInput += power;
        _triggersRecieved++;
        setReady(true);
    }

    public void sendTrigger () {
        _totalInput = (_totalInput / _triggersRecieved) * NeuralNets.MAX_INPUT; //average and normalize
        _triggersRecieved = 0;
        setReady(false);
    }

    public double getOutput () {
        return _totalInput;
    }
}
