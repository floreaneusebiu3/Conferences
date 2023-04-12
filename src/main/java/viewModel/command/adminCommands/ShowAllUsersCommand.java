package viewModel.command.adminCommands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.VMAdmin;
import viewModel.command.Command;

import java.util.ArrayList;
import java.util.List;

public class ShowAllUsersCommand implements Command {
    private VMAdmin vmAdmin;

    public ShowAllUsersCommand(VMAdmin vmAdmin) {
        this.vmAdmin = vmAdmin;
    }

    @Override
    public void execute() {
        vmAdmin.getUsersTable().get().clearData();
        List<User> allUsers = (new UserPersistence()).readAll();
        List<User> filteredUsers = getNotApprovedUsers(allUsers);
        List<String> row;
        for (User user: filteredUsers) {
            row = new ArrayList<>();
            row.add(String.valueOf(user.isApproved()));
            row.add(user.getFirstName());
            row.add(user.getLastName());
            row.add(String.valueOf(user.getAge()));
            row.add(user.getMail());
            row.add(user.getRole());
            row.add(user.getUsername());
            row.add(user.getPassword());
            vmAdmin.getUsersTable().get().add(row);
        }
        addEmptyRow();
    }

    private void addEmptyRow() {
       List<String> row = new ArrayList<>();
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        vmAdmin.getUsersTable().get().add(row);
    }

    private List<User> getNotApprovedUsers(List<User> users) {
        List<User> notApprovedUsers = new ArrayList<>();
        for (User user : users) {
            if(!user.isApproved()) {
                notApprovedUsers.add(user);
            }
        }
        return notApprovedUsers;
    }
}
