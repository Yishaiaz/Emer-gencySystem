package Objects.event;

import DataBaseConnection.SqliteDbConnection;
import Objects.Permissions.ReadPermision;
import Objects.Permissions.WritePermissions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Event {

    private Map<String,String> eventInfo;
    private SqliteDbConnection db;
    String id;
    int rank;



    //this constructor creates an event and ads it to the database
    public Event( String userId, String[] categories, String[] emergencyForces,String title,String status,String details,String date,int rank)throws Exception {
        db = SqliteDbConnection.getInstance();
        eventInfo = new HashMap<>();
        eventInfo.put("userId",userId);
        eventInfo.put("title",title);
        eventInfo.put("status",status);
        eventInfo.put("details",details);
        eventInfo.put("date",date);
        this.rank = rank;
        getIdFromDb();


        addToDb(categories,emergencyForces);
    }

    private void addToDb(String[] categories, String[] emergencyForces)throws Exception {
        //adds event to Events Table
        String[] entry = {id,getUserId(),getTitle(),getStatus(),getDetails(),getDate()};
        db.insert("Events", entry);

        //add categories to "EventsCategories"
        addCategoriesToDb(categories);
        //adds orginizations to "EventOrganizations"
        addEforcesToDb(emergencyForces);
        //adds read permissions to all orginizations involved
        addReadPermissions(emergencyForces);
        //adds write permissions
        addWritePermissions(emergencyForces);



    }

    private void getIdFromDb() throws Exception{
        LinkedList<Map<String, String>> ans= db.runQuery("SELECT id FROM Events ORDER BY id DESC LIMIT 1");
        if(ans.size()==0)
            id="1";
        else
            id=Integer.parseInt(ans.get(0).get("id"))+1+"";
    }


    private void addReadPermissions(String[] emergencyForces ) throws Exception{
        ReadPermision rp = new ReadPermision(id);
        for(String eForce : emergencyForces){
            rp.addPermissions(eForce);
        }
        rp.addPermissions("epicenter");
    }
    private void addWritePermissions(String[] emergencyForces ) throws Exception{
        WritePermissions rp = new WritePermissions(id,rank);
        for(String eForce : emergencyForces){
            rp.addPermissions(eForce);
        }
    }

    private void addEforcesToDb(String[] emergencyForces)throws Exception {
        for(String ef : emergencyForces){
            String[] entry = {id,ef};
            db.insert("EventsOrganizations",entry);
        }
    }

    private void addCategoriesToDb(String[] categories)throws Exception {
        for(String cat : categories){
            String[] entry = {id,cat};
            db.insert("EventsCategories",entry);
        }
    }

    public String getTitle() {
        return eventInfo.get("title");
    }

    public String getDate() {
        return eventInfo.get("date");
    }

    public String getStatus() {
        return eventInfo.get("status");
    }

    public String getDetails() {
        return eventInfo.get("details");
    }

    public String getUserId() {
        return eventInfo.get("userId");
    }
    public String getId(){
        return id;
    }
}
