package Objects.Organization;

import Objects.users.Auser;
import java.util.LinkedList;
import java.util.Map;

public class Epicenter extends Aorganization {
    public Epicenter(String name) throws Exception {
        super(name);
    }


    public void createEvent(String userName, String[] categories, Auser[] users)throws Exception{
        //checks that user is a Member of Epicenter
        if(!isMember(userName)){
            throw new Exception("user" + userName + "is not a member of Epicenter\n ONLY MEMBERS OF EPICENTER CAN CREATE EVENTS!");
        }


        if(categories == null || categories.length<1 ){
            throw new Exception("must give at least one category");
        }

        //checks that categories exist
        for(String cat : categories){
            db.getEntryData("Categories", cat,"category");
        }

        String newID = (db.getAllFromTable("Events").size() + 1)+"";




    }
}
