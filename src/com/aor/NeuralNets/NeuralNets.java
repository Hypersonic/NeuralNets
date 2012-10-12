package com.aor.NeuralNets;

import java.util.Random;

public class NeuralNets {

    public static final int MAX_INPUT = (int) Math.pow(2,16); //Double.MAX_VALUE;
    public static Random generator = new Random();

    public static void main (String[] args) {
        System.out.println("MAX_INPUT: " + MAX_INPUT);

        //Net mainNet = new Net();
        //mainNet.generateNet();

        //System.out.println("--------Running main net...--------");
        //double firstOut = mainNet.runNet();

        //System.out.println("------Cloning------");
        //Net cloneNet = mainNet.clone();
        //cloneNet.mutate();

        //System.out.println("--------Running clone net...--------");
        //double secondOut = cloneNet.runNet();

        Net firstNet = new Net();
        firstNet.generateNet();

        Net secondNet = new Net();//firstNet.clone();
        secondNet.generateNet();
        //secondNet.mutate();


        for (int i = 0; i < 50000; i++) {
        
            double expectedOutput = 10 + (NeuralNets.generator.nextInt(300)*2);//NeuralNets.generator.nextInt(50) * 2;//10;
            //if (i > 500) expectedOutput = NeuralNets.generator.nextInt(10);        

            double firstOut = firstNet.runNet(expectedOutput);
            double secondOut = secondNet.runNet(expectedOutput);
            double firstDelta = Math.abs(expectedOutput - firstOut);
            double secondDelta = Math.abs(expectedOutput - secondOut);

            System.out.println("Expected: " + expectedOutput);
            System.out.println("\tGot: " + firstOut + " Delta: " + firstDelta);
            System.out.println("\tGot: " + secondOut + " Delta: " + secondDelta);

            if ( secondDelta < firstDelta ) {
                firstNet = secondNet;
            }
            firstNet.reset();
            secondNet = firstNet.clone();
            secondNet.mutate();
        }

        double out = firstNet.runNet(30);
        System.out.println("In: 30, Out: " + out);
    }
}
