package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class AdminView {
    private JFrame frame;
    private JPanel initPanel;
    private JLabel adminLabel;
    private Object[][] usersData = new Object[100][8];
    private String[] usersHead = new String[]{"Approved", "First Name", "Last Name", "age", "mail", "role", "username", "password"};
    private JTable usersTable;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private String[] languages = {"english", "romana", "german"};
    private JComboBox<String> languageComboBox;

    public AdminView() {
        frame = new JFrame("Admin");
        frame.setSize(1600, 900);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(68, 68, 68));

        initPanel = new JPanel();
        initPanel.setLayout(null);
        initPanel.setBackground(new Color(68, 68, 68));
        initPanel.setBounds(0, 0, 1600, 900);

        adminLabel = new JLabel("ADMIN");
        adminLabel.setBounds(700, 50, 300, 50);
        adminLabel.setBackground(Color.BLACK);
        adminLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        initPanel.add(adminLabel);

        usersTable = new JTable(usersData, usersHead);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBounds(150, 130, 1300, 600);
        usersTable.setFont(new Font("Verdana", Font.BOLD, 10));
        initPanel.add(scrollPane);

        insertButton = new JButton(new ImageIcon("img/insert.png"));
        insertButton.setBounds(550, 750, 60, 60);
        initPanel.add(insertButton);

        deleteButton = new JButton(new ImageIcon("img/deleteIcon.jpg"));
        deleteButton.setBounds(700, 750, 60, 60);
        initPanel.add(deleteButton);

        updateButton = new JButton(new ImageIcon("img/update-icon.png"));
        updateButton.setBounds(850, 750, 60, 60);
        initPanel.add(updateButton);

        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setBounds(5, 5, 80, 30);
        initPanel.add(languageComboBox);
        frame.add(initPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}