package com.sanjeev.routing;
import com.sanjeev.routing.dataobject.RoutingDO;
import com.sanjeev.scanner.transferobject.BaggageRouterTransferObject;
import com.sanjeev.valueobject.BagVO;
import com.sanjeev.valueobject.FlightVO;
import com.sanjeev.valueobject.NodeVO;
import java.util.Map;
import java.util.Set;


/*
    The BaggageRouterEngine takes the parsed data and:
        1.  Creates shortest path between all node pairs.
        2.  Loops thru each Bag Item, and finds its source and destination (also considers flight gates if destination
        is a departure)
        3.  Chooses the shorted available path to the destination for the bag
        4.  Fetches the travel time on the shortest available path for the bag

    Importantly, this class has all functions as static so that no object needs to be created of Engine.
    It is just an entity in itself that does the job on task handed over to it.

 */
public class BaggageRouterEngine
{


    public static void prepareOptimalBagTravelRoutes(RoutingDO rdo)
    {
        for (int intermediate = 0; intermediate < rdo.getNoOfNodes() -1; intermediate++)
        {
            for (int source = 0; source < rdo.getNoOfNodes(); source++)
            {
                /* If the current iteration counter is on the same value as source counter,
                    then ignore this iteration as we won't find a shorter path in this condition
                     */
                if (intermediate == source)
                    continue;

                for (int destination = 0; destination < rdo.getNoOfNodes(); destination++)
                {

                    /* If the current iteration counter is on the same value as destination counter,
                    then ignore this iteration as we won't find a shorter path in this condition
                     */
                    if (intermediate == destination)
                        continue;

                    /*
                    Checking for a shorter route between source and destination by checking if node at intermediate
                    position offers a shorter route than current entry
                     */
                    if (rdo.getDistanceMatrix()[source][intermediate] + rdo.getDistanceMatrix()[intermediate][destination]
                            < rdo.getDistanceMatrix()[source][destination]) {
                        /* If a path with lesser time is found, then update the path time in both
                        Source to Destination
                        Destination to Source
                         */
                        rdo.getDistanceMatrix()[source][destination] = rdo.getDistanceMatrix()[source][intermediate]
                                + rdo.getDistanceMatrix()[intermediate][destination];
                        rdo.getDistanceMatrix()[destination][source] = rdo.getDistanceMatrix()[source][intermediate]
                                + rdo.getDistanceMatrix()[intermediate][destination];

                        /* If a path with lesser time is found, then update the path sequence in both
                        Source to Destination
                        Destination to Source
                         */
                        rdo.getNodeSequenceMatrix()[source][destination] = rdo.getNodesArray()[intermediate];
                        rdo.getNodeSequenceMatrix()[destination][source] = rdo.getNodesArray()[intermediate];
                    }
                }
            }
        }

    }

    /*
    Handles the routing.
    Inputs are routing data object and routing transfer object
     */
    public static void doRouting(RoutingDO rdo, BaggageRouterTransferObject brto)
    {
        /*
        Initializes the node sequence and distance matrices, and then prints them.
        Takes input as transfer object and routing data object so that transfer object can be converted to dataobject
         */
        initializeMatrices(rdo, brto);
        //printMatrices(rdo);

        /*
        Calculates the shortest path node sequences and distances, updates them in respective matrices,
        and then prints them
         */
        prepareOptimalBagTravelRoutes(rdo);
        //printMatrices(rdo);

        /*
        Does the routing of bags based on shortest paths
         */
        routeBags(rdo, brto);
    }

    /*
       Initializes the node sequence and distance matrices and updates them in routing object
       Takes input as transfer object and routing data object
        */
    private static void initializeMatrices(RoutingDO rdo, BaggageRouterTransferObject brto){


        Set<String> keySet = brto.getAllNodes().keySet();
        //rdo.setNoOfNodes(keySet.size());
        rdo.initializeRoutingDO(keySet.size());
        keySet.toArray(rdo.getNodesArray());
        String nodeName;

        for( int i=0; i< rdo.getNoOfNodes() ; i++) {
            nodeName = rdo.getNodesArray()[i];
            Map<NodeVO, Double> childNodes = brto.getAllNodes().get(nodeName).getOutNodes();

            for (int j=0; j< rdo.getNoOfNodes(); j++) {
                rdo.getNodeSequenceMatrix()[i][j] = "";
                if (i==j){
                    rdo.getDistanceMatrix()[i][j] = 0.0;
                } else {
                    rdo.getDistanceMatrix()[i][j] = Double.POSITIVE_INFINITY;
                }

                for (NodeVO childNode : childNodes.keySet()) {
                    if (childNode.getNodeName().equalsIgnoreCase(rdo.getNodesArray()[j])){
                        rdo.getNodeSequenceMatrix()[i][j] = rdo.getNodesArray()[j];
                        rdo.getDistanceMatrix()[i][j] = childNodes.get(childNode);

                        rdo.getNodeSequenceMatrix()[j][i] = rdo.getNodesArray()[i];
                        rdo.getDistanceMatrix()[j][i] = childNodes.get(childNode);
                    }
                }
            }
        }

    }


