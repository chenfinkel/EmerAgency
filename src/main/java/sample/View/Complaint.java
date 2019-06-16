package sample.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Complaint {
    public Button back;

    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent a = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene scene = new Scene(a);
        Stage s = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(scene);
        s.show();
    }
}
