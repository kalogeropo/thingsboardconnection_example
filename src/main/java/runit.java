import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class runit {


    public static void main(String[] args) {
        //System.out.println("hello");
        // ThingsBoard REST API URL
        String url = "https://tempsens.isi.gr";

        // Default Tenant Administrator credentials
        String username = "tempsenstb@isi.gr";
        String password = "fyu8h6bs4Sa3DEx";

        // Creating new rest client and auth with credentials
        RestClient client = new RestClient(url);
        client.login(username,password);
        //System.out.println(client);

// Get information of current logged in user and print it
        client.getUser().ifPresent(System.out::println);
        PageData<Asset> assets;
        PageLink pageLink = new PageLink(10);
        do {
            // Fetch all tenant devices using current page link and print each of them
            assets = client.getTenantAssets( pageLink,"");
            assets.getData().forEach(System.out::println);
            pageLink = pageLink.nextPageLink();
        } while (assets.hasNext());
// Perform logout of current user and close the client
        client.logout();
        client.close();

    }
}