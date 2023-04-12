package viewModel;

import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewModel.command.Command;
import viewModel.command.loginComands.LoginAsGuestCommand;
import viewModel.command.loginComands.LoginCommand;
@Setter
@Getter
public class VMLogin {
    private Property<String> userField;
    private Property<String> passwordField;
    private Property<String> mailField;
    private Command loginCommand;
    private Command loginAsGuestCommand;

    public VMLogin() {
        userField = PropertyFactory.createProperty("username", this, String.class);
        passwordField = PropertyFactory.createProperty("password", this, String.class);
        mailField = PropertyFactory.createProperty("mail", this, String.class);
        loginCommand = new LoginCommand(this);
        loginAsGuestCommand = new LoginAsGuestCommand(this);
    }
}