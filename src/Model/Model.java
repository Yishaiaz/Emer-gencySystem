package Model;

import DataBaseConnection.IdbConnection;
import Objects.Organization.Epicenter;

import java.sql.Date;
import java.util.*;

public class Model extends Observable implements IModel {
    private IdbConnection db;

    public Model(IdbConnection db) {
        this.db = db;
    }

    public void changeCurrentUserPassword(String username, String password) throws Exception{
        // todo: change to actualy retrieve user password and check
    }

    public boolean logInUser(String userName, String password) throws Exception {
        return true;// todo: change to actual authentication
    }

    public void createNewEvent(String username, String title, String[] categories, String[] organizations, String details) throws Exception{

    }

    public String[] getCategories() throws Exception{
        Epicenter epicenter = Epicenter.getInstance();
        String[] ans = new String[epicenter.getAllCategories().size()];
        int ctr = 0;
        for(String single: epicenter.getAllCategories()){
            ans[ctr] = single;
            ctr++;
        }
        return ans;
    }

    public String[] getEmergencyServices() throws Exception{
        LinkedList<Map<String, String>> tempAns = db.getAllFromTable("Organizations");
        String[] ans = new String[tempAns.size()];
        int i =0;
        for(Map<String, String> organization: tempAns){
            if(!organization.get("name").equals("epicenter")){
                ans[i] = organization.get("name");
                i++;
            }
        }
        return ans;
    }
}
