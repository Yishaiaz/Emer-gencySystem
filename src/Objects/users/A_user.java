package Objects.users;

import java.util.Map;

public class A_user extends Auser {


    public A_user(String name) throws Exception{
        super(name);
    }

    public A_user(String name, String password, String status, String email, String organization) {
        super(name, password, status, email, organization);
    }

    public void addCategory(String categoryName, String userName)throws Exception{
        if(categoryName == null || categoryName.length() == 0 || userName==null ){
            throw new Exception("error: bad input: A_user:addCategory");
        }

        String[] entry = {categoryName};
        db.insert("Categories",entry);
        //testimg


    }
}
