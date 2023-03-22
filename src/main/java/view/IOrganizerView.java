package view;

import model.Participant;
import model.PresentationFile;

import javax.swing.*;
import java.util.List;

public interface IOrganizerView {
	void updateParticipantsFilesTable(Object[][] data, List<Participant> participants, List<PresentationFile> presentationFiles);
	public Object[][] getParticipantsFilesData();
	public JTextField getSectionTextField();
	public void updateFilteredParticipantsTable(Object[][] data, List<Participant> participants);
	public Object[][] getFilteredParticipantsData(); 
	    public void clearFilteredTable();
		    public void clearParticipantsFilesTable();



}
