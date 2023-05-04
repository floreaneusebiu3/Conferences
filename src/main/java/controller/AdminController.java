package controller;

import model.Participant;
import model.PresentationFile;
import model.SectionParticipant;
import model.User;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import model.persistence.UserPersistence;
import utils.Language;
import view.AdminView;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.List;
import java.util.UUID;

public class AdminController implements Observer {
    private AdminView adminView;
    private UserPersistence userPersistence;
    private ParticipantPersistence participantPersistence;
    private Language language;

    public AdminController(Language language) {
        this.language = language;
        this.language.attachObserver(this);
        adminView = new AdminView();
        userPersistence = new UserPersistence();
        participantPersistence = new ParticipantPersistence();
        addActionListeners();
        showDataInUsersTable();
    }

    @Override
    public void update() {
        int index = language.getCurrentLanguage();
        changeHeader(adminView.getUsersTable(), language.getAdminUsersHeadTexts().get(index));
        adminView.getAdminLabel().setText(language.getAdminLabesTexts().get(index));
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

    private void addActionListeners() {
        adminView.getInsertButton().addActionListener(e -> {
            insertUser();
            showDataInUsersTable();
        });

        adminView.getUpdateButton().addActionListener(e -> {
            updateUser();
            showDataInUsersTable();
        });

        adminView.getDeleteButton().addActionListener(e -> {
            deleteUser();
            showDataInUsersTable();
        });

        adminView.getLanguageComboBox().addActionListener(e -> chooseLanguage());
    }

    private void chooseLanguage() {
        language.setCurrentLanguage(adminView.getLanguageComboBox().getSelectedIndex());
    }

    private void deleteUser() {
        int selectedRow = adminView.getUsersTable().getSelectedRow();
        List<Participant> participants = participantPersistence.readAll();
        User user = getSelectedUser(selectedRow);
        if (user == null) {
            showMessage(language.getAdminMustSelectAUserMessageTexts().get(language.getCurrentLanguage()));
            return;
        }
        for (Participant participant : participants) {
            if (participant.getUser() != null && participant.getUser().getFirstName().equals(user.getFirstName())) {
                deleteSectionParticipantOfThisParticipant(participant);
                deleteParticipantPresentations(participant);
                participantPersistence.delete(participant.getParticipantId());
            }
        }
        userPersistence.delete(user.getUserId());
    }

    private User getSelectedUser(int selectedRow) {
        List<User> users = userPersistence.readAll();
        if (selectedRow > users.size()) {
            return null;
        }
        return users.get(selectedRow);
    }

    private void deleteParticipantPresentations(Participant participant) {
        PresentationFilePersistence presentationFilePersistence = new PresentationFilePersistence();
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                presentationFilePersistence.delete(presentationFile.getPresentationFileId());
            }
        }
    }

    private void deleteSectionParticipantOfThisParticipant(Participant pa) {
        SectionParticipantPersistence sectionParticipantPersistence = new SectionParticipantPersistence();
        List<SectionParticipant> sectionParticipants = sectionParticipantPersistence.readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getParticipantId().equals(pa.getParticipantId())) {
                sectionParticipantPersistence.delete(sectionParticipant.getSectionParticipantId());
            }
        }
    }

    private void updateUser() {
        int index = adminView.getUsersTable().getSelectedRow();
        List<User> users = userPersistence.readAll();
        if (index > users.size() | index < 0) {
            showMessage(language.getAdminMustSelectAUserMessageTexts().get(language.getCurrentLanguage()));
            return;
        }
        User user = getUser();
        userPersistence.update(user);
    }

    private void insertUser() {
        User user = computeUser();
        userPersistence.insert(user);
    }

    private User computeUser() {
        int selectedRow = adminView.getUsersTable().getSelectedRow();
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setApproved(adminView.getUsersData()[selectedRow][0].equals("true"));
        user.setFirstName((String) adminView.getUsersData()[selectedRow][1]);
        user.setLastName((String) adminView.getUsersData()[selectedRow][2]);
        user.setAge(Integer.parseInt((String) adminView.getUsersData()[selectedRow][3]));
        user.setMail((String) adminView.getUsersData()[selectedRow][4]);
        user.setRole((String) adminView.getUsersData()[selectedRow][5]);
        user.setUsername((String) adminView.getUsersData()[selectedRow][6]);
        user.setPassword((String) adminView.getUsersData()[selectedRow][7]);
        return user;
    }

    private User getUser() {
        int selectedRow = adminView.getUsersTable().getSelectedRow();
        User user = getSelectedUser(selectedRow);
        if (user == null) {
            return null;
        }
        user.setApproved((Boolean) adminView.getUsersData()[selectedRow][0]);
        user.setFirstName((String) adminView.getUsersData()[selectedRow][1]);
        user.setLastName((String) adminView.getUsersData()[selectedRow][2]);
        user.setAge(Integer.parseInt((String) adminView.getUsersData()[selectedRow][3]));
        user.setMail((String) adminView.getUsersData()[selectedRow][4]);
        user.setRole((String) adminView.getUsersData()[selectedRow][5]);
        user.setUsername((String) adminView.getUsersData()[selectedRow][6]);
        user.setPassword((String) adminView.getUsersData()[selectedRow][7]);
        return user;
    }

    private void showDataInUsersTable() {
        clearDataInUsersTable();
        List<User> allUsers = userPersistence.readAll();
        System.out.println(allUsers.size());
        int index = 0;
        for (User user : allUsers) {
            adminView.getUsersData()[index][0] = user.isApproved();
            adminView.getUsersData()[index][1] = user.getFirstName();
            adminView.getUsersData()[index][2] = user.getLastName();
            adminView.getUsersData()[index][3] = String.valueOf(user.getAge());
            adminView.getUsersData()[index][4] = user.getMail();
            adminView.getUsersData()[index][5] = user.getRole();
            adminView.getUsersData()[index][6] = user.getUsername();
            adminView.getUsersData()[index++][7] = user.getPassword();
        }
        adminView.getFrame().repaint();
    }

    private void clearDataInUsersTable() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 8; j++) {
                adminView.getUsersData()[i][j] = "";
            }
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
