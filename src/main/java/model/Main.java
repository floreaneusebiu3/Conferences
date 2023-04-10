package model;

import model.persistence.SchedulePersistence;
import model.persistence.SectionPersistence;
import model.persistence.UserPersistence;
import view.LoginView;
import view.OrganizerView;
import view.ParticipantView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


public class Main {
    public static void main(String[] args) throws IOException {
/*        List<Section> sectionList = (new SectionPersistence()).readAll();
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        LocalDate ld = LocalDate.parse ( "01/10/2025" , f );
        Schedule schedule = new Schedule(UUID.randomUUID().toString(), ld, 19, 21, sectionList.get(1));
        (new SchedulePersistence()).insert(schedule);*/
/*        User user = (new UserPersistence()).readAll().get(0);
        ParticipantView participantView = new ParticipantView(user);*/
        OrganizerView organizerView = new OrganizerView();

       // LoginView loginView = new LoginView();
         //User user = (new UserPersistence()).readAll().get(0);
        //ParticipantView participantView = new ParticipantView(user);
        //OrganizerView organizerView = new OrganizerView();
    }
}
