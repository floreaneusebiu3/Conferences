package controller;

import model.User;
import model.persistence.UserPersistence;
import utils.Language;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.UUID;

public class LoginController implements Observer {
    private LoginView loginView;
    private Language language;

    public LoginController() {
        this.loginView = new LoginView();
        language = new Language();
        language.attachObserver(this);
        addActionListeners();
        addMailFocusListener();
    }

    @Override
    public void update() {
        int index = language.getCurrentLanguage();
        loginView.getUsernameLabel().setText(language.getLoginUsernameTexts().get(index));
        loginView.getPasswordLabel().setText(language.getLoginPasswordTexts().get(index));
        loginView.getLoginButton().setText(language.getLoginButtonTexts().get(index));
        loginView.getEnterAsGuestButton().setText(language.getLoginEnterAsGuestButtonTexts().get(index));
    }

    private void addActionListeners() {
        loginView.getLoginButton().addActionListener(e -> logUser());
        loginView.getEnterAsGuestButton().addActionListener(e -> logUserAsGuest());
        loginView.getLanguageComboBox().addActionListener(e -> chooseLanguage());
    }

    private void chooseLanguage() {
        language.setCurrentLanguage(loginView.getLanguageComboBox().getSelectedIndex());
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
        new ParticipantController(language, UUID.randomUUID().toString(), loginView.getMailField().getText());
    }

    private void logUser() {
        String userName = loginView.getUsernameTextField().getText();
        String password = loginView.getPasswordTextField().getText();
        List<User> users = (new UserPersistence()).readAll();
        User user = getRegisteredUser(users, userName, password);
        if (user != null && user.getRole() != null && user.isApproved() == true) {
            showUserInterface(user);
        } else {
            showMessage(language.getLoginFailMessageTexts().get(loginView.getLanguageComboBox().getSelectedIndex()));
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
                new ParticipantController(language, user.getUserId(), "registeredUser");
                loginView.getFrame().dispose();
                break;
            case "organizer":
                new OrganizerController(language);
                loginView.getFrame().dispose();
                break;
            case "admin":
                new AdminController(language);
                loginView.getFrame().dispose();
                break;
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
