package sample.organizations;

import sample.users.BasicUser;
import sample.users.SecurityUser;
import sample.users.ServiceUser;

public class FireDepartment extends Organization {

    private static FireDepartment fireDepartment;
    private String name = "FireDepartment";

    private FireDepartment(){
        super();
    }

    public static FireDepartment getInstance(){
        if (fireDepartment == null) {
            fireDepartment = new FireDepartment();
        }
        return fireDepartment;
    }

    public BasicUser createUser(String id, String password, String mail, Organization org, int degree){
        return new SecurityUser(id, password, mail, this, degree);
    }

    public String getName() {
        return name;
    }
}
