package viewModel.command.adminCommands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.VMAdmin;
import viewModel.command.Command;

import java.util.UUID;

public class AddNewUserCommand implements Command {
    private VMAdmin vmAdmin;

    public AddNewUserCommand(VMAdmin vmAdmin) {
        this.vmAdmin = vmAdmin;
    }

    @Override
    public void execute() {
        int index = vmAdmin.getUsersTable().get().getRowCount() - 1;
        User user = getUser(index);
        insertUser(user);
    }

    private User getUser(int index) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setApproved(vmAdmin.getUsersTable().get().getValueAt(index, 0).equals("true"));
        user.setFirstName((String) vmAdmin.getUsersTable().get().getValueAt(index, 1));
        user.setLastName((String) vmAdmin.getUsersTable().get().getValueAt(index, 2));
        user.setAge(Integer.parseInt((String) vmAdmin.getUsersTable().get().getValueAt(index, 3)));
        user.setMail((String) vmAdmin.getUsersTable().get().getValueAt(index, 4));
        user.setRole((String) vmAdmin.getUsersTable().get().getValueAt(index, 5));
        user.setUsername((String) vmAdmin.getUsersTable().get().getValueAt(index, 6));
        user.setPassword((String) vmAdmin.getUsersTable().get().getValueAt(index, 7));
        return user;
    }

    private void insertUser(User user) {
        (new UserPersistence()).insert(user);
    }
}
