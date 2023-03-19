package presenter;

import model.User;
import model.persistence.UserPersistence;
import view.*;

import java.util.List;

public class LoginPresenter {
    ILoginView loginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.loginView = iLoginView;
    }

    public void drawUserInterface(String userName, String password) {
        List<User> users = (new UserPersistence()).readAll();
        User user = getRegisteredUser(users, userName, password);
        if (user != null && user.getRole() != null && user.isApproved() == true) {
            loginView.updateOnSuccess();
            loginView.showUserInterface(user);
        } else {
            loginView.updateOnFail();
        }
    }

    private User getRegisteredUser(List<User> users, String username, String password) {
        System.out.println("username " + username);
        System.out.println("password " + password);
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
