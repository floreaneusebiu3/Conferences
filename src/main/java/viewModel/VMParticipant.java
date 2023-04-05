package viewModel;

import model.MyTable;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.command.Command;
import viewModel.command.ShowSectionsCommand;

import java.util.ArrayList;
import java.util.List;


public class VMParticipant {
    private Property<MyTable> model;
    private Property<Integer> row = PropertyFactory.createProperty("row", this, Integer.class);
    private Command showSectionsCommand;

    public VMParticipant() {
        List<String> head = new ArrayList<>();
        head.add("fdsgfgdf");
        head.add("fdsgfgdf");
        head.add("fdsgfgdf");
        head.add("fdsgfgdf");
        model = PropertyFactory.createProperty("model", this, new MyTable(head));
        showSectionsCommand = new ShowSectionsCommand(this);
    }

    public Property<MyTable> getModel() {
        return model;
    }

    public void setShowSectionsCommand(Command showSectionsCommand) {
        this.showSectionsCommand = showSectionsCommand;
    }

    public Command getShowSectionsCommand() {
        return showSectionsCommand;
    }
}
