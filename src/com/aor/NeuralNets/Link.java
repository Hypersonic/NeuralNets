package com.aor.NeuralNets;

public class Link {

    protected Node _source;    // The Node that triggers this Link
    protected Node _destination;     // The Node that gets triggered by this Link
    protected double _weight;     //Weight applied to passing signals

    // Constructors
    public Link () {
        _source = null;
        _destination = null;
        _weight = (NeuralNets.generator.nextDouble() * 2) - 1;
        System.out.println("Weight: " + _weight);
    }
    public Link (double weight) {
        this();
        setWeight(weight);
    }
    public Link (Node sourceNode, Node destinationNode) {
        this();
        setSource(sourceNode);
        setDestination(destinationNode);
    }
    public Link (Node sourceNode, Node destinationNode, double weight) {
        this(sourceNode, destinationNode);
        setWeight(weight);
    }
    
    /*
     * Cloning method
     * Clones the following information:
     *      Weight
     */
    public Link clone () {
        Link newLink = new Link(getWeight());
        return newLink;
    }

    // Helper methods
    public double getWeight () {
        return _weight;
    }
    public void setWeight (double weight) {
        _weight = weight;
        //while (_weight > 1) {
            //_weight = _weight % 1;
        //}
        //while (_weight < -1) {
            //_weight = _weight % 1;
        //}
    }

    public Node getSource () {
        return _source;
    }
    public void setSource (Node source) {
        _source = source;
        _source.addLink(this); // Register with source node
    }


    public Node getDestination () {
        return _destination;
    }
    public void setDestination (Node destination) {
        _destination = destination;
        _destination.addInput(this); // Register with source node
    }


    /*
     * Triggers the next node with an amount of input power, multiplied by this link's weight
     */
    public void trigger (double power) {
        //System.out.println("Link triggered: " + power);
        double triggerPower = power * _weight;
        _destination.recieveTrigger(triggerPower);
    }
    


}
