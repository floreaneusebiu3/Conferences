package view;

import model.Participant;
import model.PresentationFile;

import javax.swing.*;
import java.util.List;

public interface IOrganizerView {
    void showMessage(String message);

    void updateParticipantsFilesTable(Object[][] data, List<Participant> participants, List<PresentationFile> presentationFiles);


    void updateFilteredParticipantsTable(Object[][] data, List<Participant> participants);


    void clearFilteredTable();

    void clearParticipantsFilesTable();

    JTable getParticipantsFilesTable();

    Object[][] getFilteredParticipantsData();

    Object[][] getParticipantsFilesData();

    JTextField getSectionTextField();


}
