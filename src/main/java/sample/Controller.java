package sample;

import sample.users.BasicUser;
import sample.users.User;

public class Controller {

    public static BasicUser login(String input_username) {
        return DBHandler.login(input_username);
    }
}
