package sample.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.users.BasicUser;
import sample.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    public Button button_LogIN;
    public TextField logIn_User;
    public PasswordField logIn_Password;
    public Label unCorrect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        unCorrect.setVisible(false);
        unCorrect.setText("user or password incorrect");
    }

    public void LogInClick(ActionEvent event) throws IOException {
        BasicUser queryResult = Controller.getInstance().login(logIn_User.getText());

        if(queryResult == null){
            unCorrect.setVisible(true);
        }
        else if(!queryResult.getPassword().equals(logIn_Password.getText())){
            unCorrect.setVisible(true);
        }
        else{
            Controller.getInstance().setCurrentUser(queryResult);
            Parent a = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            Scene scene = new Scene(a);
            Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
            s.setScene(scene);
            s.show();
        }
    }


}
