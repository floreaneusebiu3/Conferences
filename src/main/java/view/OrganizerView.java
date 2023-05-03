package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class OrganizerView {
    private JFrame frame;
    private JPanel initPanel;
    private Object[][] participantsData = new Object[100][4];
    private String[] participantsHead = new String[]{"PARTICIPANT", "APPROVED", "FILE", "SECTION"};
    private JTable participantsFilesTable;
    private Object[][] filteredParticipantsData = new Object[100][2];
    private String[] filteredParticipantsHead = new String[]{"PARTICIPANT", "SECTION"};
    private JTable filteredParticipantsTable;
    private Object[][] sectionsData = new Object[100][4];
    private String[] sectionsHead = new String[]{"SECTION", "DATA", "START HOUR", "END HOUR"};
    private JTable sectionsTable;
    private JTextField sectionTextField;
    private JTextField dateField;
    private JTextField startHourField;
    private JTextField endHourField;
    private JButton addScheduleButton;
    private JButton filterButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton approveButton;
    private String[] languages = {"english", "romana", "german"};
    private JComboBox<String> languageComboBox;
    private JButton seeStatistics;
    private JFrame statisticsFrame;

    public OrganizerView() {
        frame = new JFrame("Organizer");
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


        participantsFilesTable = new JTable(participantsData, participantsHead);
        JScrollPane scrollPane = new JScrollPane(participantsFilesTable);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBounds(100, 50, 1300, 300);
        initPanel.add(scrollPane);

        filteredParticipantsTable = new JTable(filteredParticipantsData, filteredParticipantsHead);
        JScrollPane scrollPane1 = new JScrollPane(filteredParticipantsTable);
        scrollPane1.setBackground(Color.GRAY);
        scrollPane1.setBackground(Color.WHITE);
        scrollPane1.setBounds(50, 430, 700, 300);
        initPanel.add(scrollPane1);

        sectionTextField = new JTextField("SECTION");
        sectionTextField.setBounds(300, 740, 300, 50);
        sectionTextField.setHorizontalAlignment(JTextField.CENTER);
        sectionTextField.setBackground(Color.black);
        sectionTextField.setForeground(Color.WHITE);
        sectionTextField.setFont(new Font("Verdana", Font.BOLD, 20));
        initPanel.add(sectionTextField);


        approveButton = new JButton(new ImageIcon("img/approve.png"));
        approveButton.setBounds(150, 750, 80, 80);
        initPanel.add(approveButton);

        filterButton = new JButton("FILTER");
        filterButton.setBounds(400, 800, 100, 50);
        filterButton.setBackground(Color.BLACK);
        filterButton.setForeground(Color.WHITE);
        initPanel.add(filterButton);

        insertButton = new JButton(new ImageIcon("img/insert.png"));
        insertButton.setBounds(1470, 30, 60, 60);
        initPanel.add(insertButton);

        deleteButton = new JButton(new ImageIcon("img/deleteIcon.jpg"));
        deleteButton.setBounds(1470, 120, 60, 60);
        initPanel.add(deleteButton);

        updateButton = new JButton(new ImageIcon("img/update-icon.png"));
        updateButton.setBounds(1470, 210, 60, 60);
        initPanel.add(updateButton);

        seeStatistics = new JButton(new ImageIcon("img/chart.png"));
        seeStatistics.setBounds(1470, 300, 60, 60);
        initPanel.add(seeStatistics);

        sectionsTable = new JTable(sectionsData, sectionsHead);
        JScrollPane scrollPane2 = new JScrollPane(sectionsTable);
        scrollPane2.setBackground(Color.GRAY);
        scrollPane2.setBackground(Color.WHITE);
        scrollPane2.setBounds(850, 430, 700, 300);
        initPanel.add(scrollPane2);

        dateField = new JTextField("DATA dd/MM/uuuu");
        dateField.setBounds(850, 740, 300, 50);
        dateField.setHorizontalAlignment(JTextField.CENTER);
        dateField.setBackground(Color.black);
        dateField.setForeground(Color.WHITE);
        dateField.setFont(new Font("Verdana", Font.BOLD, 20));
        initPanel.add(dateField);

        startHourField = new JTextField("START");
        startHourField.setBounds(1200, 740, 150, 50);
        startHourField.setHorizontalAlignment(JTextField.CENTER);
        startHourField.setBackground(Color.black);
        startHourField.setForeground(Color.WHITE);
        startHourField.setFont(new Font("Verdana", Font.BOLD, 20));
        initPanel.add(startHourField);

        endHourField = new JTextField("END");
        endHourField.setBounds(1400, 740, 150, 50);
        endHourField.setHorizontalAlignment(JTextField.CENTER);
        endHourField.setBackground(Color.black);
        endHourField.setForeground(Color.WHITE);
        endHourField.setFont(new Font("Verdana", Font.BOLD, 20));
        initPanel.add(endHourField);

        addScheduleButton = new JButton("ADD SCHEDULE");
        addScheduleButton.setBounds(1150, 800, 200, 50);
        addScheduleButton.setBackground(Color.BLACK);
        addScheduleButton.setForeground(Color.WHITE);
        initPanel.add(addScheduleButton);

        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setBounds(5, 5, 80, 30);
        initPanel.add(languageComboBox);

        statisticsFrame = new JFrame();
        statisticsFrame.setSize(900, 450);
        statisticsFrame.setLayout(null);
        statisticsFrame.setVisible(false);

        frame.add(initPanel);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}