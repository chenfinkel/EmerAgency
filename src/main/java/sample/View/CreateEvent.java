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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEvent {

    @FXML
    public Button back;
    public TextField name;
    public TextField description;
    public TextField personOnDuty;
    public Label unCorrect;

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
        unCorrect.setText("Error: please input 'MADA','police' or 'fire department'");
        String dutyInput = personOnDuty.getText();
        boolean valid_duty = (dutyInput.equals("MADA") ||
                              dutyInput.equals("police") ||
                              dutyInput.equals("fire department"));
        if(valid_duty){
            categories = new ArrayList<>();
            if(c11.isSelected())
                categories.add("MADA");
            if(c11.isSelected())
                categories.add("police");
            if(c11.isSelected())
                categories.add("fire department");

            Controller.getInstance().saveEvent(name.getText(),description.getText(),categories,personOnDuty.getText());
            unCorrect.setText("successfully registered event!");
        }
        unCorrect.setVisible(true);
    }
}