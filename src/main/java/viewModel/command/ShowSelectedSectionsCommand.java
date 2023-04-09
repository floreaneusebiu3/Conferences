package viewModel.command;

import model.Section;
import model.SectionParticipant;
import model.User;
import model.persistence.SectionParticipantPersistence;
import viewModel.VMParticipant;

import java.util.ArrayList;
import java.util.List;

public class ShowSelectedSectionsCommand implements Command{
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
            row.add( section.getSchedule().getDate().toString());
            row.add(String.valueOf(section.getSchedule().getStartHour()));
            row.add(String.valueOf(section.getSchedule().getEndHour()));
            vmParticipant.getJoinedSectionsTable().get().add(row);
        }
    }

    public List<Section> getAllSectionsOfThisUser(User user) {
        List<SectionParticipant> sectionParticipants = (new SectionParticipantPersistence()).readAll();
        List<Section> sections = new ArrayList<>();
        for (SectionParticipant sectionParticipant : sectionParticipants) {
            if (sectionParticipant.getParticipant().getName().equals(user.getFirstName())) {
                sections.add(sectionParticipant.getSection());
            }
        }
        return sections;
    }
}
