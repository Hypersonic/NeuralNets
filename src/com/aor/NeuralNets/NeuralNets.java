package com.aor.NeuralNets;

import java.util.Random;

public class NeuralNets {

    public static final int MAX_INPUT = (int) Math.pow(2,16); //Double.MAX_VALUE;
    public static Random generator = new Random();

    public static void main (String[] args) {
        System.out.println("MAX_INPUT: " + MAX_INPUT);
        Net mainNet = new Net();
    }

}
