package viewModel.command;

import model.Section;
import model.persistence.SectionPersistence;
import viewModel.VMParticipant;

import java.util.ArrayList;
import java.util.List;

public class ShowSectionsCommand implements Command {
    private VMParticipant vmParticipant;

    public ShowSectionsCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    @Override
    public void execute() {
        List<Section> sections = (new SectionPersistence()).readAll();
        System.out.println(sections.size());
        List<String> row;
        for (Section section : sections) {
            row = new ArrayList<>();
            row.add(section.getName());
            row.add(section.getSchedule().getDate().toString());
            row.add(String.valueOf(section.getSchedule().getStartHour()));
            row.add(String.valueOf(section.getSchedule().getEndHour()));
            vmParticipant.getModel().get().add(row);
        }
    }
}
