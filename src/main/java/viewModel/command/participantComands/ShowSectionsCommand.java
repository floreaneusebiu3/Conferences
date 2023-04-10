package viewModel.command.participantComands;

import model.Schedule;
import model.Section;
import model.persistence.SectionPersistence;
import viewModel.VMParticipant;
import viewModel.command.Command;

import java.util.ArrayList;
import java.util.List;

public class ShowSectionsCommand implements Command {
    private VMParticipant vmParticipant;

    public ShowSectionsCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    @Override
    public void execute() {
        vmParticipant.getSectionsTable().get().clearData();
        List<Section> sections = (new SectionPersistence()).readAll();
        System.out.println(sections.size());
        for (Section section : sections) {
            System.out.println( section.getName() + "--" + (new SectionPersistence()).getSchedulesForThisSection(section).size()+ "*****************************************************************************************************************************");
            for(Schedule schedule: (new SectionPersistence()).getSchedulesForThisSection(section)) {
                List<String> row = new ArrayList<>();
                row.add(section.getName());
                row.add( schedule.getDate().toString());
                row.add(String.valueOf(schedule.getStartHour()));
                row.add(String.valueOf(schedule.getEndHour()));
                vmParticipant.getSectionsTable().get().add(row);
            }
        }
    }
}
