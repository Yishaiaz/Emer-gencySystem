package Objects.Organization;

import java.util.Locale;

public class Epicenter extends Aorganization { //single tone
    private static Epicenter instance =null;

    private Epicenter(String name) throws Exception {
        super(name);
    }
    public static Epicenter getInstance(String name) throws Exception {
        if(instance!=null){
            return instance;
        }
        instance = new Epicenter("epicenter");
        return instance;
    }




    public void addCategory(String categoryName, String userName)throws Exception{
        if(categoryName == null || categoryName.length() == 0 || userName==null ){
            throw new Exception("error: bad input: Epicenter:addCategory");
        }
        if(userName != getAdmnin()){
            throw new Exception("error: only the admin of the Epicenter can add a category: Epicenter:addCategory");
        }

        String[] entry = {categoryName};
        db.insert("Categories",entry);

    }

    public void createEvent( String userId, String[] categories, String[] emergencyForces,String title,String status,String details)throws Exception{
        if(userId==null || !isMember(userId))
            throw new Exception("ERROR: user is not an epicenter member!");
        if(categories==null || categories.length < 1)
            throw new Exception("ERROR: an event must have at least one category");
        if(emergencyForces==null || emergencyForces.length < 1)
            throw new Exception("ERROR: an event must have at least one category");

    }
    



}
