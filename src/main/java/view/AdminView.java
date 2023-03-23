package view;

import model.User;
import presenter.AdminPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminView implements IAdminView {
    private JFrame frame;
    private JPanel initialPanel;
    private JLabel adminLabel;
    private JTable usersTable;
    private Object[][] usersData = new Object[100][8];
    private String[] usersHead = {"Approved", "First Name", "Last Name", "age", "mail", "role", "username", "password"};
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private AdminPresenter adminPresenter;

    public AdminView() {
        adminPresenter = new AdminPresenter(this);
        initAdminView();
    }

    private void initAdminView() {
        frame = new JFrame("Admin");
        frame.setSize(1600, 900);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(68, 68, 68));

        initialPanel = new JPanel();
        initialPanel.setLayout(null);
        initialPanel.setBackground(new Color(68, 68, 68));
        initialPanel.setBounds(0, 0, 1600, 900);

        adminLabel = new JLabel("ADMIN");
        adminLabel.setBounds(700, 50, 200, 50);
        adminLabel.setBackground(Color.BLACK);
        adminLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        initialPanel.add(adminLabel);

        usersTable = new JTable(usersData, usersHead);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBounds(150, 130, 1300, 600);
        usersTable.setFont(new Font("Verdana", Font.BOLD, 10));
        initialPanel.add(scrollPane);

        insertButton = new JButton(new ImageIcon("img/insert.png"));
        insertButton.setBounds(550, 750, 60, 60);
        initialPanel.add(insertButton);

        deleteButton = new JButton(new ImageIcon("img/deleteIcon.jpg"));
        deleteButton.setBounds(700, 750, 60, 60);
        initialPanel.add(deleteButton);

        updateButton = new JButton(new ImageIcon("img/update-icon.png"));
        updateButton.setBounds(850, 750, 60, 60);
        initialPanel.add(updateButton);

        adminPresenter.populateUsersTable();

        frame.add(initialPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.createUser();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.deleteUser();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.updateUser();
            }
        });
    }

    @Override
    public void updateUsersTable(Object[][] data, List<User> users) {
        int index = 0;
        for (User user : users) {
            usersData[index][0] = user.isApproved();
            usersData[index][1] = user.getFirstName();
            usersData[index][2] = user.getLastName();
            usersData[index][3] = user.getAge();
            usersData[index][4] = user.getMail();
            usersData[index][5] = user.getRole();
            usersData[index][6] = user.getUsername();
            usersData[index++][7] = user.getPassword();
        }
    }

    @Override
    public void clearUsersTable() {
        for (int index = 0; index < 100; index++) {
            usersData[index][0] = "";
            usersData[index][1] = "";
            usersData[index][2] = "";
            usersData[index][3] = "";
            usersData[index][4] = "";
            usersData[index][5] = "";
            usersData[index][6] = "";
            usersData[index][7] = "";
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public Object[][] getUsersData() {
        return usersData;
    }

    @Override
    public JTable getUsersTable() {
        return usersTable;
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }
}
