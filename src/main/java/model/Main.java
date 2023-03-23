package model;

import lombok.extern.java.Log;
import model.persistence.*;
import view.AdminView;
import view.LoginView;
import view.OrganizerView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
//            LoginView loginView = new LoginView();
/*        User user = new User(UUID.randomUUID().toString(), "Razvan", "Bumbu", 19, "florean@gmail.com",
                "r", "r", "participant", true);
        (new UserPersistence()).insert(user);*/
        //User user =(new UserPersistence()).readAll().get(0);
/*        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        LocalDate ld = LocalDate.parse ( "12/03/2024" , f );
        Schedule schedule = new Schedule(UUID.randomUUID().toString(), ld, 9, 11);
            Section section = new Section(UUID.randomUUID().toString(), "Physics", null, schedule);
        (new SectionPersistence()).insert(section);*/
       // ParticipantView participantView = new ParticipantView(null);
/*        Participant participant  = new Participant();
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setUser(user);
        participant.setRegistered(true);
        participant.setName("Razvan");*/
      // LoginView loginView = new LoginView();
//        Connection connection = new Connection();
//        connection.startConnection();
//        connection.stopConnection();
/*        SectionPersistence sectionPersistence = new SectionPersistence();
        sectionPersistence.readAll();
        sectionPersistence.readAll();*/
       //OrganizerView organizerView = new OrganizerView();
        /*AdminView adminView = new AdminView();*/
        LoginView loginView = new LoginView();
    }
}
