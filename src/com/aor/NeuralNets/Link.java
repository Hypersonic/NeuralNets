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
        return new Link(getWeight());
    }
    /*
     * Cloning method
     * Clones the following information:
     *      Weight
     *      Source Node
     *      Destination Node
     */
    public Link clone (Node sourceNode, Node destinationNode) {
        return new Link(sourceNode, destinationNode, getWeight());
    }

    public String toString () {
        return "Weight " + getWeight();
    }

    // Helper methods
    public double getWeight () {
        return _weight;
    }
    public synchronized void setWeight (double weight) {
        _weight = weight;
    }

    public Node getSource () {
        return _source;
    }
    public synchronized void setSource (Node source) {
        _source = source;
        _source.addLink(this); // Register with source node
    }

    public Node getDestination () {
        return _destination;
    }
    public synchronized void setDestination (Node destination) {
        _destination = destination;
        _destination.addInput(this); // Register with destination node
    }

    /*
     * Triggers the next node with an amount of input power, multiplied by this link's weight
     */
    public void trigger (double power) {
        //System.out.println("Link triggered:\n" + 
                           //"\tPower: " + power + "\n" +
                           //"\tWeight: " + _weight + "\n" +
                           //"\tValue: " + (power * _weight));
        _destination.recieveTrigger(power * _weight);
    }
    


}
