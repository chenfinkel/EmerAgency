package sample.users;

import sample.DBHandler;
import sample.actions.Event;
import sample.organizations.Organization;
import sample.organizations.ServiceCenter;
import sample.actions.Update;

import java.util.ArrayList;
import java.util.List;

public class ServiceUser extends BasicUser {

    private List<Event> createdEvents;
    private List<String> categories;

    public ServiceUser(){
        createdEvents = new ArrayList<>();
        categories = new ArrayList<>();

    }

    public ServiceUser(String id, String password, String mail, Organization org, int degree){
        super(id, password, mail, org, degree);
    }

    public void addEvent(String title, List<BasicUser> security, String description, List<String> cats){
        Update update = new Update(description);
        Event event = new Event(title, security, update, cats);
        update.setEvent(event);
        DBHandler.addEvent(event);
    }

    public void setCategories(List<String> categories){
        this.categories = categories;
    }
}
