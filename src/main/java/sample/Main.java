package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.actions.Complaint;
import sample.actions.Event;
import sample.actions.Update;
import sample.organizations.*;
import sample.users.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/login.fxml").openStream());
        primaryStage.setTitle("EmerAgency");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {

        //get organizations
        Police police = Police.getInstance();
        MADA mada = MADA.getInstance();
        FireDepartment fd = FireDepartment.getInstance();
        ServiceCenter sc = ServiceCenter.getInstance();

        //set service center
        sc.setCategories(DBHandler.getCategories());
        HashMap<String,Organization> orgs = new HashMap<>();
        orgs.put("Police",Police.getInstance());
        orgs.put("MADA",MADA.getInstance());
        orgs.put("FireDepartment",FireDepartment.getInstance());
        sc.setSecurityOrgs(orgs);

        //set admins
        sc.setAdmin(DBHandler.getAdmin(sc));
        police.setAdmin(DBHandler.getAdmin(police));
        mada.setAdmin(DBHandler.getAdmin(mada));
        fd.getInstance().setAdmin(DBHandler.getAdmin(fd));

        //setUsers
        police.setUsers(DBHandler.getUsersByOrg(police));
        mada.setUsers(DBHandler.getUsersByOrg(mada));
        fd.setUsers(DBHandler.getUsersByOrg(fd));
        sc.setUsers(DBHandler.getUsersByOrg(sc));

        //add event example
        ServiceUser bu = (ServiceUser)(sc.getUsers().get(0));
        List<String> categories = sc.getCategories();
        SecurityUser su = (SecurityUser)(fd.getUsers().get(0));
        List<BasicUser> users = new ArrayList<>();
        users.add(su);
        bu.addEvent("hello",users,"world",categories);

        //update even example
        Event event = DBHandler.getEvent("hello");
        Update update = new Update("hello2");
        event.update(update);

        //add complaint example
        SecurityUser su2 = (SecurityUser)(fd.getUsers().get(1));
        su.complain(new Complaint(su,su2,"!!!!"));
        su.complain(new Complaint(su,su2,"!!!!"));
        su.complain(new Complaint(su,su2,"!!!!"));

        launch(args);
    }


}
