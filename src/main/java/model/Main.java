package model;

import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.UserPersistence;
import view.LoginView;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
            LoginView loginView = new LoginView();
            loginView.init();
//        User user = new User(UUID.randomUUID().toString(), "Razvan", "Bumbu", 19, "bumbu@gmail.com", "razvan", "razvan", "participant", true);
//        (new UserPersistence()).insert(user);
    }
}
