package sample;

import java.util.ArrayList;
import java.util.List;

public class ServiceUser extends BasicUser {

    private ServiceCenter serviceCenter;
    private List<Event> createdEvents;

    public ServiceUser(){
        serviceCenter = ServiceCenter.getInstance();
        createdEvents = new ArrayList<>();
    }
}
