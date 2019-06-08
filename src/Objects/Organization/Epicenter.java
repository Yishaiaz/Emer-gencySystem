package Objects.Organization;

import Objects.users.Auser;
import java.util.LinkedList;
import java.util.Map;

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



}
