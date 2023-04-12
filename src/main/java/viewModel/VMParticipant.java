package viewModel;

import lombok.Getter;
import lombok.Setter;
import utils.MyTable;
import model.User;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.command.*;
import viewModel.command.participantComands.*;

import java.util.List;

@Setter
@Getter
public class VMParticipant {
    private Property<MyTable> sectionsTable;
    private Property<Integer> selectedRow;
    private Property<MyTable> joinedSectionsTable;
    private Property<Integer> selectedRowFromJoinedSections;
    private Property<MyTable> filesTable;
    private Property<Integer> selectedRowFromFilesTable;
    private String selectedPresentationFile = null;
    private User loggedUser = null;
    private Command showSectionsCommand;
    private Command joinSectionCommand;
    private Command openFileCommand;
    private Command uploadFileCommand;
    private Command showSelectedSectionsCommand;
    private SetUserCommand setUserCommand;
    private Command updateFilesTableCommand;


    public VMParticipant() {
        selectedRow = PropertyFactory.createProperty("row", this, Integer.class);
        selectedRowFromJoinedSections = PropertyFactory.createProperty("rowFromJoined", this, Integer.class);
        selectedRowFromFilesTable = PropertyFactory.createProperty("rowFromFiles", this, Integer.class);
        List<String> head = List.of(new String[]{"SECTION", "DATA", "START HOUR", "END HOUR"});
        sectionsTable = PropertyFactory.createProperty("sectionsTable", this, new MyTable(head));
        joinedSectionsTable = PropertyFactory.createProperty("joinedSectionsTable", this, new MyTable(List.of(new String[]{"SECTION"})));
        List<String> filesHead = List.of(new String[]{"FILE", "PARTICIPANT", "SECTION"});
        filesTable = PropertyFactory.createProperty("filesTable", this, new MyTable(filesHead));
        showSectionsCommand = new ShowSectionsCommand(this);
        joinSectionCommand = new JoinSectionCommand(this);
        openFileCommand = new OpenFileCommand(this);
        uploadFileCommand = new UploadFileCommand(this);
        showSelectedSectionsCommand = new ShowSelectedSectionsCommand(this);
        setUserCommand = new SetUserCommand(this);
        updateFilesTableCommand = new UpdateFilesTable(this);
    }
}
