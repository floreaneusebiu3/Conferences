package viewModel.command;

import model.Section;
import model.persistence.SectionPersistence;
import viewModel.VMParticipant;

import java.util.List;

public class JoinSectionCommand implements Command {
    private VMParticipant vmParticipant;

    public JoinSectionCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }


    @Override
    public void execute() {
        List<Section> sectionList = (new SectionPersistence()).readAll();
        int index = vmParticipant.getSelectedRow().get();
        Section section = sectionList.get(index);
        section.setName("Sport");
        (new SectionPersistence()).update(section);
        System.out.println(sectionList.get(index).getName());
    }
}
