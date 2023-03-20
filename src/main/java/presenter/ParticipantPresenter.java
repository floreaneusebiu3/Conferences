package presenter;

import model.*;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import model.persistence.SectionPersistence;
import view.IParticipantView;
import view.ParticipantView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ParticipantPresenter {
    private IParticipantView participantView;
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

    public void openFile() {
        List<PresentationFile> files = presentationFilePersistence.readAll();
        int selectedFile = participantView.getFilesTable().getSelectedRow();
        if (selectedFile >= files.size() || selectedFile < 0) {
            participantView.showMessage("You must select a file");
            return;
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

    public void createUser(User user) {
        if (user != null) {
            participantView.setLoggedUser(user);
        } else {
            participantView.setLoggedUser(new User());
            participantView.getLoggedUser().setFirstName("Guest" + UUID.randomUUID());
        }
    }

    public void updateFilesTable() {
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        int index = 0;
        for (PresentationFile presentationFile : presentationFiles) {
            participantView.getFilesData()[index][0] = presentationFile.getFileAddress();
            participantView.getFilesData()[index][1] = presentationFile.getParticipant().getName();
            participantView.getFilesData()[index++][2] = presentationFile.getSection().getName();
        }
    }

    public void addParticipantToSection(User user, String sectionId) {
        if (selectedPresentationFile == null) {
            participantView.showMessage("You have to upload a file before join a section of this conference");
            return;
        }
        Section section = sectionPersistence.getSectionById(sectionId);
        if (userIsParticipantAtThisSection(user, section)) {
            participantView.showMessage("You have already joined this section");
            return;
        }
        Participant participant = getParticipant(user);
        participantPersistence.insert(participant);
        PresentationFile presentationFile1 = getPresentationFile(section, selectedPresentationFile, participant);
        presentationFilePersistence.insert(presentationFile1);
        addSectionParticipantsRow(participant, section);
        showAllSectionsOfThisParticipant();
    }

    public void updateJoinedSections() {
        List<Section> sectionList = getSectionsFromDataBase();
        if (participantView.getSectionsTable().getSelectedRow() >= sectionList.size() || participantView.getSectionsTable().getSelectedRow() < 0) {
            participantView.showMessage("You must select a section");
            return;
        }
        addParticipantToSection(participantView.getLoggedUser(), sectionList.get(participantView.getSectionsTable().getSelectedRow()).getSectionId());
    }

    public void showAllSectionsOfThisParticipant() {
        List<Section> sectionList = getAllSectionsOfThisUser(participantView.getLoggedUser());
        int index = 0;
        for (Section section : sectionList) {
            participantView.getJoinedSectionsData()[index][0] = section.getName();
            participantView.getJoinedSectionsData()[index][1] = section.getSchedule().getDate();
            participantView.getJoinedSectionsData()[index][2] = section.getSchedule().getStartHour();
            participantView.getJoinedSectionsData()[index++][3] = section.getSchedule().getEndHour();
        }
        participantView.getFrame().getContentPane().repaint();
    }

    public void setSelectedPresentationFile() {
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\flore\\Desktop\\Conferences\\files");
        int opened = jFileChooser.showSaveDialog(null);
        if (opened == JFileChooser.APPROVE_OPTION) {
        }
        this.selectedPresentationFile = jFileChooser.getSelectedFile().getAbsolutePath();
    }

    public void getSectionsFromDataBaseAndUpdateTable() {
        List<Section> sectionList = sectionPersistence.readAll();
        int index = 0;
        for (Section section : sectionList) {
            participantView.getSectionsData()[index][0] = section.getName();
            participantView.getSectionsData()[index][1] = section.getSchedule().getDate();
            participantView.getSectionsData()[index][2] = section.getSchedule().getStartHour();
            participantView.getSectionsData()[index++][3] = section.getSchedule().getEndHour();
        }
        participantView.getFrame().getContentPane().repaint();
    }

    public List<Section> getAllSectionsOfThisUser(User user) {
        List<SectionParticipant> sectionParticipants = sectionParticipantPersistence.readAll();
        List<Section> sections = new ArrayList<>();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getName().equals(user.getFirstName())) {
                sections.add(sectionParticipant.getSection());
            }
        }
        return sections;
    }

    public PresentationFile getPresentationFile(Section section, String pathToFile, Participant participant) {
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress(pathToFile);
        presentationFile.setParticipant(participant);
        presentationFile.setSection(section);
        return presentationFile;
    }

    public List<Section> getSectionsFromDataBase() {
        return sectionPersistence.readAll();
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

    private SectionParticipant getSectionParticipant(Participant participant, Section section) {
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
}
