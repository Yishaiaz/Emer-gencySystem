package Objects.users;

import DataBaseConnection.SqliteDbConnection;

import java.util.HashMap;
import java.util.Map;

public abstract class Auser {

    Map<String,String> userInfo;
    protected SqliteDbConnection db;


    public Auser(String name)throws Exception{

        db = SqliteDbConnection.getInstance();

       userInfo = db.getEntryData("Users",name,"name");
       if(userInfo==null)
           throw new Exception("the user "+name + "does not exist");
    }
    public Auser(String name,String password, String status, String email,String organization,String admin){
        userInfo = new HashMap<>();
        userInfo.put("name",name);
        userInfo.put("password",password);
        userInfo.put("status",status);
        userInfo.put("email",email);
        userInfo.put("organization",organization);
        userInfo.put("admin",admin);

    }

    public void addtoDB()throws Exception{
        String[] entry = {userInfo.get("name"),userInfo.get("password"),userInfo.get("status"),userInfo.get("email"),userInfo.get("organization"),userInfo.get("admin")};
        db.insert("Users",entry);
        String[] enntry2 = {getOrganization(),getName()};
        db.insert("OrginizationsUsers",enntry2);
    }


    public void changeUserPassword(String userId,String newPassword)throws Exception {
        String[] entry = {getName(),newPassword,getStatus(),getEmail(),getOrganization()};
        db.updateEntry("Users",entry, getName(),"name");
    }


    public String getStatus() {
        return userInfo.get("status");
    }

    public String getPassword() {
        return userInfo.get("password");
    }

    public String getEmail() {
        return userInfo.get("email");
    }

    public String getName() {
        return userInfo.get("name");
    }

    public String getOrganization() {
        return userInfo.get("organization");
    }

    public String getAdmin() {
        return userInfo.get("admin");
    }

    public void printInfo(){
        String ans = "user info: {";
        String value;
        for(String s : userInfo.keySet()){
            value = userInfo.get(s);
            ans = ans + s +"="+ userInfo.get(s)+",";
        }
        ans+="}";
        System.out.println(userInfo);
    }
}
