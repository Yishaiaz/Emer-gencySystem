package Objects.Permissions;

import Objects.Organization.EmergencyForce;
import Objects.users.A_user;
import Objects.users.Auser;
import Objects.users.R_user;

import java.util.Map;
import java.util.Set;

public class WritePermissions extends ReadPermision {

    int rank;

    public WritePermissions(String eventId,int rank) throws Exception {
        super(eventId);
        this.rank = rank;
    }

    @Override
    public void addPermissions(String group) throws Exception {
        EmergencyForce ef = new EmergencyForce(group);
        Set<String> members =  ef.getAllMembers();

        for(String member : members){
            Auser user = getUser(member);
            if(user instanceof R_user && ((R_user)user).getRank() >= rank) {
                String[] entry = {eventId, member};
                db.insert("WritePermissions", entry);
            }



        }

    }

    public Auser getUser(String name) throws Exception{
        Map<String,String>  userInfo = db.getEntryData("Users",name,"name");
        if(userInfo.get("admin").equals("true"))
            return new A_user(name);
        else return new R_user(name);
    }
}
