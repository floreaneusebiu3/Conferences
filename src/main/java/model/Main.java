package model;

import model.persistence.*;
import view.LoginView;
import view.ParticipantView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
//            LoginView loginView = new LoginView();
//        User user = new User(UUID.randomUUID().toString(), "Razvan", "Bumbu", 19, "bumbu@gmail.com",
//                "r", "r", "participant", true);
        User user =(new UserPersistence()).readAll().get(0);
/*        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        LocalDate ld = LocalDate.parse ( "10/05/2023" , f );
        Schedule schedule = new Schedule(UUID.randomUUID().toString(), ld, 12, 15);
        Section section = new Section(UUID.randomUUID().toString(), "History", null, schedule);
        (new SectionPersistence()).insert(section);*/
       // ParticipantView participantView = new ParticipantView(null);
        LoginView loginView = new LoginView();
//        Connection connection = new Connection();
//        connection.startConnection();
//        connection.stopConnection();
/*        SectionPersistence sectionPersistence = new SectionPersistence();
        sectionPersistence.readAll();
        sectionPersistence.readAll();*/
    }
}
