package viewModel.command.loginComands;

import model.User;
import view.ParticipantView;
import viewModel.VMLogin;
import viewModel.command.Command;

import java.util.UUID;

public class LoginAsGuestCommand implements Command {
    private VMLogin vmLogin;

    public LoginAsGuestCommand(VMLogin vmLogin) {
        this.vmLogin = vmLogin;
    }

    @Override
    public void execute() {
        User user = new User();
        user.setMail(vmLogin.getMailField().get());
         new ParticipantView(UUID.randomUUID().toString(), vmLogin.getMailField().get());
    }

}
