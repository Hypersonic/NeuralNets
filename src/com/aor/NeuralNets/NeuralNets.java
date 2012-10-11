package com.aor.NeuralNets;

import java.util.Random;

public class NeuralNets {

    public static final int MAX_INPUT = (int) Math.pow(2,16); //Double.MAX_VALUE;
    public static Random generator = new Random();

    public static void main (String[] args) {
        System.out.println("MAX_INPUT: " + MAX_INPUT);
        Net mainNet = new Net();
        mainNet.generateNet();
        System.out.println("--------Running main net...--------");
        mainNet.runNet();
        System.out.println("------Cloning------");
        Net cloneNet = mainNet.clone();
        cloneNet.mutate();
        System.out.println("--------Running clone net...--------");
        cloneNet.runNet();
        double first = mainNet.getOutputs().get(0).getOutput();
        double second = cloneNet.getOutputs().get(0).getOutput();

    }
}
