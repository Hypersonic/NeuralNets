package com.aor.NeuralNets;

import java.util.Random;
import java.util.ArrayList;

public class NeuralNets {

    public static final int MAX_INPUT = (int) Math.pow(2,16); //Double.MAX_VALUE;
    public static final Random generator = new Random();

    public static void main (String[] args) {
        System.out.println("MAX_INPUT: " + MAX_INPUT);

        int numberOfNets = 100;
        Net[] nets = new Net[numberOfNets];
        
        int netLength, netWidth;
        netLength = netWidth = 6;

        for (int runs = 0; runs < numberOfNets; runs++) {

            Net firstNet = new Net();
            firstNet.generateNet(netLength, netWidth);

            Net secondNet = new Net();
            secondNet.generateNet(netLength, netWidth);


            for (int i = 0; i < 5000; i++) {

                double expectedOutput = 10 + (NeuralNets.generator.nextInt(300)*2);

                double firstOut = firstNet.runNet(expectedOutput);
                double secondOut = secondNet.runNet(expectedOutput);
                double firstDelta = Math.abs(expectedOutput - firstOut);
                double secondDelta = Math.abs(expectedOutput - secondOut);

                //System.out.println("Expected: " + expectedOutput);
                //System.out.println("\tGot: " + firstOut + " Delta: " + firstDelta);
                //System.out.println("\tGot: " + secondOut + " Delta: " + secondDelta);

                if ( secondDelta < firstDelta ) {
                    //System.out.println("Net dying, age of: " + firstNet.age);
                    firstNet = secondNet;
                    firstDelta = secondDelta;
                    firstOut = secondOut;
                }
                firstNet.reset();
                firstNet.age++;
                secondNet = firstNet.clone();
                secondNet.mutate(-firstDelta);
            }
            nets[runs] = firstNet;

            double out = firstNet.runNet(30);
            System.out.println("In: 30, Out: " + out);
        }

        double closest = Double.MAX_VALUE;
        Net closestNet = null;
        for (Net net : nets) {
            double expected = 30;
            double out = net.runNet(expected);
            double delta = Math.abs(expected - out);
            if (delta < closest) {
                closest = delta;
                closestNet = net;
            }

        }

        System.out.println("\n\n------RESULT-------");
        for (int i = -100; i < 100; i += 10) {
            System.out.println("In: " + i + ", Out: " + closestNet.runNet(i));
        }

    }
}
