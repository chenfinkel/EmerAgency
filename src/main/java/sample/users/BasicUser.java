package sample.users;

import sample.actions.Complaint;
import sample.organizations.Organization;

import java.util.ArrayList;
import java.util.List;

public class BasicUser extends User {
    private int degree;
    private int warnings;
    private userStatus status;
    private List<Complaint> complaints;

    public BasicUser(){
        super();
    }

    public BasicUser(String id, String password, String mail, Organization org, int degree){
        super(id, password, mail, org);
        this.degree = degree;
        complaints = new ArrayList<>();
    }

    enum userStatus{ Active, NotActive, Locked }
}


