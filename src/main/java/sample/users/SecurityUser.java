package sample.users;
import sample.actions.Event;
import sample.organizations.Organization;

import java.util.ArrayList;
import java.util.List;

public class SecurityUser extends BasicUser {

    private List<Event> events;

    public SecurityUser(){
        super();
        events = new ArrayList<>();
    }

    public SecurityUser(String id, String password, String mail, Organization org, int degree){
        super(id, password, mail, org, degree);
    }

    public void addEvent(Event e){
        events.add(e);
    }
}
