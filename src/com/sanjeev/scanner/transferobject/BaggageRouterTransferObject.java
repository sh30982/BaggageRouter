package com.sanjeev.scanner.transferobject;

import com.sanjeev.valueobject.BagVO;
import com.sanjeev.valueobject.FlightVO;
import com.sanjeev.valueobject.NodeVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shemnani on 12/4/15.
 */


/*
    Contains below information;
        1.  List of all unique Nodes stored as <name, object> pairs
        2.  List of all bag objects scanned from input
        3.  List of all departure flights stored as <name, object> pairs
 */

public class BaggageRouterTransferObject {

    public BaggageRouterTransferObject() {}
    private Map<String, NodeVO> allNodes = new HashMap<String, NodeVO>();
    private List<BagVO> bagVOList = new ArrayList<BagVO>();
    private Map<String, FlightVO> flightVOMap = new HashMap<String, FlightVO>();

    public List<BagVO> getBagVOList() {
        return bagVOList;
    }

    public void setBagVOList(List<BagVO> bagVOList) {
        this.bagVOList = bagVOList;
    }

    public Map<String, FlightVO> getFlightVOMap() {
        return flightVOMap;
    }

    public void setFlightVOMap(Map<String, FlightVO> flightVOMap) {
        this.flightVOMap = flightVOMap;
    }

    public Map<String, NodeVO> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(Map<String, NodeVO> allNodes) {
        this.allNodes = allNodes;
    }
}

