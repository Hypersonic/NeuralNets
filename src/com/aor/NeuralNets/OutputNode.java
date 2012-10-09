package com.aor.NeuralNets;

import java.util.ArrayList;

public class OutputNode extends Node {

    public OutputNode () {
        _totalInput = 0;
        _ready = false;
        _links = null;
        _inputs = new ArrayList<Link>();
        _id = 0;
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
        setReady(true);
    }

    public void sendTrigger () {
        _totalInput = _totalInput * NeuralNets.MAX_INPUT;
        System.out.println("Output: " + _totalInput);
        setReady(false);
    }

    public double getOutput () {
        return _totalInput;
    }
}
