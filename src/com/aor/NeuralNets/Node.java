package com.aor.NeuralNets;

import java.util.ArrayList;
import java.util.Random;

public class Node {

    protected ArrayList<Link> _inputs;
    protected ArrayList<Link> _links;     // Links this node will trigger
    protected NodeOps _op;
    protected double _threshold;
    protected double _totalInput;
    protected boolean _ready;
    protected int _id;
    
    public Node () {
        _op = null;
        _links = new ArrayList<Link>();
        _inputs = new ArrayList<Link>();
        _threshold = NeuralNets.generator.nextDouble();
        _totalInput = 0;
        _ready = false;
        _id = 0;
    }
    public Node (int id) {
        this();
        _id = id;
    }
 
    public Node (NodeOps operation) {
        this();
        _op = operation;
    }

    public Node (int id, NodeOps operation) {
        this(id);
        _op = operation;
    }

    public Node (int id, NodeOps operation, double threshold) {
        this(id, operation);
        _threshold = threshold;
    }

    public Node (int id, NodeOps operation, ArrayList<Link> destinations, double threshold) {
        this(id, operation, threshold);
        _links = destinations;
    }

    
    /* 
     * Cloning method, returns a "copy" of this Node.
     * This copy contains:
     *       The same ID number
     *       The same operation
     *       The same threshold
     */
    public Node clone () {
        Node node = new Node(this._id, this._op, this._threshold);
        return node;
    }

    
    @Override
    public String toString () {
        String ans = "";
        ans += "\tID: " + _id + "\n";
        ans += "\tOperation: " + _op.name() + "\n";
        ans += "\tThreshold: " + _threshold + "\n";
        return ans;
    }
    public void addLink (Link newLink) {
        _links.add(newLink);
    }
    public ArrayList<Link> getLinks () {
        return _links;
    }

    public void addInput (Link newLink) {
        _inputs.add(newLink);
    }
    public ArrayList<Link> getInputs () {
        return _inputs;
    }

    public void setReady (boolean ready) {
        _ready = ready;
    }
    public boolean getReady () {
        return _ready;
    }
    
    public double getThreshold () {
        return _threshold;
    }
    public void setThreshold (double threshold) {
        _threshold = threshold;
    }
    
    public int getId () {
        return _id;
    }

    public void clear () {
        _totalInput = 0;
    }

    private boolean doOp (NodeOps op, double threshold, double input) {
        switch (op) {
            case GREATER_THAN:
                return input > threshold;
            case LESS_THAN:
                return input < threshold;
            //case EQUALS_TO:
                //return input == threshold;
            //case NOT_EQUALS_TO:
                //return input != threshold;
            case GREATER_THAN_OR_EQUAL_TO:
                return input >= threshold;
            case LESS_THAN_OR_EQUAL_TO:
                return input <= threshold;
            case ADDITION:
                _totalInput = _totalInput + (threshold / NeuralNets.MAX_INPUT);
                return true;
            case SUBTRACTION:
                _totalInput = _totalInput - (threshold / NeuralNets.MAX_INPUT);
                return true;
            case MULTIPLICATION:
                _totalInput = _totalInput * (threshold / NeuralNets.MAX_INPUT);
                return true;
            case DIVISION:
                _totalInput = _totalInput / (threshold / NeuralNets.MAX_INPUT);
                return true;
            default:
                return false;
        }
    }

    /*
     * Recieve the trigger, do something with it
     */
    public void recieveTrigger (double power) {
        _totalInput += power;
        setReady(true);
    }

    public void sendTrigger () {
        double value;
        if (doOp(_op, _threshold, _totalInput)) value = _totalInput;//Math.sin(_totalInput); // sin to bring it from -1 to 1
        else value = 0;

        //System.out.println("Value: " + value);
        for (Link link : _links) {
            link.trigger(value);
        }
        _totalInput = 0;    //reset input counter
        setReady(false);
    }


}
