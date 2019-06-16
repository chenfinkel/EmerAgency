package sample.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Controller;
import sample.DBHandler;
import sample.actions.Complaint;
import sample.users.BasicUser;

import java.io.IOException;

public class CreateComplaint {
    public Button back;
    public TextField complainedOn;
    public TextArea description;
    public Label unCorrect;


    public CreateComplaint() {
        this.back = new Button();
        this.complainedOn = new TextField();
        this.description = new TextArea();
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
        unCorrect.setText("Error: This user isn't in the database.");
        BasicUser reported = Controller.getInstance().currentUser;
        BasicUser complainedOnUser = Controller.getInstance().login(complainedOn.getText());
        if(complainedOnUser != null){
            String descriptionText = description.getText();
            Complaint complaint = new Complaint(reported,complainedOnUser,descriptionText);
            Controller.getInstance().saveComplaint(complaint);
            unCorrect.setText("Complaint registered successfully!");
        }
        unCorrect.setVisible(true);
    }
}
