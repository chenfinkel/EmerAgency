package sample.users;

import sample.organizations.FireDepartment;
import sample.organizations.MADA;
import sample.organizations.Organization;
import sample.organizations.Police;

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

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setOrg(String org){
        if(org.equalsIgnoreCase("police")){
            this.org = Police.getInstance();
        }
        if(org.equalsIgnoreCase("MADA")){
            this.org = MADA.getInstance();
        }
        if(org.replaceAll(" ","").equalsIgnoreCase("FireDepartment")){
            this.org = FireDepartment.getInstance();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //function to DB to add read permission to arg which is the event
    }



}
