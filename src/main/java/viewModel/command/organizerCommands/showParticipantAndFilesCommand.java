package viewModel.command.organizerCommands;

import model.Participant;
import model.PresentationFile;
import model.persistence.ParticipantPersistence;
import model.persistence.PresentationFilePersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import java.util.ArrayList;
import java.util.List;

public class showParticipantAndFilesCommand implements Command {
    private VMOrganizer vmOrganizer;

    public showParticipantAndFilesCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        List<Participant> participantList = (new ParticipantPersistence()).readAll();
        addTableData(participantList);
    }

    private void addTableData(List<Participant> participantList) {
        vmOrganizer.getParticipantsFilesTable().get().clearData();
        List<String> row;
        for (Participant participant : participantList) {
            row = new ArrayList<>();
            row.add(participant.getName());
            row.add(String.valueOf(participant.isApproved()));
            vmOrganizer.getParticipantsFilesTable().get().add(row);
            for (PresentationFile presentationFile : (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant)) {
                row = new ArrayList<>();
                row.add("");
                row.add("");
                row.add(presentationFile.getFileAddress());
                row.add(presentationFile.getSection().getName());
                vmOrganizer.getParticipantsFilesTable().get().add(row);
            }
        }
        addEmptyRow();
    }

    private void addEmptyRow() {
        List<String> row = new ArrayList<>();
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        vmOrganizer.getParticipantsFilesTable().get().add(row);
    }
}
