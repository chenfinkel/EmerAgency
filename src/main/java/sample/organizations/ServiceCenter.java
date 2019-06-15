package sample.organizations;

import sample.users.BasicUser;
import sample.users.ServiceUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceCenter extends Organization {

    private static ServiceCenter serviceCenter;

    private HashMap<String,Organization> securityOrgs;
    private List<String> categories;

    private ServiceCenter(){

        categories = new ArrayList<>();
        securityOrgs = new HashMap<>();

    }

    public static ServiceCenter getInstance(){
        if (serviceCenter == null) {
            serviceCenter = new ServiceCenter();
        }
        return serviceCenter;
    }

    public void setCategories(List<String> categories){
        this.categories.addAll(categories);
    }

    public void setSecurityOrgs(HashMap<String,Organization> orgs){
        securityOrgs.putAll(orgs);
    }

    public List<String> getCategories() {
        return categories;
    }

    public BasicUser createUser(String id, String password, String mail, Organization org, int degree){
        return new ServiceUser(id, password, mail, this, degree);
    }

    public HashMap<String, Organization> getSecurityOrgs() {
        return securityOrgs;
    }
}
