package viewModel.command.participantComands;

import model.*;
import model.persistence.PresentationFilePersistence;
import viewModel.VMParticipant;
import viewModel.command.Command;

import java.util.ArrayList;
import java.util.List;

public class ShowSelectedSectionsCommand implements Command {
    private VMParticipant vmParticipant;

    public ShowSelectedSectionsCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    @Override
    public void execute() {
        vmParticipant.getJoinedSectionsTable().get().clearData();
        List<Section> sectionList = getAllSectionsOfThisUser(vmParticipant.getLoggedUser());
        for (Section section : sectionList) {
                    List<String> row = new ArrayList<>();
                    row.add(section.getName());
                    vmParticipant.getJoinedSectionsTable().get().add(row);
        }
    }

    public List<Section> getAllSectionsOfThisUser(User user) {
        List<PresentationFile> presentationFiles = (new PresentationFilePersistence()).readAll();
        List<Section> sections = new ArrayList<>();
        for (PresentationFile presentationFile : presentationFiles) {
            if (presentationFile.getParticipant().getName().equals(user.getFirstName())) {
                sections.add(presentationFile.getSection());
            }
        }
        return sections;
    }
}
