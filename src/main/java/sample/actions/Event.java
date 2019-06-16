package sample.actions;

import sample.DBHandler;
import sample.actions.Update;
import sample.organizations.Organization;
import sample.users.BasicUser;
import sample.users.SecurityUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event{

    private String title;
    private LocalDateTime date;
    private Status status;
    private Update lastUpdate;
    //private List<Organization> organizations;
    private List<SecurityUser> security;
    private List<String> categories;

    public Event(String title, List<BasicUser> security, Update update, List<String> cats) {
        this.title = title;
        categories = cats;
        status = Status.inAction;
        lastUpdate = update;
        date = update.getDate();
        //organizations = new ArrayList<>();
        this.security = new ArrayList<>();
        for (int i = 0; i < security.size(); i++)
            addOrganization((SecurityUser)security.get(i));
    }

    public Event(String title, List<BasicUser> security, Update update, List<String> cats, String status) {
        this.title = title;
        categories = cats;
        this.status = Status.valueOf(status);
        lastUpdate = update;
        date = update.getDate();
        //organizations = new ArrayList<>();
        this.security = new ArrayList<>();
        for (int i = 0; i < security.size(); i++)
            addOrganization((SecurityUser)security.get(i));
    }

    public void update(Update newUpdate) {
        newUpdate.setPrevious(lastUpdate);
        newUpdate.setEvent(this);
        lastUpdate = newUpdate;
        DBHandler.updateEvent(newUpdate);
    }

    public void addOrganization(SecurityUser securityUser) {
        security.add(securityUser);
        securityUser.getOrg().addEvent(this);
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
        List<Organization> organizations = new ArrayList<>();
        for (SecurityUser s : security){
            organizations.add(s.getOrg());
        }
        return organizations;
    }

    public String getTitle() {
        return title;
    }

    public List<SecurityUser> getSecurity() {
        return security;
    }

    enum Status { inAction, Done};



}


