package viewModel;

import lombok.Getter;
import lombok.Setter;
import utils.MyTable;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.command.Command;
import viewModel.command.organizerCommands.*;

import java.util.List;

@Setter
@Getter
public class VMOrganizer {
    private Property<String> sectionField;
    private Property<String> dataField;
    private Property<String> startHourField;
    private Property<String> endHourField;
    private Property<MyTable> participantsFilesTable;
    private Property<MyTable> filteredParticipantsTable;
    private Property<MyTable> sectionsTable;
    private Property<Integer> selectedRowFromParticipantsFilesTable;
    private Property<Integer> selectedRowFromFilteredParticipantsTable;
    private Property<Integer> selectedRowFromSectionsTable;
    Command showParticipantsAndFilesCommand;
    Command showFilteredParticipantsCommand;
    Command updateParticipantAndFileCommand;
    Command insertParticipantAndFileCommand;
    Command deleteParticipantAndFileCommand;
    Command approveParticipantCommand;

    public VMOrganizer() {
        selectedRowFromParticipantsFilesTable = PropertyFactory.createProperty("selectedRowFromParticipantsFilesTable", this, Integer.class);
        selectedRowFromFilteredParticipantsTable = PropertyFactory.createProperty("selectedRowFromFilteredParticipantsTable", this, Integer.class);
        selectedRowFromSectionsTable = PropertyFactory.createProperty("selectedRowFromSectionsTable", this, Integer.class);
        List<String> participantsFilesHead = List.of(new String[]{"PARTICIPANT", "APPROVED", "FILE", "SECTION"});
        participantsFilesTable = PropertyFactory.createProperty("participantsFilesTable", this, new MyTable(participantsFilesHead));
        List<String> filteredParticipantsHead = List.of(new String[]{"PARTICIPANT", "SECTION"});
        filteredParticipantsTable = PropertyFactory.createProperty("filteredParticipantsTable", this, new MyTable(filteredParticipantsHead));
        List<String> sectionsHead = List.of(new String[]{"SECTION", "DATA", "START HOUR", "END HOUR"});
        sectionsTable = PropertyFactory.createProperty("sectionsTable", this, new MyTable(sectionsHead));
        sectionField = PropertyFactory.createProperty("sectionField", this, String.class);
        dataField = PropertyFactory.createProperty("dataField", this, String.class);
        startHourField = PropertyFactory.createProperty("startHourField", this, String.class);
        endHourField = PropertyFactory.createProperty("endHourField", this, String.class);
        showParticipantsAndFilesCommand = new showParticipantAndFilesCommand(this);
        showFilteredParticipantsCommand = new ShowFilteredParticipantsCommand(this);
        updateParticipantAndFileCommand = new UpdateParticipantAndFileCommand(this);
        insertParticipantAndFileCommand = new InsertParticipantAndFile(this);
        deleteParticipantAndFileCommand = new DeleteParticipantAndFileCommand(this);
        approveParticipantCommand = new ApproveParticipantCommand(this);
    }

}
