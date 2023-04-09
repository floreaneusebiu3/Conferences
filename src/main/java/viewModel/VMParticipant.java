package viewModel;

import model.MyTable;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.command.Command;
import viewModel.command.JoinSectionCommand;
import viewModel.command.ShowSectionsCommand;

import java.util.List;

public class VMParticipant {
    private Property<MyTable> sectionsTable;
    private Property<Integer> selectedRow;
    private Command showSectionsCommand;
    private Command joinSectionCommand;

    public VMParticipant() {
        List<String> head = List.of(new String[]{"SECTION", "DATA", "START HOUR", "END HOUR"});
        selectedRow = PropertyFactory.createProperty("row", this, Integer.class);
        sectionsTable = PropertyFactory.createProperty("sectionsTable", this, new MyTable(head));
        showSectionsCommand = new ShowSectionsCommand(this);
        joinSectionCommand = new JoinSectionCommand(this);
    }

    public Property<MyTable> getSectionsTable() {
        return sectionsTable;
    }

    public Property<Integer> getSelectedRow() {
        return selectedRow;
    }

    public Command getShowSectionsCommand() {
        return showSectionsCommand;
    }

    public void setShowSectionsCommand(Command showSectionsCommand) {
        this.showSectionsCommand = showSectionsCommand;
    }

    public Command getJoinSectionCommand() {
        return joinSectionCommand;
    }
}
