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
}
