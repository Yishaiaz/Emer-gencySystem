package Objects.Organization;

import Objects.event.Event;
import Objects.users.A_user;
import Objects.users.Auser;
import Objects.users.R_user;

import java.util.*;

public class Epicenter extends Aorganization { //single tone
    private static Epicenter instance =null;

    private Epicenter(String name) throws Exception {
        super(name);
    }
    public static Epicenter getInstance() throws Exception {
        if(instance!=null){
            return instance;
        }
        instance = new Epicenter("epicenter");
        return instance;
    }




    public void addCategory(String categoryName)throws Exception{
        if(categoryName == null || categoryName.length() == 0){
            throw new Exception("error: bad input: Epicenter:addCategory");
        }

        String[] entry = {categoryName};
        db.insert("Categories",entry);

    }

    public void createEvent( String userId, String[] categories, String[] emergencyForces,String title,String details)throws Exception{ //to do add argument checks!

        validaUser(userId);
        validateCategories(categories);
        validateemergencyForces(emergencyForces);
        Date date = new Date();
        R_user user = new R_user(userId);


        Event event = new Event(userId,categories, emergencyForces,title,"in progress",details,date.toString(),user.getRank());


    }

    private void validaUser(String userId)throws Exception{
        if(userId==null || !isMember(userId))
            throw new Exception("ERROR: user is not an epicenter member!");
        Auser user = getUser(userId);
        if(user instanceof A_user)
            throw new Exception("ERROR:only R_users can create an event!!");
    }

    private void validateemergencyForces(String[] eForces)throws Exception{
        if(eForces==null || eForces.length < 1)
        throw new Exception("ERROR: an event must have at least one category");
    }

    private void validateCategories(String[] categories)throws Exception{
        if(categories==null || categories.length < 1)
            throw new Exception("ERROR: an event must have at least one category");
        Set<String> s = getAllCategories();
        for(String cat : categories){
            if(!s.contains(cat))
                throw new Exception("ERROR: the category \""+cat+"\" does not exist ");
        }


    }

    public Set<String> getAllCategories()throws Exception{
        Set<String> s = new HashSet<>();
        LinkedList<Map<String, String>> list = db.getAllFromTable("Categories");
        for(Map<String,String> map : list){
            s.add(map.get("category"));
        }
        return s;

    }

    public Auser getUser(String name) throws Exception{
        Map<String,String>  userInfo = db.getEntryData("Users",name,"name");
        if(userInfo.get("admin").equals("true"))
            return new A_user(name);
        else return new R_user(name);
    }



}
