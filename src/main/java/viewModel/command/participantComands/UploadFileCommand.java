package viewModel.command.participantComands;

import model.Section;
import model.persistence.SectionPersistence;
import viewModel.VMParticipant;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;

public class UploadFileCommand implements Command {
    private VMParticipant vmParticipant;

    public UploadFileCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    @Override
    public void execute() {
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\flore\\Desktop\\Conferences\\files");
        int opened = jFileChooser.showSaveDialog(null);
        if (opened == JFileChooser.APPROVE_OPTION) {
        }
        vmParticipant.setSelectedPresentationFile(jFileChooser.getSelectedFile().getAbsolutePath());
    }
}
