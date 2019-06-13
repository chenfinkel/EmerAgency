package sample;

import java.util.Observable;
import java.util.Observer;

public abstract class User implements Observer {
    private String id;
    private String password;
    private String mail;
    private Organization org;

    public User(){}

    public User(String id, String password, String mail, Organization org){
        this.id = id;
        this.password = password;
        this.mail = mail;
        this.org = org;
    }

    public Organization getOrg() {
        return org;
    }

    @Override
    public void update(Observable o, Object arg) {
        //function to DB to add read permission to arg which is the event
    }



}
