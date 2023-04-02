package presenter;

import model.User;
import model.persistence.UserPersistence;
import view.*;

import java.util.List;

public class LoginPresenter {
    private ILoginView loginView;
    private UserPersistence userPersistence;

    public LoginPresenter(ILoginView iLoginView) {
        this.loginView = iLoginView;
        this.userPersistence = new UserPersistence();
    }

    public void checkUserIsRegistered(String userName, String password) {
        List<User> users = userPersistence.readAll();
        User user = getRegisteredUser(users, userName, password);
        if (user != null && user.getRole() != null && user.isApproved() == true) {
            loginView.updateOnSuccess();
            chooseUserInterface(user);
        } else {
            loginView.updateOnFail();
        }
    }

    public void chooseUserInterface(User user) {
        switch (user.getRole()) {
            case "participant":
                loginView.getFrame().dispose();
                new ParticipantView(user);
                break;
            case "organizer":
                loginView.getFrame().dispose();
                new OrganizerView();
                break;
            case "admin":
                loginView.getFrame().dispose();
                new AdminView();
                break;
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
}
