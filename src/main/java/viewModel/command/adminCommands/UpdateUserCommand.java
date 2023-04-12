package viewModel.command.adminCommands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.VMAdmin;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;

public class UpdateUserCommand implements Command {
    private VMAdmin vmAdmin;

    public UpdateUserCommand(VMAdmin vmAdmin) {
        this.vmAdmin = vmAdmin;
    }

    @Override
    public void execute() {
        int index = vmAdmin.getSelectedRowFromUsersTable().get();
        List<User> users = (new UserPersistence()).readAll();
        if (index > users.size() | index < 0) {
            showMessage("You must select a user");
            return;
        }
        User editedUser = users.get(index);
        updateUser(index, editedUser);
    }

    private void updateUser(int index, User editedUser) {
        editedUser.setApproved(vmAdmin.getUsersTable().get().getValueAt(index, 0).equals("true"));
        editedUser.setFirstName((String) vmAdmin.getUsersTable().get().getValueAt(index, 1));
        editedUser.setLastName((String) vmAdmin.getUsersTable().get().getValueAt(index, 2));
        editedUser.setAge(Integer.parseInt((String) vmAdmin.getUsersTable().get().getValueAt(index, 3)));
        editedUser.setMail((String) vmAdmin.getUsersTable().get().getValueAt(index, 4));
        editedUser.setRole((String) vmAdmin.getUsersTable().get().getValueAt(index, 5));
        editedUser.setUsername((String) vmAdmin.getUsersTable().get().getValueAt(index, 6));
        editedUser.setPassword((String) vmAdmin.getUsersTable().get().getValueAt(index, 7));
        (new UserPersistence()).update(editedUser);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
