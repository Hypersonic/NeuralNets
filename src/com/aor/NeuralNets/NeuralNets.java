package com.aor.NeuralNets;

import java.util.Random;
import java.util.ArrayList;

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

        int numberOfNets = 50;
        Net[] nets = new Net[numberOfNets];
        
        for (int runs = 0; runs < numberOfNets; runs++) {

            Net firstNet = new Net();
            firstNet.generateNet();

            Net secondNet = new Net();//firstNet.clone();
            secondNet.generateNet();
            //secondNet.mutate();


            for (int i = 0; i < 3000; i++) {

                double expectedOutput = 10 + (NeuralNets.generator.nextInt(300)*2);

                double firstOut = firstNet.runNet(expectedOutput);
                double secondOut = secondNet.runNet(expectedOutput);
                double firstDelta = Math.abs(expectedOutput - firstOut);
                double secondDelta = Math.abs(expectedOutput - secondOut);

                //System.out.println("Expected: " + expectedOutput);
                //System.out.println("\tGot: " + firstOut + " Delta: " + firstDelta);
                //System.out.println("\tGot: " + secondOut + " Delta: " + secondDelta);

                if ( secondDelta < firstDelta ) {
                    //System.out.println("Net dying, awesomeness of: " + firstNet.awesomeness);
                    firstNet = secondNet;
                    firstDelta = secondDelta;
                    firstOut = secondOut;
                }
                firstNet.reset();
                firstNet.awesomeness++;
                secondNet = firstNet.clone();
                secondNet.mutate(firstDelta);
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
