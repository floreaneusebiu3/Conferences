package presenter;

import model.Participant;
import model.PresentationFile;
import model.Section;
import model.SectionParticipant;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import model.persistence.SectionPersistence;
import view.IOrganizerView;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        organizerView.updateParticipantsFilesTable(organizerView.getParticipantsFilesData(), participantList, presentationFiles);
    }

    public void updateFilteredTable() {
        List<Participant> participants = getParticipantsForThisSection(organizerView.getSectionTextField().getText());
        organizerView.clearFilteredTable();
        organizerView.updateFilteredParticipantsTable(organizerView.getFilteredParticipantsData(), participants);
    }

    public void updateParticipantAndFile() {
        int index = organizerView.getParticipantsFilesTable().getSelectedRow();
        List<Participant> participants = participantPersistence.readAll();
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        if (participants.size() == 0 || presentationFiles.size() == 0 | !checkCorrectIndex(index)) {
            organizerView.showMessage("You haven't chosen a correct row");
            return;
        }
        Participant updatedParticipant = getParticipantByIndex(index, participants);
        if (updatedParticipant != null) {
            for (Participant participant : participants) {
                if (participant.getParticipantId().equals(updatedParticipant.getParticipantId())) {
                    participant.setName((String) organizerView.getParticipantsFilesData()[index][0]);
                    participantPersistence.update(updatedParticipant);
                }
            }
        }
        PresentationFile updatedFile = getPresentationFileByIndex(index, participants);
        if (updatedFile != null) {
            updatedFile.setFileAddress((String) organizerView.getParticipantsFilesData()[index][2]);
            presentationFilePersistence.update(updatedFile);
        }
        organizerView.clearParticipantsFilesTable();
    }

    public void insertParticipantAndFile() {
        int index = organizerView.getParticipantsFilesTable().getSelectedRow();
        Section section = getSection((String) organizerView.getParticipantsFilesData()[index][3]);
        if (section == null) {
            organizerView.showMessage("You must select an existing section");
            return;
        }
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress((String) organizerView.getParticipantsFilesData()[index][2]);
        presentationFile.setSection(section);
        Participant participant = new Participant();
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setRegistered(organizerView.getParticipantsFilesData()[index][1].equals("true"));
        participant.setName((String) organizerView.getParticipantsFilesData()[index][0]);
        presentationFile.setParticipant(participant);
        participantPersistence.insert(participant);
        presentationFilePersistence.insert(presentationFile);
        SectionParticipant sectionParticipant = new SectionParticipant();
        sectionParticipant.setSectionParticipantId(UUID.randomUUID().toString());
        sectionParticipant.setParticipant(participant);
        sectionParticipant.setSection(section);
        sectionParticipantPersistence.insert(sectionParticipant);
        organizerView.clearParticipantsFilesTable();
    }

    public void deleteParticipantAndFile() {
        int index = organizerView.getParticipantsFilesTable().getSelectedRow();
        List<Participant> participants = participantPersistence.readAll();
        if (!checkCorrectIndex(index)) {
            organizerView.showMessage("You haven't chosen a correct row");
            return;
        }
        Participant participantToBeDeleted = getParticipantByIndex(index, participants);
        if (participantToBeDeleted != null) {
            deleteAllSectionParticipantsOfThisParticipant(participantToBeDeleted);
            deleteAllPresentationsFilesOfThisParticipant(participantToBeDeleted);
            participantPersistence.delete(participantToBeDeleted.getParticipantId());
        }
        PresentationFile presentationFile = getPresentationFileByIndex(index, participants);
        if (presentationFile != null) {
            presentationFilePersistence.delete(presentationFile.getPresentationFileId());
        }
        organizerView.clearParticipantsFilesTable();
    }

    private List<Participant> getParticipantsForThisSection(String section) {
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        List<Participant> participants = new ArrayList<>();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getSection().getName().equals(section)) {
                participants.add(presentationFile.getParticipant());
            }
        }
        return participants;
    }

    private boolean checkCorrectIndex(int index) {
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        List<Participant> participants = participantPersistence.readAll();
        if (index < 0 | index > presentationFiles.size() + participants.size()) {
            organizerView.showMessage("You must select a participant or file");
            return false;
        }
        return true;
    }

    private Section getSection(String name) {
        List<Section> sectionList = (new SectionPersistence()).readAll();
        for (Section section : sectionList) {
            if (section.getName().equals(name)) {
                return section;
            }
        }
        return null;
    }

    private Participant getParticipantByIndex(int givenIndex, List<Participant> participants) {
        int k = 0;
        for (Participant participant : participants) {
            if (givenIndex == k) {
                return participant;
            } else {
                k++;
                for (PresentationFile presentationFile : participantPersistence.getPresentationsFileForThisParticipant(participant)) {
                    if (k == givenIndex)
                        return null;
                    else
                        k++;
                }
            }
        }
        return null;
    }

    private PresentationFile getPresentationFileByIndex(int givenIndex, List<Participant> participants) {
        int k = 0;
        for (Participant participant : participants) {
            if (givenIndex == k) {
                return null;
            } else {
                k++;
                for (PresentationFile presentationFile : participantPersistence.getPresentationsFileForThisParticipant(participant)) {
                    if (k == givenIndex)
                        return presentationFile;
                    else
                        k++;
                }
            }
        }
        return null;
    }

    private void deleteAllSectionParticipantsOfThisParticipant(Participant participant) {
        List<SectionParticipant> sectionParticipants = sectionParticipantPersistence.readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                sectionParticipantPersistence.delete(sectionParticipant.getSectionParticipantId());
            }
        }
    }

    private void deleteAllPresentationsFilesOfThisParticipant(Participant participant) {
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                presentationFilePersistence.delete(presentationFile.getPresentationFileId());
            }
        }
    }
}
