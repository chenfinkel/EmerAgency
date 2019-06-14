package sample;

import java.util.ArrayList;
import java.util.List;

public class ServiceUser extends BasicUser {

    private ServiceCenter serviceCenter;
    private List<Event> createdEvents;
    private List<String> categories;

    public ServiceUser(){
        serviceCenter = ServiceCenter.getInstance();
        createdEvents = new ArrayList<>();
        categories = new ArrayList<>();

    }

    public void addEvent(String title, SecurityUser securityUser, String description, List<String> cats){
        Event event = new Event(title, securityUser, null, cats);
        Update update = new Update(event, description);
        event.update(update);
        DBHandler.addEvent(event);
    }
}
