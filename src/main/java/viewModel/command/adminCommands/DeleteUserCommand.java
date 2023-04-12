package viewModel.command.adminCommands;

import model.Participant;
import model.PresentationFile;
import model.SectionParticipant;
import model.User;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import model.persistence.SectionParticipantPersistence;
import model.persistence.UserPersistence;
import viewModel.VMAdmin;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;

public class DeleteUserCommand implements Command {
    private VMAdmin vmAdmin;

    public DeleteUserCommand(VMAdmin vmAdmin) {
        this.vmAdmin = vmAdmin;
    }

    @Override
    public void execute() {
        int index = vmAdmin.getSelectedRowFromUsersTable().get();
        List<User> users = (new UserPersistence()).readAll();
        if (index > users.size() || index < 0) {
            showMessage("You must select a user");
            return;
        }
        deleteUser(users, index);
    }

    private void deleteUser(List<User> users, int index) {
        ParticipantPersistence participantPersistence = new ParticipantPersistence();
        List<Participant> participants = participantPersistence.readAll();
        User user = users.get(index);
        for (Participant participant : participants) {
            if (participant.getUser().getFirstName().equals(user.getFirstName())) {
                deleteSectionParticipantOfThisParticipant(participant);
                deleteParticipantPresentations(participant);
                participantPersistence.delete(participant.getParticipantId());
            }
        }
        (new UserPersistence()).delete(user.getUserId());
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

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
