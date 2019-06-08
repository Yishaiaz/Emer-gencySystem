package Objects.users;

import java.util.Map;

public class R_user extends Auser{
    String rank;


    public R_user(String name) {
        super(name);
        rank = db.getEntryData("Ranks","name");
    }



    public R_user(String name, String password, String status, String email, String organization,String rank) {
        super(name, password, status, email, organization);
        this.rank = rank;
    }

}
