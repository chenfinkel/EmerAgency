package sample.organizations;

import sample.actions.Event;
import sample.users.Admin;
import sample.users.BasicUser;
import sample.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class Organization extends Observable {

    private Admin admin;
    private List<BasicUser> users;
    private List<Event> events;

    public Organization(){
        users = new ArrayList<>();
        events = new ArrayList<>();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void addEvent(Event e){
        events.add(e);
        notifyObservers(e);
    }

    public void addUser(BasicUser u){
        addObserver(u);
        users.add(u);
    }

    public void setUsers(List<BasicUser> users){
        this.users.addAll(users);
    }

    public List<BasicUser> getUsers() {
        return users;
    }

    public abstract BasicUser createUser(String id, String password, String mail, Organization org, int degree);
}
