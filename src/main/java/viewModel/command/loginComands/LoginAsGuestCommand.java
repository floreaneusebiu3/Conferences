package viewModel.command.loginComands;

import model.User;
import view.ParticipantView;
import viewModel.VMLogin;
import viewModel.command.Command;

public class LoginAsGuestCommand implements Command {
    private VMLogin vmLogin;

    public LoginAsGuestCommand(VMLogin vmLogin) {
        this.vmLogin = vmLogin;
    }

    @Override
    public void execute() {
        User user = new User();
        user.setMail(vmLogin.getMailField().get());
         new ParticipantView(user);
    }

}
