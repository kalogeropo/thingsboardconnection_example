import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.asset.AssetInfo;
import org.thingsboard.server.common.data.asset.AssetSearchQuery;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;

import java.util.ArrayList;

public class ThingsBoard {
    // TEPMSENS URL
     private final String url;
     RestClient client;
    // Tenant Administrator credentials
    private final String username;
    private final String password;
    private PageData<Asset> assets;

    public ThingsBoard() {
        // Default Tenant Administrator credentials as ADMIN!
        url = "https://tempsens.isi.gr";
        username = "tempsenstb1@isi.gr";
        password = "39r$LA6E8nli";
        client = new RestClient(url);
    }
    public void connection_close(){
        // Perform logout of current user and close the client
        client.logout();
        client.close();}
    public void login(){
        client.login(username,password);
    }
    public PageData<Asset> getTenantAssetsByType(String assetType) {
        //aggressivly high pageSize in order to avoid a while loop
        PageLink pageLink = new PageLink(1000);
        // Fetch all tenant devices using current page link and print each of them
        assets = client.getTenantAssets(pageLink, assetType);
        return assets;
    }
    public ArrayList<String> getLocations(PageData <Asset> asset) {
        ArrayList<String> locations = new ArrayList<>();
        System.out.println(assets.getTotalElements());
        //assets.getData().get(i).getName();
        for(int i=0;i <assets.getTotalElements();i++) {
          locations.add(assets.getData().get(i).getName());
        }
        return (locations);
    }
    // debug
    //client.getUser().ifPresent();
    public String toString(){
        return client.getUser().toString();
    }
}
