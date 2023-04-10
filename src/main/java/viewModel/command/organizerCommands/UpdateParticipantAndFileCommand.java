package viewModel.command.organizerCommands;

import model.Participant;
import model.PresentationFile;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;

public class UpdateParticipantAndFileCommand implements Command {
    private VMOrganizer vmOrganizer;

    public UpdateParticipantAndFileCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        int index = vmOrganizer.getSelectedRowFromParticipantsFilesTable().get();
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        if (participants.size() == 0 || presentationFiles.size() == 0 | !checkCorrectIndex(presentationFiles, participants, index)) {
            showMessage("You haven't chosen a correct row");
            return;
        }
        Participant updatedParticipant = getParticipantByIndex(index, participants);
        if (updatedParticipant != null) {
            for (Participant participant : participants) {
                if (participant.getParticipantId().equals(updatedParticipant.getParticipantId())) {
                    participant.setName((String) vmOrganizer.getParticipantsFilesTable().get().getValueAt(index, 0));
                    (new ParticipantPersistence()).update(updatedParticipant);
                }
            }
        }
        PresentationFile updatedFile = getPresentationFileByIndex(index, participants);
        if (updatedFile != null) {
            updatedFile.setFileAddress((String) vmOrganizer.getParticipantsFilesTable().get().getValueAt(index, 2));
            (new PresentationFilePersistence()).update(updatedFile);
        }
    }

    private Participant getParticipantByIndex(int givenIndex, List<Participant> participants) {
        int k = 0;
        for (Participant participant : participants) {
            if (givenIndex == k) {
                return participant;
            } else {
                k++;
                for (PresentationFile presentationFile : (new ParticipantPersistence()).getPresentationsFileForThisParticipant(participant)) {
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
                for (PresentationFile presentationFile : (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant)) {
                    if (k == givenIndex)
                        return presentationFile;
                    else
                        k++;
                }
            }
        }
        return null;
    }

    private boolean checkCorrectIndex(List<PresentationFile> presentationFiles, List<Participant> participants, int index) {
        if (index < 0 | index > presentationFiles.size() + participants.size()) {
            showMessage("You must select a participant or file");
            return false;
        }
        return true;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
