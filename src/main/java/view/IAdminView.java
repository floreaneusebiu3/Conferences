package view;

import model.User;

import javax.swing.*;
import java.util.List;

public interface IAdminView {
    void updateUsersTable(Object[][] data, List<User> users);

    Object[][] getUsersData();

    JTable getUsersTable();

    void clearUsersTable();

    JFrame getFrame();

    void showMessage(String message);


}
