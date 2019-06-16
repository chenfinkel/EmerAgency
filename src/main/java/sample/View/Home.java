package sample.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    public Button create_event;
    public Button Create_Complaint;
    public Button Update_Event;
    public Button log_out;



    public void logOut(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("you logged out");
        alert.setHeaderText("Look, we are going to say bye bye");
        alert.setContentText("bye bye!");

        alert.showAndWait();

        Parent a = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();

    }

    public void createEvent(ActionEvent actionEvent) throws IOException {
        Parent a = FXMLLoader.load(getClass().getResource("/createEvent.fxml"));
        try {
            a = FXMLLoader.load(getClass().getResource("/createEvent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();
    }

    public void CreateComplaint(ActionEvent actionEvent) throws IOException {
        Parent a = FXMLLoader.load(getClass().getResource("/createComplaint.fxml"));
        try {
            a = FXMLLoader.load(getClass().getResource("/createComplaint.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();

    }

    public void UpdateEvent(ActionEvent actionEvent) throws IOException {
        Parent a = FXMLLoader.load(getClass().getResource("/updateEvent.fxml"));
        try {
            a = FXMLLoader.load(getClass().getResource("/updateEvent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();
    }
}
