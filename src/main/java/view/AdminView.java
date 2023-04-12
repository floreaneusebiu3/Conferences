package view;

import net.sds.mvvm.bindings.*;
import viewModel.VMAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView {
    private JFrame frame;
    private JPanel initialPanel;
    private JLabel adminLabel;
    @BindValues({@Bind(value = "model", target = "usersTable.value", type = BindingType.TARGET_TO_SOURCE),
            @Bind(value = "selectedRow", target = "selectedRowFromUsersTable.value", type = BindingType.BI_DIRECTIONAL)})
    private JTable usersTable;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private VMAdmin vmAdmin;

    public AdminView() {
        vmAdmin = new VMAdmin();
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

        usersTable = new JTable();
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

        vmAdmin.getShowAllUsersCommand().execute();

        frame.add(initialPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            Binder.bind(this, vmAdmin);
        } catch (BindingException e) {
            e.printStackTrace();
        }

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdmin.getAddNewUserCommand().execute();
                vmAdmin.getShowAllUsersCommand().execute();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdmin.getDeleteUserCommand().execute();
                vmAdmin.getShowAllUsersCommand().execute();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdmin.getUpdateUserCommand().execute();
                vmAdmin.getShowAllUsersCommand().execute();
            }
        });
    }
}