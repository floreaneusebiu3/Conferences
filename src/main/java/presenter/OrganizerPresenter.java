package presenter;

import model.Participant;
import model.PresentationFile;
import model.Section;
import model.SectionParticipant;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import view.IOrganizerView;
import view.OrganizerView;

import java.util.ArrayList;
import java.util.List;

public class OrganizerPresenter {
    private IOrganizerView organizerView;
    private ParticipantPersistence participantPersistence;
    private PresentationFilePersistence presentationFilePersistence;
    private SectionParticipantPersistence sectionParticipantPersistence;

    public OrganizerPresenter(IOrganizerView organizerView) {
        this.organizerView = organizerView;
        sectionParticipantPersistence = new SectionParticipantPersistence();
        participantPersistence = new ParticipantPersistence();
        presentationFilePersistence = new PresentationFilePersistence();
    }

    public void updateParticipantsFilesTable() {
        List<Participant> participantList = participantPersistence.readAll();
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        organizerView.updateParticipantsFilesTable(organizerView.getParticipantsFilesData(), participantList, presentationFiles );
    }

    public void updateFilteredTable(){
       List<Participant> participants = getParticipantsForThisSection(organizerView.getSectionTextField().getText());
       organizerView.updateFilteredParticipantsTable(organizerView.getFilteredParticipantsData(), participants);
    }

    private List<Participant> getParticipantsForThisSection(String section) {
        List<SectionParticipant> sectionParticipants = sectionParticipantPersistence.readAll();
        List<Participant> participants = new ArrayList<>();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getSection().getName().equals(section)) {
                participants.add(sectionParticipant.getParticipant());
            }
        }
        return participants;
    }
}
