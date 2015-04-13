package com.sanjeev.scanner;

import com.sanjeev.scanner.transferobject.BaggageRouterTransferObject;
import com.sanjeev.valueobject.BagVO;
import com.sanjeev.valueobject.FlightVO;
import com.sanjeev.valueobject.NodeVO;

import java.io.*;

/**
 * Created by shemnani on 12/4/15.
 */

/*
    Scans the input file provided to fetch Conveyer system nodes, departures and bags data and store them in transfer
    object.
 */
public class ScanInput {
    public BaggageRouterTransferObject baggageRoutingTO =  new BaggageRouterTransferObject();

    public BaggageRouterTransferObject getBaggageRoutingTO() {
        return baggageRoutingTO;
    }

    public void addNodeVO(String nodeInfo) {
    try{
            String[] nodeMapEntry = nodeInfo.split(" ");
            if(nodeMapEntry.length != 3){
                throw new Exception("Not enough values in line");
            }

            NodeVO sourceNode = baggageRoutingTO.getAllNodes().get(nodeMapEntry[0]);
            /* If the source node in this line doesn't exist, then create one */
            if (sourceNode == null){
                sourceNode = new NodeVO(nodeMapEntry[0]);
                /* Also add it to the nodes list so that if it is chosen as a start point in bag routing */
                baggageRoutingTO.getAllNodes().put(sourceNode.getNodeName(), sourceNode);
            }

            NodeVO destinationNode = baggageRoutingTO.getAllNodes().get(nodeMapEntry[1]);
            /* If the destination node in this line doesn't exist, then create one */
            if (destinationNode == null){
                destinationNode = new NodeVO(nodeMapEntry[1]);
                /* Also add it to the nodes list so that if it is chosen as a start point in bag routing */
                baggageRoutingTO.getAllNodes().put(destinationNode.getNodeName(), destinationNode);
            }



            /* Once the source and destination nodes are found,
            add the destination node to outNodes list of source node
            and add the source node to outNodes list of destination node

            This operation can be done atomic with lines above to add source and destination nodes to the list,
            but keeping it separate so that the nodes still remain in existence even if the time is not a valid entry
            */
            sourceNode.getOutNodes().put(destinationNode, Double.valueOf(nodeMapEntry[2]));
            destinationNode.getOutNodes().put(sourceNode, Double.valueOf(nodeMapEntry[2]));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Line Entry Invalid. Ignoring :: " + nodeInfo + "\t" + e);
            return;
        }
    }


    public void addBagVO(String bagInfo) {

        try {
            String[] bagDetails = bagInfo.split(" ");

            if(bagDetails.length != 3) {
                throw new Exception("Not enough values in line");
            }
            BagVO bagVO = new BagVO(bagDetails[0], bagDetails[1], bagDetails[2]);
            baggageRoutingTO.getBagVOList().add(bagVO);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Line Entry Invalid. Ignoring :: " + bagInfo + "\t" + e);
            return;
        }
    }

    public void addFlightVO(String flightInfo){

       try {
            String[] flightDetails = flightInfo.split(" ");

            if(flightDetails.length != 4) {
                throw new Exception("Not enough values in line");
            }
            FlightVO flightVO = new FlightVO(flightDetails[0], flightDetails[1], flightDetails[2], flightDetails[3]);

           baggageRoutingTO.getFlightVOMap().put(flightVO.getFlightName(), flightVO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Line Entry Invalid. Ignoring :: " + flightInfo + "\t" + e);
            return;
        }

    }

    public void scanInputFile(File inputFile) throws IOException {

        int sectionBeingParsed = -1;
        BufferedReader br = null;
        String lineEntry = null;

        br = new BufferedReader(
                new FileReader(inputFile));
        while ((lineEntry = br.readLine()) != null)
        {

            /* If the current line entry is a start of new section,
                then update the current section, and move to next line */
            if (lineEntry.startsWith("# Section:")) {

                if(lineEntry.contains("Conveyor System")){
                   sectionBeingParsed = 0;
                   continue;
                }
                else if(lineEntry.contains("Departures")){
                    sectionBeingParsed = 1;
                    continue;
                }
                else if(lineEntry.contains("Bags")){
                    sectionBeingParsed = 2;
                    continue;
                } else {
                    sectionBeingParsed = 3;
                    continue;
                }

            } else {
                /* If the current line is not a section header line,
                then parse the line according to the section being parsed
                */
                switch (sectionBeingParsed){
                    case 0 : {
                        addNodeVO(lineEntry);
                        break;
                    }
                    case 1 : {
                        addFlightVO(lineEntry);
                        break;
                    }
                    case 2 : {
                        addBagVO(lineEntry);
                        break;
                    }
                    default : {
                        System.out.println("Oops! Being here means you have a wrong section. Line being parsed :: " + lineEntry);
                    }
                }
            }
            //System.out.println(lineEntry);
        }



    }

}
