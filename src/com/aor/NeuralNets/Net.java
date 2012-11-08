package com.aor.NeuralNets;

import java.util.ArrayList;
import java.util.Collections;


public class Net {

    private ArrayList<Node> _nodes;
    private ArrayList<InputNode> _inputs;
    private ArrayList<OutputNode> _outputs;
    private ArrayList<Link> _links;
    private int _topId;
    private int _netLength;
    private int _netWidth;
    public int awesomeness;
    

    public Net () {
        _nodes = new ArrayList<Node>();
        _inputs = new ArrayList<InputNode>();
        _outputs = new ArrayList<OutputNode>();
        _links = new ArrayList<Link>();
        _topId = 0;
        _netLength = 6;
        _netWidth = 6;
        awesomeness = 0;
    }

    public Net clone () {
        Net newNet = new Net();
    
        //first the simple variables
        newNet.setLength(this._netLength);
        newNet.setWidth(this._netWidth);

        //then, lets clone all the nodes...
        for (Node node : this._nodes) {
            Node newNode = node.clone();
            newNet.addNode(newNode);
            // if it's an input or an output, put it in the right place
            if (newNode instanceof InputNode) newNet.addInput((InputNode) newNode);
            if (newNode instanceof OutputNode) newNet.addOutput((OutputNode) newNode);
        }
        
        // Clone the links, matching source and destination with corresponding IDs
        for (Link link : this._links) {
            if (link.getWeight() != 0) { //Dont' clone links with values of 0, I guess...
                Node source = newNet.getNodeForId(link.getSource().getId());
                Node dest = newNet.getNodeForId(link.getDestination().getId());
                Link newLink = link.clone(source, dest);
                newNet.addLink(newLink);
            }
        }

        newNet.setTopId(this._topId);

        newNet.sortNodes(); //Sort the nodes for more optimal access

        return newNet;
    }

    /*
     * Populate this net with random stuffs
     */
    public void generateNet () {

        ArrayList<Node> firstLayer = new ArrayList<Node>();
        ArrayList<Node> secondLayer = new ArrayList<Node>();
        
        // populate the first layer
        for (int i = 0; i < _netWidth; i++) {
            InputNode firstNode = new InputNode(getNextId());

            firstLayer.add(firstNode);
            _inputs.add(firstNode);
            _nodes.add(firstNode);
    
        }
        
        
        for (int j = 0; j < _netLength; j++) {

            for (int i = 0; i < _netWidth; i++) {

                NodeOps OpChoice = NodeOps.values()[NeuralNets.generator.nextInt(NodeOps.values().length)];
                Node secondNode = new Node(getNextId(), OpChoice);

                secondLayer.add(secondNode);
                _nodes.add(secondNode);

            }

            for (Node firstNode : firstLayer) {
                for (Node secondNode : secondLayer) {
                    Link newLink = new Link(firstNode, secondNode);
                    _links.add(newLink);
                }
            }
            firstLayer = secondLayer; //move up one layer 
            secondLayer = new ArrayList<Node>(); //reset the second layer
        }
        
        for (int i = 0; i < 1; i++) { //Node node : firstLayer) {
            OutputNode newOutput = new OutputNode(getNextId());
            _outputs.add(newOutput);
            _nodes.add(newOutput);
        }
        for (Node node : firstLayer) {
            for (Node output : _outputs) {
                Link newLink = new Link(node, output);
                _links.add(newLink);
            }
        }
    
    }

