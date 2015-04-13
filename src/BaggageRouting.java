import com.sanjeev.routing.BaggageRouterEngine;
import com.sanjeev.routing.dataobject.RoutingDO;
import com.sanjeev.scanner.ScanInput;

import java.io.File;
import java.io.IOException;

/**
 * Created by shemnani on 13/4/15.
 */
public class BaggageRouting {

    public static void main(String... args)
    {

        /*
        Scanning the input file for the Conveyer System data, departures data, and bags data.

        In ideal world, the Conveyer system data would remain constant for an airport, so it would make sense to
        parse the Conveyer System data from a separate file.
        And this parsed data should be processed for calculating shorted paths between different nodes, and then
        the processed data should be stored in a static object.
        A static function could also be offered to update this data, in case there are delays between nodes due to
        various reasons like construction, new gate openings etc.

        Flight and Bag data should be a constant input flow.

        However, since the use case presented contains a single file input, and a single file output, hence coding
        to those semantics i.e parsing all data in one single core function, and converting them to VO objects for
        further processing.
         */

        try {
            if(args.length!=1){
                throw new Exception("Enter the Input file path as the JVM argument. Only one parameter accepted");
            }

            ScanInput si = new ScanInput();
            si.scanInputFile(new File(args[0]));

            RoutingDO rdo = new RoutingDO();
            BaggageRouterEngine.doRouting(rdo, si.getBaggageRoutingTO());


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Input File :: " + e);
        }


    }

}
