package sample.View;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.actions.Event;
import sample.actions.Update;
import sample.organizations.FireDepartment;
import sample.organizations.MADA;
import sample.organizations.Police;
import sample.users.BasicUser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateEvent {

    @FXML
    public Button back;
    public TextField name;
    public TextField description;
    public TextField personOnDuty;
    public Label unCorrect;

    public CheckBox crobbery;
    public CheckBox cmurder;
    public CheckBox cfire;
    public CheckBox ccaraccident;
    public CheckBox cterror;

    public CheckBox c11;
    public CheckBox c21;
    public CheckBox c31;

    public ArrayList<String> categories;
    public ArrayList<String> organizations;

    public CreateEvent() {
        this.back = new Button();
        this.name = new TextField();
        this.description = new TextField();
        this.personOnDuty = new TextField();
        this.crobbery = new CheckBox();
        this.cmurder = new CheckBox();
        this.cfire = new CheckBox();
        this.ccaraccident = new CheckBox();
        this.cterror = new CheckBox();
        this.c11 = new CheckBox();
        this.c21 = new CheckBox();
        this.c31 = new CheckBox();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent a = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();
    }

    public void save(ActionEvent actionEvent) {
        unCorrect.setVisible(false);
        unCorrect.setText("Error: The person of duty doesn't exist.");
        BasicUser person = Controller.getInstance().login(personOnDuty.getText());
        if(person != null){
            categories = new ArrayList<>();
            if(crobbery.isSelected())
                categories.add("Robbery");
            if(cmurder.isSelected())
                categories.add("Murder");
            if(cfire.isSelected())
                categories.add("Fire");
            if(ccaraccident.isSelected())
                categories.add("CarAccident");
            if(cterror.isSelected())
                categories.add("Terror");
            if(categories.size() == 0){
                unCorrect.setText("Error: must select at least 1 category.");
            }
            else{
                boolean valid_duty = (person.getOrg() instanceof MADA && c11.isSelected())
                        || (person.getOrg() instanceof Police && c21.isSelected())
                        || (person.getOrg() instanceof FireDepartment && c31.isSelected());

                if(!valid_duty){
                    unCorrect.setText("Error: person on duty's organization must be checked.");
                }
                else{
                    //prepare input for controller
                    String nameInput = name.getText();
                    ArrayList<BasicUser> dutyInput = new ArrayList<>();
                    dutyInput.add(person);
                    Update start = new Update();
                    start.setDate(LocalDateTime.now());
                    start.setCurrentDescription(description.getText());
                    start.setOriginalDescription(description.getText());
                    Event event = new Event(nameInput,dutyInput,start,categories);
                    start.setEvent(event);
                    Controller.getInstance().saveEvent(event);
                    unCorrect.setText("successfully registered event!");
                }
            }
        }
        unCorrect.setVisible(true);
    }
}