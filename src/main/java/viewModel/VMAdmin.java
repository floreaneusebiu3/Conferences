package viewModel;

import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import utils.MyTable;
import viewModel.command.Command;
import viewModel.command.adminCommands.AddNewUserCommand;
import viewModel.command.adminCommands.DeleteUserCommand;
import viewModel.command.adminCommands.ShowAllUsersCommand;
import viewModel.command.adminCommands.UpdateUserCommand;
import viewModel.command.organizerCommands.AddNewScheduleCommand;

import java.util.List;
@Setter
@Getter
public class VMAdmin {
    Property<Integer> selectedRowFromUsersTable;
    Property<MyTable> usersTable;
    Command showAllUsersCommand;
    Command addNewUserCommand;
    Command deleteUserCommand;
    Command updateUserCommand;

   public VMAdmin() {
       List<String> usersHead = List.of(new String[]{"Approved", "First Name", "Last Name", "age", "mail", "role", "username", "password"});
       usersTable = PropertyFactory.createProperty("usersTable", this, new MyTable(usersHead));
       selectedRowFromUsersTable = PropertyFactory.createProperty("selectedRowFromUsersTable", this, Integer.class);
       showAllUsersCommand = new ShowAllUsersCommand(this);
       addNewUserCommand = new AddNewUserCommand(this);
       deleteUserCommand = new DeleteUserCommand(this);
       updateUserCommand = new UpdateUserCommand(this);
   }
}
