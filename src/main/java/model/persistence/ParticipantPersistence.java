package model.persistence;

import model.Participant;
import model.PresentationFile;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class ParticipantPersistence extends AbstractPersistence<Participant>{
    public User getUserForThisParticipant(Participant participant) {
        User user;
        user = participant.getUser();
        return user;
    }

    public List<PresentationFile> getPresentationsFileForThisParticipant(Participant participant) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        Participant participantFromDataBase = session.get(Participant.class, participant.getParticipantId());
        Set<PresentationFile> presentationFiles = participantFromDataBase.getPresentationFiles();
        List<PresentationFile> presentationFilesAsList = new ArrayList<>(presentationFiles);
        t.commit();
        factory.close();
        session.close();
        return presentationFilesAsList;
    }
}
