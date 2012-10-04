package com.aor.NeuralNets;

import java.util.ArrayList;

public class OutputNode extends Node {

    public OutputNode () {
        _totalInput = 0;
        _ready = false;
        _links = null;
    }
    
    public OutputNode (int id) {
        this();
        _id = id;
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
