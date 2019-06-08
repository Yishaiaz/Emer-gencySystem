package Objects.users;

import java.util.Map;

public class A_user extends Auser {


    public A_user(String name) {
        super(name);
    }

    public A_user(String name, String password, String status, String email, String organization) {
        super(name, password, status, email, organization);
    }
}
