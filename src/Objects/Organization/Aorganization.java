package Objects.Organization;

import DataBaseConnection.SqliteDbConnection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public abstract class Aorganization {
    Set<String> members;
    Map<String,String> orgInfo;
    protected SqliteDbConnection db;





    public Aorganization(String name)throws Exception{


        db = SqliteDbConnection.getInstance();

        members = new HashSet<>();

        orgInfo = db.getEntryData("Organizations",name,"name");

        LinkedList<Map<String,String>> allEntrys = db.getAllFromTable(name+"_members");
        for (Map<String,String> m : allEntrys){
            members.add(m.get("member"));
        }



    }

    public boolean isMember(String name){
        if(members.contains(name))
            return true;
        return false;
    }

    public String getName() {
        return orgInfo.get("name");
    }

    public String getType() {
        return orgInfo.get("type");
    }

    public String getAdmnin() {
        return orgInfo.get("admin");
    }
}
