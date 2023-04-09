package viewModel.command;

import model.PresentationFile;
import model.persistence.PresentationFilePersistence;
import viewModel.VMParticipant;

import java.util.ArrayList;
import java.util.List;

public class UpdateFilesTable implements Command{
    private VMParticipant vmParticipant;

    public UpdateFilesTable(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    @Override
    public void execute() {
        vmParticipant.getFilesTable().get().clearData();
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        for (PresentationFile presentationFile : presentationFiles) {
            List<String> row = new ArrayList<>();
            row.add(presentationFile.getFileAddress());
            row.add(presentationFile.getParticipant().getName());
            row.add(presentationFile.getSection().getName());
            vmParticipant.getFilesTable().get().add(row);
        }
    }
}
