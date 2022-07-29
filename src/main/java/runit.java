

//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;

import java.util.ArrayList;

public class runit {


    public static void main(String[] args) {
        //System.out.println("hello");
        // ThingsBoard REST API URL
        ThingsBoard brd = new ThingsBoard();
        brd.login();
        System.out.println(brd);
// Get information of current logged in user and print it
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<String> RFids = new ArrayList<>();

        String[] types={""};
        for(String type: types) {
            locations.addAll(brd.getNames(brd.getTenantAssetsByType(type)));
        }
        System.out.println(locations);
        for (String loc: locations) {
            System.out.println(loc);
        }
        brd.ChangeRelationship("E28011710000020D56CF9D8D","TestLoc");

        // Perform logout of current user and close the client
        brd.connection_close();
        String file = "C:\\Users\\nrk_pavilion\\IdeaProjects\\thingsboardconnection_example\\src\\main\\java\\RFID.csv";
        //brd.parsecsv(file);

    }
}