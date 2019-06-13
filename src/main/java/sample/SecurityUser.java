package sample;
import java.util.ArrayList;
import java.util.List;

public class SecurityUser extends BasicUser {

    private List<Event> events;

    public SecurityUser(){
        super();
        events = new ArrayList<>();
    }

    public void addEvent(Event e){
        events.add(e);
    }
}
