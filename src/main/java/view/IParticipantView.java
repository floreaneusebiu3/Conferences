package view;
import model.User;
import javax.swing.*;

public interface IParticipantView {
    void showMessage(String message);

    JFrame getFrame();

    JTable getSectionsTable();

    JTable getFilesTable();

    Object[][] getFilesData();

    Object[][] getJoinedSectionsData();

    Object[][] getSectionsData();

    User getLoggedUser();

    void setLoggedUser(User loggedUser);


}