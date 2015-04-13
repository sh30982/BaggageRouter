package com.sanjeev.routing.dataobject;

/**
 * Created by shemnani on 13/4/15.
 */

/*
    Contains below information about bag node;
        1.  Distance Matrix - Shortest possible distance between each pair of nodes
        2.  Sequence Matrix - Shortest possible route between each pair of nodes
        3.  Number of nodes
        4.  Array of Node names
 */

public class RoutingDO {

    private double distanceMatrix[][];
    private String nodeSequenceMatrix[][];
    private int noOfNodes;
    private String[] nodesArray;


    public void initializeRoutingDO(int noOfNodes) {
        this.distanceMatrix = new double[noOfNodes][noOfNodes];
        this.nodeSequenceMatrix = new String[noOfNodes][noOfNodes];
        this.noOfNodes = noOfNodes;
        this.nodesArray = new String[noOfNodes];
    }

    public RoutingDO() {
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public String[][] getNodeSequenceMatrix() {
        return nodeSequenceMatrix;
    }

    public void setNodeSequenceMatrix(String[][] nodeSequenceMatrix) {
        this.nodeSequenceMatrix = nodeSequenceMatrix;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public void setNoOfNodes(int noOfNodes) {
        this.noOfNodes = noOfNodes;
    }

    public String[] getNodesArray() {
        return nodesArray;
    }

    public void setNodesArray(String[] nodesArray) {
        this.nodesArray = nodesArray;
    }
}
