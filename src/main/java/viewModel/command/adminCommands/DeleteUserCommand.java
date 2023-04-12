package viewModel.command.adminCommands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.VMAdmin;
import viewModel.command.Command;

import javax.swing.*;
import java.util.List;

public class DeleteUserCommand implements Command {
    private VMAdmin vmAdmin;

    public DeleteUserCommand(VMAdmin vmAdmin) {
        this.vmAdmin = vmAdmin;
    }

    @Override
    public void execute() {
        int index = vmAdmin.getSelectedRowFromUsersTable().get();
        List<User> users = (new UserPersistence()).readAll();
        if (index > users.size() || index < 0) {
            showMessage("You must select a user");
            return;
        }
        deleteUser(users, index);
    }

    private void deleteUser(List<User> users, int index) {
        User user = users.get(index);
        (new UserPersistence()).delete(user.getUserId());
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
