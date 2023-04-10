package viewModel.command.organizerCommands;

import model.*;
import model.persistence.*;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class InsertParticipantAndFile implements Command {
    private VMOrganizer vmOrganizer;

    public InsertParticipantAndFile(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        int index = vmOrganizer.getParticipantsFilesTable().get().getRowCount() - 1;
        Section section = getSection((String) vmOrganizer.getParticipantsFilesTable().get().getValueAt(index, 3));
        if (section == null) {
            showMessage("You must select an existing section");
            return;
        }
        Participant participant = getParticipant(index);
        PresentationFile presentationFile = getPresentationFile(index, section, participant);
        insertParticipantAndPresentationFile(participant, presentationFile, section);
    }

    private void insertParticipantAndPresentationFile(Participant participant, PresentationFile presentationFile, Section section) {
        (new ParticipantPersistence()).insert(participant);
        (new PresentationFilePersistence()).insert(presentationFile);
        SectionParticipant sectionParticipant = new SectionParticipant();
        sectionParticipant.setSectionParticipantId(UUID.randomUUID().toString());
        sectionParticipant.setParticipant(participant);
        sectionParticipant.setSection(section);
        (new SectionParticipantPersistence()).insert(sectionParticipant);
    }
    private PresentationFile getPresentationFile(int index, Section section, Participant participant) {
        PresentationFile presentationFile = new PresentationFile();
        presentationFile.setPresentationFileId(UUID.randomUUID().toString());
        presentationFile.setFileAddress((String) vmOrganizer.getParticipantsFilesTable().get().getValueAt(index, 2));
        presentationFile.setSection(section);
        presentationFile.setParticipant(participant);
        return presentationFile;
    }

    private Participant getParticipant(int index) {
        Participant participant = new Participant();
        participant.setParticipantId(UUID.randomUUID().toString());
        participant.setApproved(false);
        participant.setName((String) vmOrganizer.getParticipantsFilesTable().get().getValueAt(index, 0));
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

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
