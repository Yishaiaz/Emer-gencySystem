package Objects.Organization;

import DataBaseConnection.SqliteDbConnection;

import java.util.*;

public abstract class Aorganization {

    Map<String,String> orgInfo;
    protected SqliteDbConnection db;


    public Aorganization(String name)throws Exception{

        db = SqliteDbConnection.getInstance();
        orgInfo = db.getEntryData("Organizations",name,"name");
    }

    public  Aorganization(String name, String admin){

        orgInfo = new HashMap<>();
        orgInfo.put("name",name);
        orgInfo.put("admin",admin);
    }

    public void addToDB()throws Exception{
        String[] entry = {getName(),getAdmnin()};
        db.insert("Orginazations",entry);
    }

    public Set<String> getAllMembers()throws Exception{
        Set<String> members = new HashSet<>();
        LinkedList<Map<String,String>> allEntrys = db.getAllFromTable("OrganizationsUsers");
        for (Map<String,String> m : allEntrys){
            if(m.get("name").equals(orgInfo.get("name")))
            members.add(m.get("member"));
        }
        return members;
    }

    public boolean isMember(String name)throws Exception{
        if(getAllMembers().contains(name))
            return true;
        return false;
    }

    public String getName() {
        return orgInfo.get("name");
    }

    public String getAdmnin() {
        return orgInfo.get("admin");
    }

    public void printInfo(){
        System.out.println("OrgInfo: {name="+getName()+", admin="+getAdmnin()+"}");
    }
}
