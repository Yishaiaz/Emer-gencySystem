package Objects.users;

import java.util.Map;

public abstract class Auser {
    public String name;
    public String password;
    public String status;
    public String email;
    public String organization;


    public Auser(String name){

        Map<String,String> userInfo = Db.getUser(name);
        this.name = userInfo.get("name");
        this.password = userInfo.get("password");
        this.status = userInfo.get("status");
        this.email = userInfo.get("email");
        this.organization = userInfo.get("organization");
    }
    public Auser(String name,String password, String status, String email,String organization){
        this.name = name;
        this.password = password;
        this.status = status;
        this.email = email;
        this.organization = organization;
    }


    public void changeUserPassword(String userId,String newPassword)throws Exception {
        String[] entry = {name,newPassword,status,email,organization};
        db.updateEntry("users",entry, name);
    }

}
