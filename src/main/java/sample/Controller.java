package sample;

import sample.actions.Event;
import sample.actions.Update;
import sample.users.BasicUser;
import sample.users.User;

import java.util.ArrayList;

public class Controller {

    private static Controller instance;
    public User currentUser;

    private Controller(){
        currentUser = null;
    }

    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    public BasicUser login(String input_username) {
        return DBHandler.login(input_username);
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public void saveEvent(String name,
                          String description,
                          ArrayList<String> categories,
                          String personOnDuty) {
        //public Event(String title, List<BasicUser> security,
        // Update update, List<String> cats, String status) {

        BasicUser manager= new BasicUser();
        manager.setUsername(personOnDuty);
        ArrayList<BasicUser> security =new ArrayList<>();
        security.add(manager);
        Update start = new Update();
        Event e = new Event(name,security
                ,start,categories, description);
        start.setEvent(e);
        DBHandler.addEvent(e);
    }
}
