package com.aor.NeuralNets;

public class Link {

    protected Node _source;    // The Node that triggers this Link
    protected Node _destination;     // The Node that gets triggered by this Link
    protected double _weight;     //Weight applied to passing signals

    // Constructors
    public Link () {
        _source = null;
        _destination = null;
        _weight = NeuralNets.generator.nextDouble();
    }
    public Link (Node sourceNode, Node destinationNode) {
        _source = sourceNode;
        _source.addLink(this); // Register with source node
        _destination = destinationNode;
        _destination.addInput(this); // Register with source node
        _weight = NeuralNets.generator.nextDouble();
    }
    public Link (Node sourceNode, Node destinationNode, double weight) {
        _source = sourceNode;
        _source.addLink(this); // Register with source node
        _destination = destinationNode;
        _destination.addInput(this); // Register with source node
        _weight = weight;
    }

    // Helper methods
    public double getWeight () {
        return _weight;
    }
    public void setWeight (double weight) {
        _weight = weight;
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
