import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
public class ThingsBoard {
    // TEPMSENS URL
     private final String url;
     RestClient client;
    // Tenant Administrator credentials
    private final String username;
    private final String password;
    PageData<Asset> assets;
    public ThingsBoard() {
        // Default Tenant Administrator credentials as ADMIN!
        url = "https://tempsens.isi.gr";
        username = "tempsenstb@isi.gr";
        password = "fyu8h6bs4Sa3DEx";
        client = new RestClient(url);
    }
    public void connection_close(){
        // Perform logout of current user and close the client
        client.logout();
        client.close();}
    public void login(){
        client.login(username,password);
    }
    public PageData<Asset> get_tenants_assets(){
        PageLink pageLink = new PageLink(10);
            do {
        // Fetch all tenant devices using current page link and print each of them
            assets = client.getTenantAssets( pageLink,"");
            assets.getData().forEach(System.out::println);
            pageLink = pageLink.nextPageLink();
        } while (assets.hasNext());
        return assets;
    }
    // debug
    //client.getUser().ifPresent();
    public String toString(){
        return client.getUser().toString();
    }
}
