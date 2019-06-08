package Objects.Organization;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public abstract class Aorganization {
    Set<String> members;
    Map<String,String> userInfo;





    public Aorganization(String name){
        members = new HashSet<>();

        userInfo = Db.getEntryData("Organizations",name);

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
        return userInfo.get("name");
    }

    public String getType() {
        return userInfo.get("type");
    }

    public String getAdmnin() {
        return userInfo.get("admin");;
    }
}