    /*
    Routes all bags provided in the input.

    Takes inputs as
    1.  routing data object for node travel time and path info
    2.  routing transfer object for getting departure and bag input information
     */
    private static void routeBags(RoutingDO rdo, BaggageRouterTransferObject brto) {

        for (BagVO bagVO : brto.getBagVOList()) {
            FlightVO nextFlight = null;
            String bagDestination = bagVO.getDestination();
            if (bagDestination.equalsIgnoreCase("ARRIVAL")) {
                bagDestination = "BaggageClaim";
            } else {
                nextFlight = brto.getFlightVOMap().get(bagDestination);
                bagDestination = nextFlight.getGate();
            }
            String bagTravelPath = calculateBagTravelPath(bagVO.getArrivalGate(), bagDestination, rdo);
            double bagTravelTime = calculateBagTravelTime(bagVO.getArrivalGate(), bagDestination, rdo);

            System.out.println(bagVO.getBagId() + "\t" + bagVO.getArrivalGate() + "-" + bagTravelPath + "\t" + bagTravelTime);
        }
    }


    /*
    Calculates the shortest travel time based on source and destination. Takes additional input as routing data object
    which has information of travel times between various nodes.
     */
    private static double calculateBagTravelTime(String arrivalGate, String bagDestination, RoutingDO rdo) {

        int arrivalIndex = -1;
        int destinationIndex = -1;
        for (int i=0; i<rdo.getNoOfNodes() ; i++) {
            if (rdo.getNodesArray()[i].equalsIgnoreCase(arrivalGate))
                arrivalIndex = i;
            if (rdo.getNodesArray()[i].equalsIgnoreCase(bagDestination))
                destinationIndex = i;
        }

        return rdo.getDistanceMatrix()[arrivalIndex][destinationIndex];
    }

    /*
    Calculates the shortest travel path based on source and destination. Takes additional input as routing data object
    which has information of travel paths between various nodes.
     */
    private static String calculateBagTravelPath (String arrivalGate, String bagDestination, RoutingDO rdo) {

        int arrivalIndex = -1;
        int destinationIndex = -1;
        for (int i=0; i<rdo.getNodesArray().length ; i++) {
            if (rdo.getNodesArray()[i].equalsIgnoreCase(arrivalGate))
                arrivalIndex = i;
            if (rdo.getNodesArray()[i].equalsIgnoreCase(bagDestination))
                destinationIndex = i;
        }

        String connectorNode = rdo.getNodeSequenceMatrix()[arrivalIndex][destinationIndex];

        if(connectorNode.equalsIgnoreCase(bagDestination)){
            return bagDestination;
        } else {
            return calculateBagTravelPath(arrivalGate, connectorNode, rdo) + "-" + calculateBagTravelPath(connectorNode, bagDestination, rdo);
        }

    }

    /*
    Prints the state of distance and sequence matrices between all pairs of nodes.
     */
    private static void printMatrices(RoutingDO rdo) {

        for (int source = 0; source < rdo.getNoOfNodes(); source++)
            System.out.print("\t" + rdo.getNodesArray()[source]);
        System.out.print("\n");
        for( int i=0; i< rdo.getNoOfNodes() ; i++) {
            System.out.print(rdo.getNodesArray()[i]);
            for (int j=0; j<rdo.getNoOfNodes(); j++) {
                System.out.print ("\t" + rdo.getNodeSequenceMatrix()[i][j]);
            }
            System.out.print("\n");
        }

        for (int source = 0; source < rdo.getNoOfNodes(); source++)
            System.out.print("\t" + rdo.getNodesArray()[source]);
        System.out.print("\n");
        for( int i=0; i< rdo.getNoOfNodes() ; i++) {
            System.out.print(rdo.getNodesArray()[i]);
            for (int j=0; j<rdo.getNoOfNodes(); j++) {
                System.out.print ("\t" + rdo.getDistanceMatrix()[i][j]);
            }
            System.out.print("\n");
        }
    }
}

