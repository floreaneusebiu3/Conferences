package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class AdminView {
    private JFrame frame;
    private JPanel initialPanel;
    private JLabel adminLabel;
    private Object[][] usersData = new Object[100][8];
    private String[] usersHead = new String[]{"Approved", "First Name", "Last Name", "age", "mail", "role", "username", "password"};
    private JTable usersTable;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;

    public AdminView() {
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

        frame.add(initialPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}