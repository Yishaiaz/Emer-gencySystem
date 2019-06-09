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

    /**
     *
     * @param username
     * @param password
     * @throws Exception
     */
    public void changeCurrentUserPassword(String username, String password) throws Exception{
        // todo: change to actualy retrieve user password and check
    }

    /**
     * this needs to throw an exception if the credentials do not match
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public Map<String,String> logInUser(String userName, String password) throws Exception {
        Map<String, String> userData = new HashMap<>();
        userData.put("username", userName);
        userData.put("organization", "epicenter");
        return userData;// todo: change to actual authentication

    }

    /**
     *
     * @param username
     * @param title
     * @param categories
     * @param organizations
     * @param details
     * @throws Exception
     */
    public void createNewEvent(String username, String title, String[] categories, String[] organizations, String details) throws Exception{

    }

    /**
     *
     * @return
     * @throws Exception
     */
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

    /**
     *
     * @return
     * @throws Exception
     */
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
