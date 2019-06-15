package sample.users;

import sample.organizations.Organization;

public class Admin extends User {

    public Admin(String username, String password, String mail, Organization org) {
        super(username, password, mail, org);
    }

}
