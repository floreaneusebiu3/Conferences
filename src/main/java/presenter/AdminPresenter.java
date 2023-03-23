package presenter;

import model.User;
import model.persistence.UserPersistence;
import view.AdminView;
import view.IAdminView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminPresenter {
    private IAdminView adminView;
    private UserPersistence userPersistence;

    public AdminPresenter(IAdminView adminView) {
        userPersistence = new UserPersistence();
        this.adminView = adminView;
    }

    public void populateUsersTable() {
        List<User> users = userPersistence.readAll();
        adminView.updateUsersTable(adminView.getUsersData(), filterNotApprovedUsers(users));
        adminView.getFrame().repaint();
    }

    private List<User> filterNotApprovedUsers(List<User> users) {
        List<User> notApprovedUsers = new ArrayList<>();
        for (User user : users) {
            if(!user.isApproved()) {
                notApprovedUsers.add(user);
            }
        }
        return notApprovedUsers;
    }

    public void createUser() {
        int index = adminView.getUsersTable().getSelectedRow();
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setApproved(adminView.getUsersData()[index][0].equals("true"));
        user.setFirstName((String) adminView.getUsersData()[index][1]);
        user.setLastName((String) adminView.getUsersData()[index][2]);
        user.setAge(Integer.parseInt((String) adminView.getUsersData()[index][3]));
        user.setMail((String) adminView.getUsersData()[index][4]);
        user.setRole((String) adminView.getUsersData()[index][5]);
        user.setUsername((String) adminView.getUsersData()[index][6]);
        user.setPassword((String) adminView.getUsersData()[index][7]);
        userPersistence.insert(user);
        adminView.clearUsersTable();
        populateUsersTable();
    }

    public void deleteUser() {
        int index = adminView.getUsersTable().getSelectedRow();
        List<User> users = userPersistence.readAll();
        if (index > users.size() || index < 0) {
            adminView.showMessage("You must select a user");
            return;
        }
        User user = users.get(index);
        userPersistence.delete(user.getUserId());
        adminView.clearUsersTable();
        adminView.clearUsersTable();
        populateUsersTable();
    }

    public void updateUser() {
        int index = adminView.getUsersTable().getSelectedRow();
        List<User> users = userPersistence.readAll();
        if (index > users.size() | index < 0) {
            adminView.showMessage("You must select a user");
            return;
        }
        User editedUser = users.get(index);
        editedUser.setApproved(adminView.getUsersData()[index][0].equals("true"));
        editedUser.setFirstName((String) adminView.getUsersData()[index][1]);
        editedUser.setLastName((String) adminView.getUsersData()[index][2]);
        editedUser.setAge((Integer) adminView.getUsersData()[index][3]);
        editedUser.setMail((String) adminView.getUsersData()[index][4]);
        editedUser.setRole((String) adminView.getUsersData()[index][5]);
        editedUser.setUsername((String) adminView.getUsersData()[index][6]);
        editedUser.setPassword((String) adminView.getUsersData()[index][7]);
        userPersistence.update(editedUser);
        adminView.clearUsersTable();
        populateUsersTable();
    }
}
