package viewModel.command.participantComands;

import model.PresentationFile;
import model.persistence.PresentationFilePersistence;
import viewModel.VMParticipant;
import viewModel.command.Command;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OpenFileCommand implements Command {
    private VMParticipant vmParticipant;

    public OpenFileCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    @Override
    public void execute() {
        List<PresentationFile> files = (new PresentationFilePersistence()).readAll();
        int selectedFile = vmParticipant.getSelectedRowFromFilesTable().get();
        if (selectedFile >= files.size() || selectedFile < 0) {
            showMessage("You must select a file");
            return;
        }
        PresentationFile presentationFile = files.get(selectedFile);
        File file = new File(presentationFile.getFileAddress());
        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
