package sample.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Controller;

import java.io.IOException;
import java.util.ArrayList;

public class CreateEvent {

    @FXML
    public Button back;
    public TextField name;
    public TextField description;
    public ComboBox manager;

    public CheckBox c11;
    public CheckBox c21;
    public CheckBox c31;

    public ArrayList<String> categories;
    public ArrayList<String> organizations;
    public Controller controller;

    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent a = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();
    }

    public void showPersons(ActionEvent actionEvent)
    {
        categories = new ArrayList<>();
        if(c11.isSelected())
            categories.add("MADA");
        if(c11.isSelected())
            categories.add("police");
        if(c11.isSelected())
            categories.add("fire department");

    }

    public void save(ActionEvent actionEvent) {
        controller.saveEvent(name.getText(),description.getText(),categories,manager.getSelectionModel().getSelectedItem().toString());
    }

}