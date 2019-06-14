package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Organization extends Observable {

    private Admin admin;
    private List<User> users;
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

    public void addUser(User u){
        addObserver(u);
        users.add(u);
    }

    public void setUsers(List<User> users){
        this.users.addAll(users);
    }
}
