package sample;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

public class Event extends Observable {

    private String title;
    private LocalDateTime date;
    private Status status;
    private Update lastUpdate;
    private List<Organization> organizations;
    private List<String> categories;

    public Event(String title, SecurityUser securityUser, Update update, List<String> cats) {
        this.title = title;
        categories = cats;
        status = Status.inAction;
        lastUpdate = update;
        date = update.getDate();
        organizations = new ArrayList<>();
        addOrganization(securityUser);
    }

    public void update(Update newUpdate) {
        newUpdate.setPrevious(lastUpdate);
        lastUpdate = newUpdate;
    }

    public void addOrganization(SecurityUser securityUser) {
        addObserver(securityUser);
        organizations.add(securityUser.getOrg());

    }

    public Update getLastUpdate() {
        return lastUpdate;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getStatus() {
        return status.name();
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Organization> getOrganizations(){
        return organizations;
    }

    public String getTitle() {
        return title;
    }

    enum Status { inAction, Done};



}


