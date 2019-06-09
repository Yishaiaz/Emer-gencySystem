package Objects.Permissions;

import DataBaseConnection.SqliteDbConnection;
import Objects.Organization.EmergencyForce;

import java.util.Set;

public class ReadPermision {
    protected String eventId;
    protected SqliteDbConnection db;


    public ReadPermision(String eventId) throws Exception{
        this.eventId = eventId;
        db = SqliteDbConnection.getInstance();


    }

    public void addPermissions(String group)throws Exception{
        EmergencyForce ef = new EmergencyForce(group);
        Set<String> members =  ef.getAllMembers();

        for(String member : members){
            String[] entry = {eventId,member};
            db.insert("ReadPermissions",entry);
        }
        System.out.println("all members of "+ group + "have been added to ReadPermission list");

    }
}
