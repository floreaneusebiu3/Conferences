package viewModel.command.organizerCommands;

import model.Participant;
import model.PresentationFile;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowFilteredParticipantsCommand implements Command {
    private VMOrganizer vmOrganizer;

    public ShowFilteredParticipantsCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        vmOrganizer.getFilteredParticipantsTable().get().clearData();
        if (vmOrganizer.getSectionField().get().equals("all")) {
            showAllParticipants();
            return;
        }
        List<Participant> participants = getParticipantsForThisSection(vmOrganizer.getSectionField().get());
        vmOrganizer.getFilteredParticipantsTable().get().clearData();
        updateFilteredParticipantsTable(participants);
    }

    private void showAllParticipants() {
        List<Participant> participants = (new ParticipantPersistence()).readAll();
        List<String> row;
        for (Participant participant : participants) {
            if (!participant.isApproved()) {
                row = new ArrayList<>();
                row.add(participant.getName());
                row.add("approved:  " + (participant.isApproved()));
                vmOrganizer.getFilteredParticipantsTable().get().add(row);
            }
        }
    }

    private List<Participant> getParticipantsForThisSection(String section) {
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        List<Participant> participants = new ArrayList<>();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getSection().getName().equals(section)) {
                participants.add(presentationFile.getParticipant());
            }
        }
        return participants;
    }

    private void updateFilteredParticipantsTable(List<Participant> participants) {
        Set<Participant> uniqueParticipants = new HashSet<>(participants);
        List<String> row;
        for (Participant participant : uniqueParticipants) {
            row = new ArrayList<>();
            row.add(participant.getName());
            row.add(vmOrganizer.getSectionField().get());
            vmOrganizer.getFilteredParticipantsTable().get().add(row);
        }
    }
}
