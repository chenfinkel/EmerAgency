package sample.organizations;

import sample.users.BasicUser;
import sample.users.SecurityUser;
import sample.users.ServiceUser;

public class Police extends Organization {

    private static Police police;
    private String name = "Police";

    private Police(){
        super();
    }

    public static Police getInstance(){
        if (police == null) {
            police = new Police();
        }
        return police;
    }

    public BasicUser createUser(String id, String password, String mail, Organization org, int degree){
        return new SecurityUser(id, password, mail, this, degree);
    }

    public String getName() {
        return name;
    }
}
