package Objects.Organization;

import Objects.users.Auser;

import java.util.LinkedList;
import java.util.Map;

public class Epicenter extends Aorganization {
    public Epicenter(String name) {
        super(name);
    }

    public void addCategory(String categoryName, String userName)throws Exception{
        if(categoryName == null || categoryName.length() == 0 || userName==null ){
            throw new Exception("error: bad input: Epicenter:addCategory");
        }
        if(!isMember(userName)){
            throw new Exception("user" + userName + "is not a member of Epicenter");
        }
        String[] entry = {categoryName};
        db.insert("Categories",entry);


    }

    public void createEvent(String userName, String[] categories, Auser[] users)throws Exception{
        if(!isMember(userName)){
            throw new Exception("user" + userName + "is not a member of Epicenter\n ONLY MEMBERS OF EPICENTER CAN CREATE EVENTS!");
        }


        if(categories == null || categories.length<1 ){
            throw new Exception("must give at least one category");
        }


        for(String cat : categories){ //makes sure that all categories exist
            db.getEntryData("Categories", cat);
        }

        db.in


    }
}
