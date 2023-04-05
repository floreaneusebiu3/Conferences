package viewModel.command;

import model.User;
import model.persistence.UserPersistence;
import view.AdminView;
import view.OrganizerView;
import view.ParticipantView;
import viewModel.VMLogin;

import java.util.List;

public class LoginCommand implements Command{

    private VMLogin vmLogin;

    public LoginCommand(VMLogin vmLogin) {
        this.vmLogin = vmLogin;
    }

    @Override
    public void execute() {
        String userName = vmLogin.getUserField();
        String password = vmLogin.getPasswordField();
        List<User> users = (new UserPersistence()).readAll();
        User user = getRegisteredUser(users, userName, password);
        if (user != null && user.getRole() != null && user.isApproved() == true) {
            showUserInterface(user);
        }
    }

    private User getRegisteredUser(List<User> users, String username, String password) {
        for (User user : users) {
            if (isValidUser(user) && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private boolean isValidUser(User user) {
        return user.getUsername() != null && user.getPassword() != null;
    }

    private void showUserInterface(User user) {
        switch (user.getRole()) {
            case "participant":
                new ParticipantView(user);
                break;
            case "organizer":
                new OrganizerView();
                break;
            case "admin":
                new AdminView();
                break;
        }
    }
}
