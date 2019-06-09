package Objects.users;

import java.util.Map;

public class A_user extends Auser {


    public A_user(String name) throws Exception{
        super(name);
    }

    public A_user(String name, String password, String status, String email, String organization,String admin)throws Exception {
        super(name, password, status, email, organization,admin);
        addtoDB();
    }




}
