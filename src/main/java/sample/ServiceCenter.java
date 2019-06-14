package sample;

import java.util.ArrayList;
import java.util.List;

public class ServiceCenter extends Organization{

    private static ServiceCenter serviceCenter;

    private List<Organization> securityOrgs;
    private List<String> categories;
    private List<ServiceUser> serviceUsers;

    private ServiceCenter(){

        categories = new ArrayList<>();
        serviceUsers = new ArrayList<>();
        securityOrgs = new ArrayList<>();

        securityOrgs.add(Police.getInstance());
        securityOrgs.add(MADA.getInstance());
        securityOrgs.add(FireDepartment.getInstance());

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


}
