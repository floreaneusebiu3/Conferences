package controller;

import model.*;
import model.persistence.*;
import utils.Language;
import view.ParticipantView;


import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ParticipantController implements Observer {
    private ParticipantView participantView;
    private SectionPersistence sectionPersistence;
    private PresentationFilePersistence presentationFilePersistence;
    private ParticipantPersistence participantPersistence;
    private String userId;
    private String userMail;
    private String selectedPresentationFile;
    private User loggedUser;
    private Language language;

    public ParticipantController(Language language, String userId, String userMail) {
        this.language = language;
        this.language.attachObserver(this);
        this.userId = userId;
        this.userMail = userMail;
        participantView = new ParticipantView();
        presentationFilePersistence = new PresentationFilePersistence();
        sectionPersistence = new SectionPersistence();
        participantPersistence = new ParticipantPersistence();
        setUser();
        showDataInSectionsTable();
        showDataInJoinedSectionsTable();
        addActionsListeners();
    }

    @Override
    public void update() {
        int index = language.getCurrentLanguage();
        changeHeader(participantView.getSectionsTable(), language.getParticipantSectionsHeadTexts().get(index));
        changeHeader(participantView.getFilesTable(), language.getParticipantFilesHeadTexts().get(index));
        changeHeader(participantView.getJoinedSectionsTable(), language.getParticipantJoinedSectionsHeadTexts().get(index));
        participantView.getSectionsTableTitle().setText(language.getParticipantSectionsTableTitleTexts().get(index));
        participantView.getJoinedSectionsTitle().setText(language.getParticipantJoinedSectionsTableTitleTexts().get(index));
        participantView.getRegisteredCondition().setText(language.getParticipantRegisteredConditionTexts().get(index));
        participantView.getJoinSectionButton().setText(language.getParticipantJoinSectionButtonTexts().get(index));
        participantView.getSeeConferenceVolumeButton().setText(language.getParticipantSeeConferenceButtonTexts().get(index));
        participantView.getOpenFileButton().setText(language.getParticipantOpenFileButtonTexts().get(index));
    }

    private void changeHeader(JTable table, String[] newHead) {
        JTableHeader header = table.getTableHeader();
        TableColumnModel colModel = header.getColumnModel();
        for (int i = 0; i < colModel.getColumnCount(); i++) {
            TableColumn col = colModel.getColumn(i);
            col.setHeaderValue(newHead[i]);
        }
        header.repaint();
    }

    private void addActionsListeners() {
        participantView.getJoinSectionButton().addActionListener(e -> {
            joinSection();
            showDataInJoinedSectionsTable();
        });

        participantView.getUploadFileButton().addActionListener(e -> uploadFile());

        participantView.getOpenFileButton().addActionListener(e -> openFile());

        participantView.getBackButton().addActionListener(e -> {
            participantView.getFrame().remove(participantView.getVolumePanel());
            participantView.getFrame().add(participantView.getInitPanel());
            participantView.getFrame().repaint();
        });

        participantView.getSeeConferenceVolumeButton().addActionListener(e -> {
            if (loggedUser.isApproved()) {
                participantView.getFrame().remove(participantView.getInitPanel());
                participantView.getFrame().add(participantView.getVolumePanel());
                showDataInFilesTable();
                participantView.getFrame().repaint();
            }
        });

        participantView.getLanguageComboBox().addActionListener(e -> chooseLanguage());
    }

    private void chooseLanguage() {
        language.setCurrentLanguage(participantView.getLanguageComboBox().getSelectedIndex());
    }

    private void joinSection() {
        int selectedIndex = participantView.getSectionsTable().getSelectedRow();
        List<Section> sectionList = sectionPersistence.readAll();
        String searchedSectionName = (String) participantView.getSectionsData()[selectedIndex][0];
        addParticipantToSection(loggedUser, getSectionId(sectionList, searchedSectionName));
    }

    private void addParticipantToSection(User user, String sectionId) {
        if (selectedPresentationFile == null) {
            showMessage(language.getParticipantUploadFileMessageTexts().get(participantView.getLanguageComboBox().getSelectedIndex()));
            return;
        }
        Section section = sectionPersistence.getSectionById(sectionId);
        if (isParticipantAtThisSection(user, section)) {
            showMessage(language.getParticipantAlreadyJoinedSectionMessageTexts().get(participantView.getLanguageComboBox().getSelectedIndex()));
            return;
        }
        Participant participant = getParticipant(user);
        PresentationFile presentationFile1 = getPresentationFile(section, selectedPresentationFile, participant);
        presentationFilePersistence.insert(presentationFile1);
        addSectionParticipantsRow(participant, section);
    }

    private void addSectionParticipantsRow(Participant participant, Section section) {
        SectionParticipant sectionParticipant = getSectionParticipant(participant, section);
        new SectionParticipantPersistence().insert(sectionParticipant);
    }

    private SectionParticipant getSectionParticipant(Participant participant, Section section) {
        return new SectionParticipant(UUID.randomUUID().toString(), participant, section);
    }

    private PresentationFile getPresentationFile(Section section, String pathToFile, Participant participant) {
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress(pathToFile);
        presentationFile.setParticipant(participant);
        presentationFile.setSection(section);
        return presentationFile;
    }

    private Participant getParticipant(User user) {
        List<Participant> participants = participantPersistence.readAll();
        for (Participant participant : participants) {
            if (participant.getName().equals(user.getFirstName()))
                return participant;
        }
        Participant participant = new Participant();
        participant.setName(user.getFirstName());
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setUser(user);
        participant.setApproved(false);
        participant.setUser(loggedUser);
        participantPersistence.insert(participant);
        return participant;
    }

    private String getSectionId(List<Section> sections, String searchedSectionName) {
        for (Section section : sections) {
            if (section.getName().equals(searchedSectionName)) {
                return section.getSectionId();
            }
        }
        return null;
    }

    private boolean isParticipantAtThisSection(User user, Section section) {
        List<SectionParticipant> sectionParticipants = (new SectionParticipantPersistence()).readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getSection().getSectionId().equals(section.getSectionId()) && sectionParticipant.getParticipant().getName().equals(user.getFirstName()))
                return true;
        }
        return false;
    }

    private void openFile() {
        List<PresentationFile> files = presentationFilePersistence.readAll();
        int selectedFile = participantView.getFilesTable().getSelectedRow();
        if (selectedFile >= files.size() || selectedFile < 0) {
            showMessage(language.getParticipantMustSelectFileMessageTexts().get(participantView.getLanguageComboBox().getSelectedIndex()));
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

    private void showDataInFilesTable() {
        clearFilesTableData();
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        int index = 0;
        for (PresentationFile presentationFile : presentationFiles) {
            participantView.getFilesData()[index][0] = presentationFile.getFileAddress();
            participantView.getFilesData()[index][1] = presentationFile.getParticipant().getName();
            participantView.getFilesData()[index++][2] = presentationFile.getSection().getName();
        }
    }

    private void uploadFile() {
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\flore\\Desktop\\Conferences\\files");
        int opened = jFileChooser.showSaveDialog(null);
        if (opened == JFileChooser.APPROVE_OPTION) {
        }
        selectedPresentationFile = jFileChooser.getSelectedFile().getAbsolutePath();
    }

    private void showDataInJoinedSectionsTable() {
        clearJoinedSectionsTableData();
        List<Section> sectionList = sectionPersistence.getAllSectionsByUser(loggedUser.getFirstName());
        int index = 0;
        for (Section section : sectionList) {
            participantView.getJoinedSectionsData()[index++][0] = section.getName();
        }
        participantView.getFrame().repaint();
    }

    private void showDataInSectionsTable() {
        clearSectionsTableData();
        List<Section> sections = sectionPersistence.readAll();
        int index = 0;
        for (Section section : sections) {
            for (Schedule schedule : (new SectionPersistence()).getSchedulesForThisSection(section)) {
                participantView.getSectionsData()[index][0] = section.getName();
                participantView.getSectionsData()[index][1] = schedule.getDate().toString();
                participantView.getSectionsData()[index][2] = String.valueOf(schedule.getStartHour());
                participantView.getSectionsData()[index++][3] = String.valueOf(schedule.getEndHour());
            }
        }
        participantView.getFrame().repaint();
    }

    private void clearSectionsTableData() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 4; j++) {
                participantView.getSectionsData()[i][j] = "";
            }
        }
    }

    private void clearJoinedSectionsTableData() {
        for (int i = 0; i < 100; i++) {
            participantView.getJoinedSectionsData()[i][0] = "";
        }
    }

    private void clearFilesTableData() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 3; j++) {
                participantView.getFilesData()[i][j] = "";
            }
        }
    }

    private void setUser() {
        if (!userMail.equals("registeredUser")) {
            User loggedUser = new User();
            loggedUser.setUserId(userId);
            loggedUser.setRole("participant");
            loggedUser.setFirstName("user" + new Random().nextInt(1000));
            loggedUser.setLastName("user" + new Random().nextInt(1000));
            loggedUser.setUsername("user" + new Random().nextInt(1000));
            loggedUser.setPassword("password" + new Random().nextInt(1000));
            loggedUser.setMail(userMail);
            loggedUser.setAge(0);
            loggedUser.setApproved(false);
            (new UserPersistence()).insert(loggedUser);
            this.loggedUser = loggedUser;
            return;
        }
        List<User> users = (new UserPersistence()).readAll();
        for (User user : users) {
            if (userMail != null) {
                if (user.getUserId().equals(userId)) {
                    loggedUser = user;
                    return;
                }
            }
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
