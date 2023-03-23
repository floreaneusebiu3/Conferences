package view;

import model.Participant;
import model.PresentationFile;
import model.persistence.PresentationFilePersistence;
import presenter.OrganizerPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class OrganizerView implements IOrganizerView {
    private JFrame frame;
    private JPanel initialPanel;
    private JTable participantsFilesTable;
    private String[] participantsFilesHead = {"PARTICIPANT", "REGISTERED", "FILE", "SECTION"};
    private Object[][] participantsFilesData = new Object[100][4];
    private JTable filteredParticipants;
    private String[] filteredParticipantsHead = {"PARTICIPANT", "SECTION",};
    private Object[][] filteredParticipantsData = new Object[100][3];
    private JTextField sectionTextField;
    private JButton filterButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private OrganizerPresenter organizerPresenter;

    public OrganizerView() {
        this.organizerPresenter = new OrganizerPresenter(this);
        initOrganizerView();
    }

    private void initOrganizerView() {
        frame = new JFrame("Organizer");
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

        participantsFilesTable = new JTable(participantsFilesData, participantsFilesHead);
        JScrollPane scrollPane = new JScrollPane(participantsFilesTable);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setForeground(Color.WHITE);
        participantsFilesTable.setFont(new Font("Verdana", Font.BOLD, 10));
        scrollPane.setBounds(100, 50, 1400, 300);
        initialPanel.add(scrollPane);

        filteredParticipants = new JTable(filteredParticipantsData, filteredParticipantsHead);
        JScrollPane scrollPane1 = new JScrollPane(filteredParticipants);
        scrollPane1.setBackground(Color.GRAY);
        scrollPane1.setBackground(Color.WHITE);
        scrollPane1.setBounds(100, 500, 800, 300);
        initialPanel.add(scrollPane1);

        sectionTextField = new JTextField("SECTION");
        sectionTextField.setBounds(1100, 550, 300, 50);
        sectionTextField.setHorizontalAlignment(JTextField.CENTER);
        sectionTextField.setBackground(Color.black);
        sectionTextField.setForeground(Color.WHITE);
        sectionTextField.setFont(new Font("Verdana", Font.BOLD, 20));
        initialPanel.add(sectionTextField);
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

        filterButton = new JButton("FILTER");
        filterButton.setBounds(1200, 620, 100, 50);
        filterButton.setBackground(Color.BLACK);
        filterButton.setForeground(Color.WHITE);
        initialPanel.add(filterButton);

        insertButton = new JButton(new ImageIcon("img/insert.png"));
        insertButton.setBounds(600, 400, 60, 60);
        initialPanel.add(insertButton);

        deleteButton = new JButton(new ImageIcon("img/deleteIcon.jpg"));
        deleteButton.setBounds(720, 400, 60, 60);
        initialPanel.add(deleteButton);

        updateButton = new JButton(new ImageIcon("img/update-icon.png"));
        updateButton.setBounds(840, 400, 60, 60);
        initialPanel.add(updateButton);

        organizerPresenter.updateParticipantsFilesTable();
        frame.add(initialPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                organizerPresenter.updateFilteredTable();
                frame.repaint();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                organizerPresenter.updateParticipantAndFile();
                organizerPresenter.updateParticipantsFilesTable();
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                organizerPresenter.insertParticipantAndFile();
                organizerPresenter.updateParticipantsFilesTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                organizerPresenter.deleteParticipantAndFile();
                organizerPresenter.updateParticipantsFilesTable();
            }
        });

    }

    @Override
    public void updateParticipantsFilesTable(Object[][] data, List<Participant> participants, List<PresentationFile> presentationFiles) {
        int index = 0;
        for (Participant participant : participants) {
            data[index][0] = participant.getName();
            data[index++][1] = participant.isRegistered();
            for (PresentationFile presentationFile : (new PresentationFilePersistence()).getPresentationsFileForThisParticipant(participant)) {
                data[index][2] = presentationFile.getFileAddress();
                data[index++][3] = presentationFile.getSection().getName();
            }
        }
        frame.repaint();
    }

    @Override
    public void updateFilteredParticipantsTable(Object[][] data, List<Participant> participants) {
        int index = 0;
        for (Participant participant : participants) {
            data[index][0] = participant.getName();
            data[index++][1] = sectionTextField.getText();
        }
    }

    @Override
    public Object[][] getParticipantsFilesData() {
        return participantsFilesData;
    }

    @Override
    public JTextField getSectionTextField() {
        return sectionTextField;
    }

    @Override
    public Object[][] getFilteredParticipantsData() {
        return filteredParticipantsData;
    }

    @Override
    public void clearFilteredTable() {
        for (int i = 0; i < 100; i++) {
            filteredParticipantsData[i][0] = "";
            filteredParticipantsData[i][1] = "";
        }
    }

    @Override
    public void clearParticipantsFilesTable() {
        for (int i = 0; i < 100; i++) {
            participantsFilesData[i][0] = "";
            participantsFilesData[i][1] = "";
            participantsFilesData[i][2] = "";
            participantsFilesData[i][3] = "";
        }
    }

    @Override
    public JTable getParticipantsFilesTable() {
        return participantsFilesTable;
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
