package Objects.users;

import java.util.Map;

public class R_user extends Auser{
    String rank;


    public R_user(String name)throws Exception {
        super(name);
        rank = db.getEntryData("Ranks",name,"name").get("rank");
    }



    public R_user(String name, String password, String status, String email, String organization,String admin,String rank) throws Exception{
        super(name, password, status, email, organization,admin);
        this.rank = rank;
        addtoDB();
    }

    @Override
    public void addtoDB() throws Exception {
        super.addtoDB();
        String[] entry = {userInfo.get("name"),rank};
        db.insert("Ranks",entry);
    }

    public boolean isMemberOfEpicenter(){
        if(userInfo.get("organization").equals("epicenter"))
            return true;
        return false;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("rank = "+rank);
    }
}
