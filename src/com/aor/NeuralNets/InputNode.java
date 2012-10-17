package com.aor.NeuralNets;

import java.util.ArrayList;

public class InputNode extends Node {

    public InputNode () {
        _totalInput = 0;
        _ready = false;
        _links = new ArrayList<Link>();
        _inputs = new ArrayList<Link>();
        _id = 0;
    }

    public InputNode (int id) {
        this();
        _id = id;
    }

    public InputNode clone () {
        return new InputNode(this._id);
    }

    @Override
    public String toString () {
        String ans = "";
        ans += "\tID: " + _id + "\n";
        ans += "\tOperation: Input \n";
        return ans;
    }
    public void recieveTrigger (double power) {
        _totalInput = power;
        setReady(true);
        //System.out.println("InputNode recieved input of value: " + power);
    }

    public void sendTrigger () {
        for (Link link : _links) {
            link.trigger(_totalInput / NeuralNets.MAX_INPUT); // Normalize the input
        }
        _totalInput = 0;    //reset input counter
        setReady(false);
    }
}
