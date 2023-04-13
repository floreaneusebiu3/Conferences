package viewModel.command.participantComands;

import model.User;
import model.persistence.UserPersistence;
import viewModel.VMParticipant;

import java.util.List;
import java.util.Random;

public class SetUserCommand{
    private VMParticipant vmParticipant;

    public SetUserCommand(VMParticipant vmParticipant) {
        this.vmParticipant = vmParticipant;
    }

    public void execute() {
        setUser(vmParticipant.getUserIdField().get(), vmParticipant.getUserMail().get());
    }

    private void setUser(String id, String mail) {
        if (!mail.equals("registeredUser")) {
            User loggedUser = new User();
            loggedUser.setUserId(id);
            loggedUser.setRole("participant");
            loggedUser.setFirstName("user" + new Random().nextInt(1000));
            loggedUser.setLastName("user" + new Random().nextInt(1000));
            loggedUser.setUsername("user" + new Random().nextInt(1000));
            loggedUser.setPassword("password" + new Random().nextInt(1000));
            loggedUser.setMail(mail);
            loggedUser.setAge(0);
            loggedUser.setApproved(false);
            (new UserPersistence()).insert(loggedUser);
            vmParticipant.setLoggedUser(loggedUser);
            return;
        }
        List<User> users = (new UserPersistence()).readAll();
        for (User user : users) {
            if (mail != null) {
                if (user.getUserId().equals(id)) {
                    vmParticipant.setLoggedUser(user);
                    return;
                }
            }
        }
    }
}
