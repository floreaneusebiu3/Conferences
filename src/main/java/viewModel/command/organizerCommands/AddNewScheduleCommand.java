package viewModel.command.organizerCommands;

import model.Schedule;
import model.Section;
import model.persistence.SchedulePersistence;
import model.persistence.SectionPersistence;
import viewModel.VMOrganizer;
import viewModel.command.Command;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class AddNewScheduleCommand implements Command {
   private VMOrganizer vmOrganizer;

    public AddNewScheduleCommand(VMOrganizer vmOrganizer) {
        this.vmOrganizer = vmOrganizer;
    }

    @Override
    public void execute() {
        int index = vmOrganizer.getSelectedRowFromSectionsTable().get();
        String sectionName = (String) vmOrganizer.getSectionsTable().get().getValueAt(index, 0);
        Section section = getSection(sectionName);
        if (section == null) {
            showMessage("YOU MUST SELECT AN EXISTING SECTION");
            return;
        }
        Schedule schedule = getSchedule(section, vmOrganizer.getDataField().get(), vmOrganizer.getStartHourField().get(), vmOrganizer.getEndHourField().get());
        if (schedule == null) {
            showMessage("YOU MUST INSERT A VALID DATE");
            return;
        }
    }

    private Schedule getSchedule(Section section, String date, String startHour, String endHour) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        LocalDate ld = LocalDate.parse ( date , f );
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setScheduleId(UUID.randomUUID().toString());
        schedule.setDate(ld);
        schedule.setStartHour(Integer.parseInt(startHour));
        schedule.setEndHour(Integer.parseInt(endHour));
        schedule.setSection(section);
        (new SchedulePersistence()).insert(schedule);
        return schedule;
    }

    private Section getSection(String name) {
        List<Section> sectionList = (new SectionPersistence()).readAll();
        for (Section section : sectionList) {
            if (section.getName().equals(name)) {
                return section;
            }
        }
        return null;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
