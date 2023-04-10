package viewModel.command.organizerCommands;

import model.Participant;
import model.persistence.ParticipantPersistence;
import utils.EmailSender;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import java.util.List;

public class ApproveParticipantCommand implements Command {
    private VMOrganizer vmOrganizer;

    public ApproveParticipantCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        EmailSender emailSender = new EmailSender();
        int index = vmOrganizer.getSelectedRowFromFilteredParticipantsTable().get();
        String participantName = (String) vmOrganizer.getFilteredParticipantsTable().get().getValueAt(index, 0);
        Participant participant = getParticipant(participantName);
        if (participant != null) {
            participant.setApproved(true);
            (new ParticipantPersistence()).update(participant);
            vmOrganizer.getShowFilteredParticipantsCommand().execute();
            if (participant.getUser() != null) {
                emailSender.sendMail(participant.getUser().getMail(), participant.getUser().getMail(), "YOU WAS ACCEPTED AT REQUESTED CONFERENCE");
            }
        }
    }

    private Participant getParticipant(String participantName) {
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        for (Participant participant : participants) {
            if (participant.getName().equals(participantName)) {
                return participant;
            }
        }
        return null;
    }
}
