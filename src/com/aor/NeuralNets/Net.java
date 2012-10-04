package com.aor.NeuralNets;

import java.util.ArrayList;


public class Net {

    private ArrayList<Node> _nodes;
    private ArrayList<InputNode> _inputs;
    private ArrayList<OutputNode> _outputs;
    private ArrayList<Link> _links;
    private int _topId = 0;
    

    public Net () {
        _nodes = new ArrayList<Node>();
        _inputs = new ArrayList<InputNode>();
        _outputs = new ArrayList<OutputNode>();
        _links = new ArrayList<Link>();
        generateNet();
        //for (int i = 0; i < 10; i++) {
            runNet();
        //}
    }

    /*
     * Populate this net with random stuffs
     */
    public void generateNet () {
        System.out.println("Generating net, or something");
        
        // 4, 4, link, do four times

        ArrayList<Node> firstLayer = new ArrayList<Node>();
        ArrayList<Node> secondLayer = new ArrayList<Node>();
        
        // populate the first layer
        for (int i = 0; i < 4; i++) {
            InputNode firstNode = new InputNode(getNextId());

            firstLayer.add(firstNode);
            _inputs.add(firstNode);
            _nodes.add(firstNode);
    
        }
        
        
        for (int j = 0; j < 4; j++) {

            for (int i = 0; i < 4; i++) {

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

    public void runNet () {
        // Test sending something in
        double startingInput = 2.0;//NeuralNets.generator.nextInt(100);
        double workingInput = startingInput;
        for (Node input : _inputs) {
            input.recieveTrigger(workingInput);
            workingInput += 2.0;
        }
        
        
        double targetValue = workingInput; // Value we want as output.
        System.out.println("Target: " + targetValue);
        
        // Store the nodes that are ready so we don't get confused later
        ArrayList<Node> readyNodes = new ArrayList<Node>();
        for (Node node : _nodes) {
            if (node.getReady()) {
                readyNodes.add(node);
            }
        }
        while (readyNodes.size() != 0) {
            System.out.println("----Activating next layer----");
            for (Node node : readyNodes) {
                    //System.out.println("Activating node with:");
                    //System.out.println(node);
                    node.sendTrigger();
            }

            readyNodes = new ArrayList<Node>();
            for (Node node : _nodes) {
                if (node.getReady()) {
                    readyNodes.add(node);
                }
            }
        }
        
        double output = _outputs.get(0).getOutput();
        
        if (output != targetValue) {
            double delta = targetValue - output;
            double gradient = delta * startingInput;
            System.out.println("Target: " + targetValue);
            System.out.println("Delta: " + delta);
            System.out.println("Gradient: " + gradient);
            ArrayList<Link> links = _outputs.get(0).getInputs();
            for (Link link : _links) {
                link.setWeight( link.getWeight() + delta );
            }
            System.out.println("Output from net: " + output);
        }


    }



    // Helper methods
    
    public int getNextId() {
        _topId++;
        System.out.println("Assigning ID: " + _topId);
        return _topId;
    }


}
