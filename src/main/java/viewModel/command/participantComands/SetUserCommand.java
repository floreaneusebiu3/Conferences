package viewModel.command.participantComands;

import model.User;
import viewModel.VMParticipant;

import java.util.UUID;

public class SetUserCommand{
    private VMParticipant vmParticipant;

    public SetUserCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    public void execute(User user) {
        if (user.getUserId() != null) {
            vmParticipant.setLoggedUser(user);
        } else {
            User loggedUser = new User();
            loggedUser.setMail(user.getMail());
            loggedUser.setFirstName("Guest" + UUID.randomUUID());
            vmParticipant.setLoggedUser(loggedUser);
        }
    }
}
