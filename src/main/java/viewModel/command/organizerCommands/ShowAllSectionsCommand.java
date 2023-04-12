package viewModel.command.organizerCommands;

import model.Schedule;
import model.Section;
import model.persistence.SectionPersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import java.util.ArrayList;
import java.util.List;

public class ShowAllSectionsCommand implements Command {
    private VMOrganizer vmOrganizer;

    public ShowAllSectionsCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        vmOrganizer.getSectionsTable().get().clearData();
        List<Section> sections = (new SectionPersistence()).readAll();
        List<String> row;
        for (Section section: sections) {
            List<Schedule> schedules = (new SectionPersistence()).getSchedulesForThisSection(section);
            for (Schedule schedule : schedules) {
                row = new ArrayList<>();
                row.add(section.getName());
                row.add(schedule.getDate().toString());
                row.add(String.valueOf(schedule.getStartHour()));
                row.add(String.valueOf(schedule.getEndHour()));
                vmOrganizer.getSectionsTable().get().add(row);
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
        vmOrganizer.getSectionsTable().get().add(row);
    }
}
