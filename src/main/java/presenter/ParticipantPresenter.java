package presenter;

import model.*;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import model.persistence.SectionPersistence;
import view.ParticipantView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ParticipantPresenter {
    private ParticipantView participantView;
    private String selectedPresentationFile = null;
    private SectionPersistence sectionPersistence;
    private ParticipantPersistence participantPersistence;
    private SectionParticipantPersistence sectionParticipantPersistence;
    private PresentationFilePersistence presentationFilePersistence;

    public ParticipantPresenter(ParticipantView participantView) {
        presentationFilePersistence = new PresentationFilePersistence();
        sectionParticipantPersistence = new SectionParticipantPersistence();
        participantPersistence = new ParticipantPersistence();
        sectionPersistence = new SectionPersistence();
        this.participantView = participantView;
    }

    public List<Section> getAllSectionsOfThisUser(User user) {
        List<SectionParticipant> sectionParticipants = sectionParticipantPersistence.readAll();
        List<Section> sections = new ArrayList<>();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getName().equals(user.getFirstName())) {
                System.out.println("************************************************************************");
                System.out.println(sectionParticipant.getParticipant().getName());
                System.out.println("************************************************************************");
                System.out.println(user.getFirstName());
                sections.add(sectionParticipant.getSection());
            }
        }
        return sections;
    }

    private boolean isParticipantAtThisSection(User user, Set<Participant> participants) {
        for (Participant participant : participants) {
            if (user.getFirstName().equals(participant.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addParticipantToSection(User user, String sectionId) {
        if (selectedPresentationFile == null) {
            participantView.showMessageMustSelectAFile();
            return;
        }

        Section section = sectionPersistence.getSectionById(sectionId);
        if (userIsParticipantAtThisSection(user, section)) {
            participantView.showMessageAlreadyJoinedThisSection();
            return;
        }
        Participant participant = getParticipant(user);
        participantPersistence.insert(participant);
        PresentationFile presentationFile1 = getPresentationFile(section, selectedPresentationFile, participant);
        presentationFilePersistence.insert(presentationFile1);
        addSectionParticipantsRow(participant, section);
        participantView.showAllSectionsOfThisParticipant();

    }

    private boolean userIsParticipantAtThisSection(User user, Section section) {
        List<SectionParticipant> sectionParticipants = sectionParticipantPersistence.readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getSection().getSectionId().equals(section.getSectionId()) && sectionParticipant.getParticipant().getName().equals(user.getFirstName()))
                return true;
        }
        return false;
    }

    private void addSectionParticipantsRow(Participant participant, Section section) {
        SectionParticipant sectionParticipant = getSectionParticipant(participant, section);
        sectionParticipantPersistence.insert(sectionParticipant);
    }

    public void getSectionsFromDataBaseAndUpdateTable() {
       List<Section> sectionList = sectionPersistence.readAll();
       participantView.updateSectionsTable(sectionList);
    }

    public SectionParticipant getSectionParticipant(Participant participant, Section section) {
        return new SectionParticipant(UUID.randomUUID().toString(), participant, section);
    }

    private Participant getParticipant(User user) {
        Participant participant = new Participant();
        participant.setName(user.getFirstName());
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setRegistered(true);
        participant.setUser(user);
        if (user.getUserId() == null) {
            participant.setRegistered(false);
            participant.setUser(null);
        }
        return participant;
    }

    public PresentationFile getPresentationFile(Section section, String pathToFile, Participant participant) {
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress(pathToFile);
        presentationFile.setParticipant(participant);
        presentationFile.setSection(section);
        return presentationFile;
    }

    public void setSelectedPresentationFile(String selectedPresentationFile) {
        this.selectedPresentationFile = selectedPresentationFile;
    }

    public List<Section> getSectionsFromDataBase() {
        return sectionPersistence.readAll();
    }

    public void updateFilesTable() {
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        participantView.showAllFiles(presentationFiles);
    }

    public void openFile() {
        List<PresentationFile> files= presentationFilePersistence.readAll();
        int selectedFile = participantView.getFilesTable().getSelectedRow();
        if(selectedFile >= files.size() || selectedFile < 0) {
            participantView.showMessageMustSelectAFileToBeOpened();
        }
        PresentationFile presentationFile = files.get(selectedFile);
        File file = new File(presentationFile.getFileAddress());
        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
