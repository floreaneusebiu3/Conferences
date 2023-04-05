package viewModel.command;

import view.ParticipantView;
import viewModel.VMLogin;

public class LoginAsGuestCommand implements Command{
    private VMLogin vmLogin;

    public LoginAsGuestCommand(VMLogin vmLogin) {
        this.vmLogin = vmLogin;
    }

    @Override
    public void execute() {
         new ParticipantView(null);
    }

}
