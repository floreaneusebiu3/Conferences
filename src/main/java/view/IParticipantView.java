package view;

import model.PresentationFile;
import model.Section;

import javax.swing.*;
import java.util.List;

public interface IParticipantView {
    void updateSectionsTable(List<Section> sectionList);
    void showMessageAlreadyJoinedThisSection();
    void showAllSectionsOfThisParticipant();
    void showMessageMustSelectAFile();
    void showExplorer();
    void updateJoinedSections();
    void updateSections();
    void showAllFiles(List<PresentationFile> presentationFiles);
    public void showMessageMustSelectAFileToBeOpened();
    public JTable getFilesTable();
}
