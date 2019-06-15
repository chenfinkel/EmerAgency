package sample.users;

import sample.organizations.Organization;

import java.util.Observable;
import java.util.Observer;

public abstract class User implements Observer {
    private String username;
    private String password;
    private String mail;
    private Organization org;

    public User(){}

    public User(String username, String password, String mail, Organization org){
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.org = org;
    }

    public Organization getOrg() {
        return org;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public void update(Observable o, Object arg) {
        //function to DB to add read permission to arg which is the event
    }



}
