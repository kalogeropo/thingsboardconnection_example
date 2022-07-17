

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class runit {


    public static void main(String[] args) {
        //System.out.println("hello");
        // ThingsBoard REST API URL
        ThingsBoard brd = new ThingsBoard();
        brd.login();
        System.out.println(brd);
// Get information of current logged in user and print it
        //brd.get_tenants_assets();
// Perform logout of current user and close the client
        brd.connection_close();

    }
}