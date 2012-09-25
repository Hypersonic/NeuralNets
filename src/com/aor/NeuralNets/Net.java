package com.aor.NeuralNets;

import java.util.ArrayList;


public class Net {

    private ArrayList<Node> _nodes;
    private ArrayList<Node> _inputs;
    private ArrayList<Link> _links;
    

    public Net () {
        _nodes = new ArrayList<Node>();
        _inputs = new ArrayList<Node>();
        _links = new ArrayList<Link>();
        generateNet();
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
            InputNode firstNode = new InputNode();

            firstLayer.add(firstNode);
            _inputs.add(firstNode);
            _nodes.add(firstNode);
    
        }
        
        
        for (int j = 0; j < 4; j++) {

            for (int i = 0; i < 4; i++) {

                NodeOps OpChoice = NodeOps.values()[NeuralNets.generator.nextInt(NodeOps.values().length)];
                Node secondNode = new Node(OpChoice);

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
        

        // TODO: Move this stuff to a run() function, give better stepping control

        // Test sending something in
        double startingInput = 2.0;
        for (Node input : _inputs) {
            input.recieveTrigger(startingInput);
            startingInput += 2.0;
        }
        // Store the nodes that are ready so we don't get confused later
        ArrayList<Node> readyNodes = new ArrayList<Node>();
        for (Node node : _nodes) {
            if (node.getReady()) {
                readyNodes.add(node);
            }
        }
        for (int i = 0; i < 4; i++) {
            System.out.println("\n\n\n----Activating next layer----");
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

    
    
    }

    
}
