package sample.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Controller;
import sample.actions.Event;
import sample.actions.Update;

import java.io.IOException;

public class UpdateEvent {

    public Button back;
    public Label unCorrect;
    public TextField eventName;
    public TextField status;
    public TextArea des_update;

    public UpdateEvent() {
        this.back = new Button();
        this.eventName = new TextField();
        this.status = new TextField();
        this.des_update = new TextArea();
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
        unCorrect.setText("Error: This event doesn't exist.");
        String eventTitle = eventName.getText();
        Event event = Controller.getInstance().getEvent(eventTitle);
        if(event != null){
            String statusInput = status.getText();
            if(!Event.isvalidStatus(statusInput)){
                unCorrect.setText("Error: status can only be inAction or done.");
            }
            else{
                String descUpdateInput = des_update.getText();
                Update update = new Update(event,descUpdateInput);
                Controller.getInstance().saveUpdate(update);
                unCorrect.setText("Event updated successfully!");
            }
        }
        unCorrect.setVisible(true);
    }
}
