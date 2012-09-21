package com.aor.NeuralNets;

public class Link {

    private Node _source;    // The Node that triggers this Link
    private Node _destination;     // The Node that gets triggered by this Link
    private double _weight;     //Weight applied to passing signals

    // Constructors
    public Link () {
        _source = null;
        _destination = null;
        _weight = Math.random();
    }
    public Link (Node sourceNode, Node destinationNode) {
        _source = sourceNode;
        _destination = destinationNode;
        _weight = Math.random();
    }
    public Link (Node sourceNode, Node destinationNode, double weight) {
        _source = sourceNode;
        _destination = destinationNode;
        _weight = weight;
    }
    
    /*
     * Triggers the next node with an amount of input power, multiplied by this link's weight
     */
    public void trigger (double power) {
        double triggerPower = power * _weight;
        _destination.recieveTrigger(triggerPower);
    }
    


}
