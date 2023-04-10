package view;

import net.sds.mvvm.bindings.*;
import viewModel.VMOrganizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class OrganizerView {
    @BindValues({@Bind(value = "model", target = "participantsFilesTable.value", type = BindingType.TARGET_TO_SOURCE),
            @Bind(value = "selectedRow", target = "selectedRowFromParticipantsFilesTable.value", type = BindingType.BI_DIRECTIONAL)})
    private JTable participantsFilesTable;
    @BindValues({@Bind(value = "model", target = "filteredParticipantsTable.value", type = BindingType.TARGET_TO_SOURCE),
            @Bind(value = "selectedRow", target = "selectedRowFromFilteredParticipantsTable.value", type = BindingType.BI_DIRECTIONAL)})
    private JTable filteredParticipantsTable;
    @BindValues({@Bind(value = "model", target = "sectionsTable.value", type = BindingType.TARGET_TO_SOURCE),
            @Bind(value = "selectedRow", target = "selectedRowFromSectionsTable.value", type = BindingType.BI_DIRECTIONAL)})
    private JTable sectionsTable;
    @Bind(value = "text", target = "sectionField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField sectionTextField;
    @Bind(value = "text", target = "dataField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField dateField;
    @Bind(value = "text", target = "startHourField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField startHourField;
    @Bind(value = "text", target = "endHourField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField endHourField;
    private JFrame frame;
    private JPanel bottomPanel;
    private JButton addScheduleButton;
    private JButton filterButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton approveButton;
    private VMOrganizer vmOrganizer;

    public OrganizerView() {
        vmOrganizer = new VMOrganizer();
        frame = new JFrame("Organizer");
        frame.setSize(1600, 900);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(68, 68, 68));

        bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(new Color(68, 68, 68));
        bottomPanel.setBounds(0, 0, 1600, 900);


        participantsFilesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(participantsFilesTable);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBounds(100, 50, 1300, 300);
        bottomPanel.add(scrollPane);

        filteredParticipantsTable = new JTable();
        JScrollPane scrollPane1 = new JScrollPane(filteredParticipantsTable);
        scrollPane1.setBackground(Color.GRAY);
        scrollPane1.setBackground(Color.WHITE);
        scrollPane1.setBounds(50, 430, 700, 300);
        bottomPanel.add(scrollPane1);

        sectionTextField = new JTextField("SECTION");
        sectionTextField.setBounds(300, 740, 300, 50);
        sectionTextField.setHorizontalAlignment(JTextField.CENTER);
        sectionTextField.setBackground(Color.black);
        sectionTextField.setForeground(Color.WHITE);
        sectionTextField.setFont(new Font("Verdana", Font.BOLD, 20));
        bottomPanel.add(sectionTextField);
        sectionTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sectionTextField.getText().equals("SECTION")) {
                    sectionTextField.setForeground(Color.WHITE);
                    sectionTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (sectionTextField.getText().isEmpty()) {
                    sectionTextField.setText("SECTION");
                }
            }
        });

        approveButton = new JButton(new ImageIcon("img/approve.png"));
        approveButton.setBounds(150, 750, 80, 80);
        bottomPanel.add(approveButton);

        filterButton = new JButton("FILTER");
        filterButton.setBounds(400, 800, 100, 50);
        filterButton.setBackground(Color.BLACK);
        filterButton.setForeground(Color.WHITE);
        bottomPanel.add(filterButton);

        insertButton = new JButton(new ImageIcon("img/insert.png"));
        insertButton.setBounds(1470, 80, 60, 60);
        bottomPanel.add(insertButton);

        deleteButton = new JButton(new ImageIcon("img/deleteIcon.jpg"));
        deleteButton.setBounds(1470, 170, 60, 60);
        bottomPanel.add(deleteButton);

        updateButton = new JButton(new ImageIcon("img/update-icon.png"));
        updateButton.setBounds(1470, 260, 60, 60);
        bottomPanel.add(updateButton);

        sectionsTable = new JTable();
        JScrollPane scrollPane2 = new JScrollPane(sectionsTable);
        scrollPane2.setBackground(Color.GRAY);
        scrollPane2.setBackground(Color.WHITE);
        scrollPane2.setBounds(850, 430, 700, 300);
        bottomPanel.add(scrollPane2);

        dateField = new JTextField("DATA");
        dateField.setBounds(850, 740, 300, 50);
        dateField.setHorizontalAlignment(JTextField.CENTER);
        dateField.setBackground(Color.black);
        dateField.setForeground(Color.WHITE);
        dateField.setFont(new Font("Verdana", Font.BOLD, 20));
        bottomPanel.add(dateField);
        dateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateField.getText().equals("DATA")) {
                    dateField.setForeground(Color.WHITE);
                    dateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dateField.getText().isEmpty()) {
                    dateField.setText("DATA");
                }
            }
        });

        startHourField = new JTextField("START");
        startHourField.setBounds(1200, 740, 150, 50);
        startHourField.setHorizontalAlignment(JTextField.CENTER);
        startHourField.setBackground(Color.black);
        startHourField.setForeground(Color.WHITE);
        startHourField.setFont(new Font("Verdana", Font.BOLD, 20));
        bottomPanel.add(startHourField);
        startHourField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (startHourField.getText().equals("START")) {
                    startHourField.setForeground(Color.WHITE);
                    startHourField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (startHourField.getText().isEmpty()) {
                    startHourField.setText("START");
                }
            }
        });

        endHourField = new JTextField("END");
        endHourField.setBounds(1400, 740, 150, 50);
        endHourField.setHorizontalAlignment(JTextField.CENTER);
        endHourField.setBackground(Color.black);
        endHourField.setForeground(Color.WHITE);
        endHourField.setFont(new Font("Verdana", Font.BOLD, 20));
        bottomPanel.add(endHourField);
        endHourField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (endHourField.getText().equals("END")) {
                    endHourField.setForeground(Color.WHITE);
                    endHourField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (endHourField.getText().isEmpty()) {
                    endHourField.setText("END");
                }
            }
        });

        addScheduleButton = new JButton("ADD SCHEDULE");
        addScheduleButton.setBounds(1150, 800, 200, 50);
        addScheduleButton.setBackground(Color.BLACK);
        addScheduleButton.setForeground(Color.WHITE);
        bottomPanel.add(addScheduleButton);

        vmOrganizer.getShowParticipantsAndFilesCommand().execute();
        frame.add(bottomPanel);
        frame.add(bottomPanel);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            Binder.bind(this, vmOrganizer);
        } catch (BindingException e) {
            e.printStackTrace();
        }

        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmOrganizer.getApproveParticipantCommand().execute();
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmOrganizer.getShowFilteredParticipantsCommand().execute();
                frame.repaint();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmOrganizer.getUpdateParticipantAndFileCommand().execute();
                vmOrganizer.getShowParticipantsAndFilesCommand().execute();
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmOrganizer.getInsertParticipantAndFileCommand().execute();
                vmOrganizer.getShowParticipantsAndFilesCommand().execute();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmOrganizer.getDeleteParticipantAndFileCommand().execute();
                vmOrganizer.getShowParticipantsAndFilesCommand().execute();
            }
        });
    }
}