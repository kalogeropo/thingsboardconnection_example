import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.id.AssetId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.kv.AttributeKvEntry;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.common.data.relation.EntityRelation;
import org.thingsboard.server.common.data.relation.RelationsSearchParameters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
        url ="";

        username = "";
        password = "";
        client = new RestClient(url);
    }
    public void connection_close(){
        // Perform logout of current user and close the client
        client.logout();
        client.close();
    }
    public void login(){
        client.login(username,password);
    }
    public AssetId QueryIDbyName(String RFID){
        System.out.println("==============");
        AssetId Id = client.findAsset(RFID).get().getId();
        //client.deleteRelation(fromID,Type,TypeGroup,ToId);
        //client.deleteRelations(Id);
        return Id;
    }
    public String QueryTypebyName(String RFID){
        System.out.println("==============");
        String type = client.findAsset(RFID).get().getType();
        //client.deleteRelation(fromID,Type,TypeGroup,ToId);
        //client.deleteRelations(Id);
        return type;
    }
    public void ChangeRelationship(String FromName, String ToLocation){
        EntityId FromID = QueryIDbyName(FromName);
        EntityId ToLocationID = QueryIDbyName(ToLocation);
        String FromAssetType = QueryTypebyName(FromName);
        System.out.println(FromAssetType);
        String ToAssetType = QueryTypebyName(ToLocation);
        ArrayList<String> AcceptedTypes = new ArrayList<>(List.of("Milk","Cheese","Loc"));
        if(AcceptedTypes.contains(FromAssetType)&&AcceptedTypes.contains(ToAssetType)){
            //System.out.println("Delete "+ FromName+ "With id "+FromID+"\n"+"Adding relation to "+ToLocationID +" "+ToLocation );
            client.deleteRelations(FromID);
            EntityRelation newRel = new EntityRelation(FromID,ToLocationID,"Contains");
            client.saveRelation(newRel);
        }

    }
    public PageData<Asset> getTenantAssetsByType(String assetType) {
        //aggressivly high pageSize in order to avoid a while loop
        PageLink pageLink = new PageLink(1000);
        // Fetch all tenant devices using current page link and print each of them
        assets = client.getTenantAssets(pageLink, assetType);
        return assets;
    }
    public ArrayList<String> getNames(PageData <Asset> asset) {
        ArrayList<String> Names = new ArrayList<>();
        System.out.println(asset.getTotalElements());
        for(int i=0;i <asset.getTotalElements();i++) {
            Names.add(asset.getData().get(i).getName());
          //locations.add(String.valueOf(assets.getData().get(i).getId()));
        }
        return (Names);
    }

    //Deprecated after changed Names to RFIDs
    public ArrayList<String> getAssetsRFID (PageData <Asset> asset) {
        ArrayList<String> RFIDs  =new ArrayList<>();
        EntityId temp=null;
        //System.out.println(asset.getTotalElements());
        for(int i=0;i <asset.getTotalElements();i++) {
            temp = asset.getData().get(i).getId();
            List<AttributeKvEntry> attr =client.getAttributeKvEntries(temp,List.of("RFID Tag"));
                    //client.getAttributesByScope(temp,"SERVER_ATTRIBUTES", List.of("RFID Tag"));
           for(AttributeKvEntry at:attr){
               RFIDs.add((String) at.getValue());
               //System.out.println(at.getValue());
           }

        }
        return (RFIDs);
    }
    public String parsecsv(String file){
        ArrayList<String> prod = new ArrayList<>();
        ArrayList<String> prodType = new ArrayList<>();
        try {
            FileReader flreader = new FileReader(file);
            CSVReader csv = new CSVReader(flreader);
            String[] Record;
            while((Record = csv.readNext())!= null){
                for(String cell: Record){
                    System.out.print(cell+"\t");
                }
                System.out.println();

            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String str = null;
        return  str;
        }
    // debug
    //client.getUser().ifPresent();
    public String toString(){
        return client.getUser().toString();
    }
}
