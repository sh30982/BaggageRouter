package com.sanjeev.valueobject;

import java.util.HashMap;

/**
 * Created by shemnani on 12/4/15.
 */
/*
    Contains below information about each node;
        1.  Name
        2.  Out Nodes with travel time associated to each node
 */
public class NodeVO {

    HashMap<NodeVO, Double> outNodes = new HashMap<NodeVO, Double>();
    private String nodeName;

    public NodeVO(String nodeName) {
        this.nodeName = nodeName;
    }

    public HashMap<NodeVO, Double> getOutNodes() {
        return outNodes;
    }

    public void setOutNodes(HashMap<NodeVO, Double> outNodes) {
        this.outNodes = outNodes;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
