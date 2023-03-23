import model.Participant;
import model.persistence.Connection;
import model.persistence.ParticipantPersistence;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CrudTest {
    private ParticipantPersistence participantPersistence;
    @Mock
    private Session mockSession;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private Query<Participant> query;
    @Before
    public void setUp() {
        Mockito.when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        Connection.setSessionFactory(mock(SessionFactory.class));
        Mockito.when(Connection.getSessionFactory().openSession()).thenReturn(mockSession);
        participantPersistence = new ParticipantPersistence();
    }

    @Test
    public void testInsertUserShouldSaveUserAndCommitOneTime() {
       Participant participantTest = new Participant();
       participantTest.setParticipantId(UUID.randomUUID().toString());
       participantTest.setName("John");
       participantTest.setRegistered(true);

       participantPersistence.insert(participantTest);

       Mockito.verify(mockSession, times(1)).save(participantTest);
       Mockito.verify(mockTransaction, times(1)).commit();
    }

    @Test
    public void testUpdateUserShouldUpdateUserAndCommitOneTime() {
        Participant participantTest = new Participant();
        participantTest.setParticipantId(UUID.randomUUID().toString());
        participantTest.setName("John");
        participantTest.setRegistered(true);

        participantPersistence.update(participantTest);

        Mockito.verify(mockSession, times(1)).update(participantTest);
        Mockito.verify(mockTransaction, times(1)).commit();
    }

    @Test
    public void testDeleteUserShouldWorkWithRightArgument() {
        Participant participantTest = new Participant();
        participantTest.setParticipantId(UUID.randomUUID().toString());
        Class<Participant> classType = Participant.class;
        String id = participantTest.getParticipantId();
        Mockito.when(mockSession.get(classType, id)).thenReturn(participantTest);

        participantPersistence.delete(participantTest.getParticipantId());

        Mockito.verify(mockSession).delete(participantTest);
    }

    @Test
    public void testReadAllShouldReturnNotNullListOfParticipants() {
        List<Participant> participants = participantPersistence.readAll();

        boolean expectedResult = true;

        Assert.assertNotNull(participants);
        Assert.assertEquals(participants.size()>0, expectedResult);
    }
}