package sample.organizations;

import sample.users.BasicUser;
import sample.users.SecurityUser;
import sample.users.ServiceUser;

public class MADA extends Organization {

    private static MADA mada;
    private String name = "MADA";

    private MADA(){
        super();
    }

    public static MADA getInstance(){
        if (mada == null) {
            mada = new MADA();
        }
        return mada;
    }

    public BasicUser createUser(String id, String password, String mail, Organization org, int degree){
        return new SecurityUser(id, password, mail, this, degree);
    }

    public String getName() {
        return name;
    }
}
