package viewModel;

import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.command.Command;
import viewModel.command.LoginAsGuestCommand;
import viewModel.command.LoginCommand;

public class VMLogin {

    private Property<String> userField;
    private Property<String> passwordField;
    private Command loginCommand;
    private Command loginAsGuestCommand;

    public VMLogin() {
        userField = PropertyFactory.createProperty("username", this, String.class);
        passwordField = PropertyFactory.createProperty("password", this, String.class);
        loginCommand = new LoginCommand(this);
        loginAsGuestCommand = new LoginAsGuestCommand(this);
    }

    public String getUserField() {
        return userField.get();
    }

    public void setUserField(String userField) {
        this.userField.set(userField);
    }

    public String getPasswordField() {
        return passwordField.get();
    }

    public void setPasswordField(String passwordField) {
        this.passwordField.set(passwordField);
    }

    public Command getLoginCommand() {
        return loginCommand;
    }

    public Command getLoginAsGuestCommand() {
        return loginAsGuestCommand;
    }
}