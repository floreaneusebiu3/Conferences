package controller;

import model.User;
import model.persistence.UserPersistence;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.UUID;

public class LoginController implements Observer{
    private LoginView loginView;

    public LoginController() {
        this.loginView = new LoginView();
        addActionListeners();
        addMailFocusListener();
    }

    @Override
    public void update() {

    }

    private void addActionListeners() {
        loginView.getLoginButton().addActionListener(e -> logUser());
        loginView.getEnterAsGuestButton().addActionListener(e -> logUserAsGuest());
    }

    private void addMailFocusListener() {
        loginView.getMailField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (loginView.getMailField().getText().equals("mail:")) {
                    loginView.getMailField().setForeground(Color.black);
                    loginView.getMailField().setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (loginView.getMailField().getText().isEmpty()) {
                    loginView.getMailField().setForeground(Color.gray);
                    loginView.getMailField().setText("mail:");
                }
            }
        });
    }

    private void logUserAsGuest() {
        User user = new User();
        user.setMail(loginView.getMailField().getText());
        new ParticipantController(UUID.randomUUID().toString(), loginView.getMailField().getText());
    }

    private void logUser() {
        String userName = loginView.getUsernameTextField().getText();
        String password = loginView.getPasswordTextField().getText();
        List<User> users = (new UserPersistence()).readAll();
        User user = getRegisteredUser(users, userName, password);
        if (user != null && user.getRole() != null && user.isApproved() == true) {
            showUserInterface(user);
        } else {
            showMessage("TRY AGAIN");
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
                new ParticipantController(user.getUserId(), "registeredUser");
                loginView.getFrame().dispose();
                break;
            case "organizer":
                new OrganizerController();
                loginView.getFrame().dispose();
                break;
            case "admin":
                new AdminController();
                loginView.getFrame().dispose();
                break;
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