    public double runNet (double expectedOutput) {

        double inputDelta = 2.0; //NeuralNets.generator.nextInt(100); //Start at a random int, so the net doesn't evolve to just spit out the "right" answer for a given seed, rather than actually thinking it through.
        double workingInput = expectedOutput;
        for (InputNode input : _inputs) {
            workingInput -= 2.0;
            input.recieveTrigger(workingInput);
        }
        
        // Store the nodes that are ready so we don't get confused later
        ArrayList<Node> readyNodes = new ArrayList<Node>();
        for (Node node : _nodes) {
            if (node.getReady()) {
                readyNodes.add(node);
            }
        }
        while (readyNodes.size() != 0) {
            //System.out.println("----Activating next layer----");
            for (Node node : readyNodes) {
                    node.sendTrigger();
            }

            readyNodes = new ArrayList<Node>();
            for (Node node : _nodes) {
                if (node.getReady()) {
                    readyNodes.add(node);
                }
            }
        }
        
        // get and print the output
        double output = _outputs.get(0).getOutput();
        //System.out.println("Output from net: " + output);
        return output;

    }

    /*
     * Sort the nodes by Id
     * TODO: Trim the list.
     */
    public void sortNodes () {
        Collections.sort(_nodes, new NodeComparator());
        
        //Now trim the list to make sure we can get optimal access.
        int i = 1; //Because I need normal for loop stuff too, for this...
        for (Node node : _nodes) {
            if (node.getId() != i) {
                node.setId(i);
            }
            i++;
        }
    }

    /*
     * Reset all stored values
     */
    public void reset () {
        for (Node node : getNodes()) {
            node.clear();
        }
    }

    /*
     * Alter this net randomly
     */
    public void mutate (double intensity) {
        //Our start/end values for the set of node ids we want to mutate
        int topEnd = NeuralNets.generator.nextInt(this.getTopId()) + 1;
        //System.out.println("Top Node: " + topEnd);
        int bottomEnd = NeuralNets.generator.nextInt(topEnd) + 1;
        //System.out.println("Bot Node: " + bottomEnd);

        Node[] alteredNodes = new Node[topEnd - bottomEnd];
        for (int i = bottomEnd; i < topEnd; i++) {
            alteredNodes[i - bottomEnd] = this.getNodeForId(i);
        }

        for (Node node : alteredNodes) {
            node.setThreshold(node.getThreshold() + (NeuralNets.generator.nextGaussian() * intensity));
        }

        int topLink = NeuralNets.generator.nextInt(getLinks().size()) + 1;
        //System.out.println("Top Link: " + topLink);
        int bottomLink = NeuralNets.generator.nextInt(topLink) + 1;
        //System.out.println("Bottom Link: " + bottomLink);
        for (int i = bottomLink; i < topLink; i++) {
            Link link = getLinks().get(i);
            link.setWeight(link.getWeight() + (NeuralNets.generator.nextGaussian()));// + intensity));
        }
    }

    // Backwards compatibility stuff, just call mutate with the proper args
    public void mutate () {
        mutate(1);
    }

    // Helper methods
    
    public int getNextId () {
        _topId++;
        return _topId;
    }

    /*
     * Gets a node with matching ID in this net
     */
    public Node getNodeForId (int Id) {
        Node testNode = getNodes().get(Id - 1);
        if (testNode.getId() == Id) return testNode; // Do a sane lookup
        for (Node node : getNodes()) {
            if (node.getId() == Id) return node;
        }
        return null;
    }

    // Getters/Setters
    
    public synchronized void setTopId (int Id) {
        _topId = Id;
    }
    public int getTopId () {
        return _topId;
    }
    public int getWidth () {
       return _netWidth;
    }
    public void synchronized setWidth (int width) {
        _netWidth = width;
    }

    public int getLength () {
        return _netLength;
    }
    public void synchronized setLength (int length) {
        _netLength = length;
    }

    public ArrayList<Node> getNodes () {
        return _nodes;
    }
    public void synchronized addNode (Node node) {
        _nodes.add(node);
    }

    public ArrayList<InputNode> getInputs () {
        return _inputs;
    }
    public void synchronized addInput (InputNode node) {
        _inputs.add(node);
    }

    public ArrayList<OutputNode> getOutputs () {
        return _outputs;
    }
    public void synchronized addOutput (OutputNode node) {
        _outputs.add(node);
    }

    public ArrayList<Link> getLinks () {
        return _links;
    }
    public void synchronized addLink (Link link) {
        getLinks().add(link);
    }

}
