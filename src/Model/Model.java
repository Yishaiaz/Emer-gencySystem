package Model;

import DataBaseConnection.IdbConnection;
import Objects.Organization.Epicenter;
import Objects.users.A_user;
import Objects.users.Auser;
import Objects.users.R_user;

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

        Auser user = getUser(username);
        user.changeUserPassword(username,password);
    }

    /**
     * this needs to throw an exception if the credentials do not match
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public Map<String,String> logInUser(String userName, String password) throws Exception {
        Auser user = getUser(userName);
        if(user.getPassword().equals(password)) {



            Map<String, String> userData = new HashMap<>();
            userData.put("username", user.getName());
            if(user instanceof A_user)
                userData.put("rank", "");
            else{
                userData.put("rank", ((R_user)user).getRank()+"");
            }
            userData.put("organization", user.getOrganization());
            userData.put("isAdmin", user.getAdmin());
            return userData;// todo: change to actual authentication
        }
        else{
            throw new Exception("ERROR: invalid password");
        }
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

        Epicenter.getInstance().createEvent(username,categories,organizations,title,details);
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

    public void createNewCategory(String newCategory) throws Exception{
        Epicenter.getInstance().addCategory(newCategory);

    }

    public Auser getUser(String name) throws Exception{
        Map<String,String>  userInfo = db.getEntryData("Users",name,"name");
        if(userInfo.get("admin").equals("true"))
            return new A_user(name);
        else return new R_user(name);
    }
}
