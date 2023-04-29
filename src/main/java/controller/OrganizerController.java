package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import model.*;
import model.persistence.*;
import utils.EmailSender;
import utils.Language;
import view.OrganizerView;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class OrganizerController implements Observer{
    private OrganizerView organizerView;
    private SectionPersistence sectionPersistence;
    private ParticipantPersistence participantPersistence;
    private PresentationFilePersistence presentationFilePersistence;
    private SchedulePersistence schedulePersistence;
    private Language language;

    public OrganizerController(Language language) {
        this.language = language;
        this.language.attachObserver(this);
        organizerView = new OrganizerView();
        sectionPersistence = new SectionPersistence();
        participantPersistence = new ParticipantPersistence();
        presentationFilePersistence = new PresentationFilePersistence();
        schedulePersistence = new SchedulePersistence();
        addFocusListeners();
        addActionListeners();
        showDataInSectionsTable();
        showDataInParticipantsTable();
    }

    @Override
    public void update() {
        int index = language.getCurrentLanguage();
       changeHeader(organizerView.getParticipantsFilesTable(), language.getOrganizerParticipantsHeadTexts().get(index));
       changeHeader(organizerView.getSectionsTable(), language.getOrganizerSectionsHeadTexts().get(index));
       changeHeader(organizerView.getFilteredParticipantsTable(), language.getOrganizerFilteredParticipantsHeadTexts().get(index));
       organizerView.getSectionTextField().setText(language.getOrganizerSectionLabelTexts().get(index));
       organizerView.getDateField().setText(language.getOrganizerDateLabelTexts().get(index));
       organizerView.getStartHourField().setText(language.getOrganizerStartHourLabelTexts().get(index));
       organizerView.getEndHourField().setText(language.getOrganizerEndHourLabelTexts().get(index));
       organizerView.getFilterButton().setText(language.getOrganizerFilterButtonTexts().get(index));
       organizerView.getAddScheduleButton().setText(language.getOrganizerAddScheduleTexts().get(index));
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
        organizerView.getFilterButton().addActionListener(e -> {
            filterParticipants();
            serializeParticipantsAndFiles();
        });

        organizerView.getInsertButton().addActionListener(e -> {
            insertParticipantAndFile();
            showDataInParticipantsTable();
        });

        organizerView.getDeleteButton().addActionListener(e -> {
            deleteParticipantAndFile();
            showDataInParticipantsTable();
        });

        organizerView.getUpdateButton().addActionListener(e -> {
            updateParticipantAndFile();
            showDataInParticipantsTable();
        });

        organizerView.getAddScheduleButton().addActionListener(e -> {
            addNewSchedule();
            showDataInSectionsTable();
        });

        organizerView.getApproveButton().addActionListener(e -> {
            approveUser();
            filterParticipants();
        });

        organizerView.getLanguageComboBox().addActionListener(e -> chooseLanguage());
    }

    private void chooseLanguage() {
        language.setCurrentLanguage(organizerView.getLanguageComboBox().getSelectedIndex());
    }
    private void serializeParticipantsAndFiles() {
        String sectionName = organizerView.getSectionTextField().getText();
        List<Participant> participants = participantPersistence.getParticipantsBySection(sectionName);
        writeDataInJsonFile(participants);
        writeDataInCsvFile(participants);
        try {
            writeDataInXML(participants);
            writeDataInTxt(participants);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void writeDataInCsvFile(List<Participant> participants) {
        List<String[]> values = new ArrayList<>();
        try (CSVWriter writer = new CSVWriter(new FileWriter("presentationFiles.csv"))) {
            for (Participant participant : participants) {
                List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
                for (PresentationFile presentationFile : presentationFiles) {
                    Field[] fields = PresentationFile.class.getDeclaredFields();
                    String[] row = new String[fields.length];
                    int index = 0;
                    for (Field field : fields) {
                        if (index > 1) {
                            row[index++] = participant.getName();
                            break;
                        }
                        field.setAccessible(true);
                        row[index++] = String.valueOf(field.get(presentationFile));
                    }
                    values.add(row);
                }
            }
            writer.writeAll(values);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void writeDataInJsonFile(List<Participant> participants) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PresentationFile> presentationFilesToBeSerialized = new ArrayList<>();
        for (Participant participant : participants) {
            List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
            presentationFilesToBeSerialized.addAll(presentationFiles);
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("presentationFiles.json"), presentationFilesToBeSerialized);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeDataInXML(List<Participant> participants) throws IOException, IllegalAccessException {
        FileWriter file = new FileWriter("presentationFiles.xml");
        StringBuilder sb = new StringBuilder();
        List<String[]> values = new ArrayList<>();
        sb.append("<?xml version=\"1.0\" ?>\n\n");
        sb.append("<presentationFiles>\n\n");
        for (Participant participant : participants) {
            List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
            for (PresentationFile presentationFile : presentationFiles) {
                Field[] fields = PresentationFile.class.getDeclaredFields();
                String[] row = new String[fields.length];
                int index = 0;
                for (Field field : fields) {
                    if (index > 1) {
                        row[index++] = participant.getName();
                        break;
                    }
                    field.setAccessible(true);
                    row[index++] = String.valueOf(field.get(presentationFile));
                }
                values.add(row);
            }
        }
        for (String[] data : values) {
            sb.append("<presentationFile id=\"" + data[0] + "\">\n");
            sb.append("<name>" + data[1] + "</name>\n");
            sb.append("</presentationFile>\n\n");
        }
        sb.append("</presentationFiles>");
        file.write(sb.toString());
        file.close();
    }

    private void writeDataInTxt(List<Participant> participants) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("presentationFiles.txt");
        for (Participant participant : participants) {
            List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant);
            for (PresentationFile presentationFile : presentationFiles) {
                StringBuilder row = new StringBuilder();
                row.append(presentationFile.getPresentationFileId())
                        .append(" ")
                        .append(presentationFile.getFileAddress())
                        .append(" ")
                        .append(presentationFile.getParticipant().getName());
                printWriter.println(row);
                printWriter.flush();
            }
        }
    }

    private void approveUser() {
        EmailSender emailSender = new EmailSender();
        int selectedRow = organizerView.getFilteredParticipantsTable().getSelectedRow();
        String participantName = (String) organizerView.getFilteredParticipantsData()[selectedRow][0];
        Participant participant = getExistingParticipant(participantName);
        if (participant != null) {
            participant.setApproved(true);
            participantPersistence.update(participant);
            if (participant.getUser() != null) {
                emailSender.sendMail(participant.getUser().getMail(), participant.getUser().getMail(), "YOU WAS ACCEPTED AT REQUESTED CONFERENCE");
            }
        }
    }

    private Participant getExistingParticipant(String participantName) {
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        for (Participant participant : participants) {
            if (participant.getName().equals(participantName)) {
                return participant;
            }
        }
        return null;
    }

    private void addNewSchedule() {
        int selectedRow = organizerView.getSectionsTable().getSelectedRow();
        String sectionName = (String) organizerView.getSectionsData()[selectedRow][0];
        Section section = getSection(sectionName);
        if (section == null) {
            showMessage(language.getOrganizerMustSelectExistingSectionMessageTexts().get(language.getCurrentLanguage()));
            return;
        }
        Schedule schedule = getSchedule(section, organizerView.getDateField().getText(), organizerView.getStartHourField().getText(), organizerView.getEndHourField().getText());
        if (schedule == null) {
            showMessage(language.getOrganizerMustInsertValidDateMessageTexts().get(language.getCurrentLanguage()));
            return;
        }
    }

    private Schedule getSchedule(Section section, String date, String startHour, String endHour) {
        if (!date.matches("[0-3][0-9]/(([0][1-9])|([1][0-2]))/\\d{4}")) {
            return null;
        }
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate ld = LocalDate.parse(date, f);
        Schedule schedule = new Schedule();
        schedule.setScheduleId(UUID.randomUUID().toString());
        schedule.setDate(ld);
        schedule.setStartHour(Integer.parseInt(startHour));
        schedule.setEndHour(Integer.parseInt(endHour));
        schedule.setSection(section);
        schedulePersistence.insert(schedule);
        return schedule;
    }

    private void updateParticipantAndFile() {
        int selectedRow = organizerView.getParticipantsFilesTable().getSelectedRow();
        List<Participant> participants = participantPersistence.readAll();
        List<PresentationFile> presentationFiles = presentationFilePersistence.readAll();
        if (participants.size() == 0 || presentationFiles.size() == 0 | !checkCorrectIndex(presentationFiles, participants, selectedRow)) {
            showMessage(language.getOrganizerIncorrectRowMessageTexts().get(language.getCurrentLanguage()));
            return;
        }
        Participant updatedParticipant = getParticipantByIndex(selectedRow, participants);
        if (updatedParticipant != null) {
            for (Participant participant : participants) {
                if (participant.getParticipantId().equals(updatedParticipant.getParticipantId())) {
                    participant.setName((String) organizerView.getParticipantsData()[selectedRow][0]);
                    (new ParticipantPersistence()).update(updatedParticipant);
                }
            }
        }
        PresentationFile updatedFile = getPresentationFileByIndex(selectedRow, participants);
        if (updatedFile != null) {
            updatedFile.setFileAddress((String) organizerView.getParticipantsData()[selectedRow][2]);
            (new PresentationFilePersistence()).update(updatedFile);
        }
    }

    private boolean checkCorrectIndex(List<PresentationFile> presentationFiles, List<Participant> participants, int index) {
        if (index < 0 | index > presentationFiles.size() + participants.size()) {
            showMessage(language.getOrganizerMustSelectParticipantMessageTexts().get(language.getCurrentLanguage()));
            return false;
        }
        return true;
    }

    private void deleteParticipantAndFile() {
        int selectedRow = organizerView.getParticipantsFilesTable().getSelectedRow();
        List<Participant> participants = participantPersistence.readAll();
        Participant participantToBeDeleted = getParticipantByIndex(selectedRow, participants);
        if (participantToBeDeleted != null) {
            deleteAllSectionParticipantsOfThisParticipant(participantToBeDeleted);
            deleteAllPresentationsFilesOfThisParticipant(participantToBeDeleted);
            (new ParticipantPersistence()).delete(participantToBeDeleted.getParticipantId());
        }
        PresentationFile presentationFile = getPresentationFileByIndex(selectedRow, participants);
        if (presentationFile != null) {
            (new PresentationFilePersistence()).delete(presentationFile.getPresentationFileId());
        }
    }

    private void deleteAllSectionParticipantsOfThisParticipant(Participant participant) {
        List<SectionParticipant> sectionParticipants = (new SectionParticipantPersistence()).readAll();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                (new SectionParticipantPersistence()).delete(sectionParticipant.getSectionParticipantId());
            }
        }
    }

    private void deleteAllPresentationsFilesOfThisParticipant(Participant participant) {
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getParticipant().getParticipantId().equals(participant.getParticipantId())) {
                (new PresentationFilePersistence()).delete(presentationFile.getPresentationFileId());
            }
        }
    }

    private PresentationFile getPresentationFileByIndex(int givenIndex, List<Participant> participants) {
        int k = 0;
        for (Participant participant : participants) {
            if (givenIndex == k) {
                return null;
            } else {
                k++;
                for (PresentationFile presentationFile : (new ParticipantPersistence()).getPresentationsFileForThisParticipant(participant)) {
                    if (k == givenIndex)
                        return presentationFile;
                    else
                        k++;
                }
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

    private void insertParticipantAndFile() {
        int selectedRow = organizerView.getParticipantsFilesTable().getSelectedRow();
        Section section = getSection((String) organizerView.getParticipantsData()[selectedRow][3]);
        if (section == null) {
            showMessage(language.getOrganizerMustSelectParticipantMessageTexts().get(language.getCurrentLanguage()));
            return;
        }
        Participant participant = getParticipant(selectedRow);
        PresentationFile presentationFile = getPresentationFile(selectedRow, section, participant);
        insertParticipantAndPresentationFile(participant, presentationFile, section);
    }

    private void insertParticipantAndPresentationFile(Participant participant, PresentationFile presentationFile, Section section) {
        participantPersistence.insert(participant);
        presentationFilePersistence.insert(presentationFile);
        SectionParticipant sectionParticipant = new SectionParticipant();
        sectionParticipant.setSectionParticipantId(UUID.randomUUID().toString());
        sectionParticipant.setParticipant(participant);
        sectionParticipant.setSection(section);
        (new SectionParticipantPersistence()).insert(sectionParticipant);
    }

    private PresentationFile getPresentationFile(int index, Section section, Participant participant) {
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress((String) organizerView.getParticipantsData()[index][2]);
        presentationFile.setSection(section);
        presentationFile.setParticipant(participant);
        return presentationFile;
    }

    private Participant getParticipant(int index) {
        Participant participant = new Participant();
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setApproved(false);
        participant.setName((String) organizerView.getParticipantsData()[index][0]);
        User user = new User(UUID.randomUUID().toString(), " ", " ", 0, "mail@yahoo.com", " ", " ", "participant", false);
        (new UserPersistence()).insert(user);
        participant.setUser(user);
        return participant;
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

    private void filterParticipants() {
        clearDataInFilteredParticipantsTable();
        if (organizerView.getSectionTextField().getText().equals("all")) {
            List<Participant> participants = participantPersistence.readAll();
            showDataInFilteredParticipantsTable(participants);
            return;
        }
        List<Participant> participants = participantPersistence.getParticipantsBySection(organizerView.getSectionTextField().getText());
        Set<Participant> uniqueParticipants = new HashSet<>(participants);
        showDataInFilteredParticipantsTable(new ArrayList<>(uniqueParticipants));
    }

    private void showDataInFilteredParticipantsTable(List<Participant> participants) {
        clearDataInFilteredParticipantsTable();
        int index = 0;
        for (Participant participant : participants) {
            organizerView.getFilteredParticipantsData()[index][0] = participant.getName();
            organizerView.getFilteredParticipantsData()[index++][1] = "approved:  " + (participant.isApproved());
        }
        organizerView.getFrame().repaint();
    }

    private void showDataInSectionsTable() {
        clearDataInSectionsTable();
        List<Section> sections = sectionPersistence.readAll();
        int index = 0;
        for (Section section : sections) {
            List<Schedule> schedules = (new SectionPersistence()).getSchedulesForThisSection(section);
            for (Schedule schedule : schedules) {
                organizerView.getSectionsData()[index][0] = section.getName();
                organizerView.getSectionsData()[index][1] = schedule.getDate().toString();
                organizerView.getSectionsData()[index][2] = schedule.getStartHour();
                organizerView.getSectionsData()[index++][3] = schedule.getEndHour();
            }
        }
        organizerView.getFrame().repaint();
    }

    private void showDataInParticipantsTable() {
        clearDataInParticipantsTable();
        List<Participant> participantList = participantPersistence.readAll();
        int index = 0;
        for (Participant participant : participantList) {
            organizerView.getParticipantsData()[index][0] = participant.getName();
            organizerView.getParticipantsData()[index++][1] = String.valueOf(participant.isApproved());
            for (PresentationFile presentationFile : presentationFilePersistence.getPresentationsFileForThisParticipant(participant)) {
                organizerView.getParticipantsData()[index][2] = presentationFile.getFileAddress();
                organizerView.getParticipantsData()[index++][3] = presentationFile.getSection().getName();
            }
        }
        organizerView.getFrame().repaint();
    }

    private void addFocusListeners() {
        organizerView.getSectionTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (organizerView.getSectionTextField().getText().equals(language.getOrganizerSectionLabelTexts().get(language.getCurrentLanguage()))) {
                    organizerView.getSectionTextField().setForeground(Color.WHITE);
                    organizerView.getSectionTextField().setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (organizerView.getSectionTextField().getText().isEmpty()) {
                    organizerView.getSectionTextField().setText(language.getOrganizerSectionLabelTexts().get(language.getCurrentLanguage()));
                }
            }
        });

        organizerView.getDateField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (organizerView.getDateField().getText().equals(language.getOrganizerDateLabelTexts().get(language.getCurrentLanguage()))) {
                    organizerView.getDateField().setForeground(Color.WHITE);
                    organizerView.getDateField().setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (organizerView.getDateField().getText().isEmpty()) {
                    organizerView.getDateField().setText(language.getOrganizerDateLabelTexts().get(language.getCurrentLanguage()));
                }
            }
        });

        organizerView.getStartHourField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (organizerView.getStartHourField().getText().equals(language.getOrganizerStartHourLabelTexts().get(language.getCurrentLanguage()))) {
                    organizerView.getStartHourField().setForeground(Color.WHITE);
                    organizerView.getStartHourField().setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (organizerView.getStartHourField().getText().isEmpty()) {
                    organizerView.getStartHourField().setText(language.getOrganizerStartHourLabelTexts().get(language.getCurrentLanguage()));
                }
            }
        });

        organizerView.getEndHourField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (organizerView.getEndHourField().getText().equals(language.getOrganizerEndHourLabelTexts().get(language.getCurrentLanguage()))) {
                    organizerView.getEndHourField().setForeground(Color.WHITE);
                    organizerView.getEndHourField().setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (organizerView.getEndHourField().getText().isEmpty()) {
                    organizerView.getEndHourField().setText(language.getOrganizerEndHourLabelTexts().get(language.getCurrentLanguage()));
                }
            }
        });
    }

    private void clearDataInSectionsTable() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 4; j++) {
                organizerView.getSectionsData()[i][j] = "";
            }
        }
    }

    private void clearDataInParticipantsTable() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 4; j++) {
                organizerView.getParticipantsData()[i][j] = "";
            }
        }
    }

    private void clearDataInFilteredParticipantsTable() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 2; j++) {
                organizerView.getFilteredParticipantsData()[i][j] = "";
            }
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
