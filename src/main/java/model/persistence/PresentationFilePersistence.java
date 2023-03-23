package model.persistence;

import model.Participant;
import model.PresentationFile;
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

public class PresentationFilePersistence extends AbstractPersistence<PresentationFile>{

    public List<PresentationFile> getPresentationsFileForThisParticipant(Participant participant) {
        Transaction transaction = null;
        List<PresentationFile> presentationFilesAsList = null;
        try (Session session = Connection.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the user object

        Participant participantFromDataBase = session.get(Participant.class, participant.getParticipantId());
        Set<PresentationFile> presentationFiles = participantFromDataBase.getPresentationFiles();
        presentationFilesAsList = new ArrayList<>(presentationFiles);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return presentationFilesAsList;
    }

}

