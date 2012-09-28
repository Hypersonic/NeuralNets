package com.aor.NeuralNets;

import java.util.ArrayList;

public class OutputNode extends Node {

    public OutputNode () {
        _totalInput = 0;
        _ready = false;
        _links = null;
    }

    public void recieveTrigger (double power) {
        _totalInput = (_totalInput + power) / 2; // Keep ugly-averaging
        setReady(true);
    }

    public void sendTrigger () {
        System.out.println("Output: " + (_totalInput * NeuralNets.MAX_INPUT));
        setReady(false);
    }
}
