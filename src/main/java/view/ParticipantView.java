package view;

import model.PresentationFile;
import model.Section;
import model.User;
import presenter.ParticipantPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ParticipantView implements IParticipantView {
    private JFrame frame;
    private ParticipantPresenter participantPresenter;
    private JPanel initPanel;
    private JTable sectionsTable;
    private String[] sectionsHead = {"SECTION", "DATA", "START HOUR", "END HOUR"};
    private Object[][] sectionsData = new Object[100][4];
    private JTable joinedSectionsTable;
    private Object[][] joinedSectionsData = new Object[100][4];
    private JLabel sectionsTableTitle;
    private JLabel joinedSectionsTitle;
    private JLabel registeredCondition;
    private JButton joinSectionButton;
    private JButton seeConferenceVolumeButton;
    private JButton uploadFileButton;
    private Icon icon;
    private User loggedUser;

    private JTable filesTable;
    private Object[][] filesData = new Object[100][3];
    private String[] filesHead = {"FILE", "PARTICIPANT", "SECTION"};
    private JPanel volumePanel;
    private JButton backButton;
    private JButton openFileButton;

    public ParticipantView(User user) {
        if (user != null) {
            this.loggedUser = user;
        } else {
            this.loggedUser = new User();
            this.loggedUser.setFirstName("Guest" + UUID.randomUUID());
        }

        participantPresenter = new ParticipantPresenter(this);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        frame = new JFrame("Participant");
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

        volumePanel = new JPanel();
        volumePanel.setLayout(null);
        volumePanel.setBackground(new Color(68, 68, 68));
        volumePanel.setBounds(0, 0, 1600, 900);

        sectionsTableTitle = new JLabel("Sections");
        sectionsTableTitle.setBounds(330, 20, 300, 80);
        sectionsTableTitle.setForeground(Color.WHITE);
        sectionsTableTitle.setFont(new Font("Verdana", Font.BOLD, 30));
        initPanel.add(sectionsTableTitle);

        joinedSectionsTitle = new JLabel("Joined Sections");
        joinedSectionsTitle.setBounds(1030, 20, 400, 80);
        joinedSectionsTitle.setForeground(Color.WHITE);
        joinedSectionsTitle.setFont(new Font("Verdana", Font.BOLD, 30));
        initPanel.add(joinedSectionsTitle);

        sectionsTable = new JTable(sectionsData, sectionsHead);
        JScrollPane scrollPane = new JScrollPane(sectionsTable);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBounds(50, 100, 700, 600);
        initPanel.add(scrollPane);

        joinedSectionsTable = new JTable(joinedSectionsData, sectionsHead);
        JScrollPane scrollPane1 = new JScrollPane(joinedSectionsTable);
        scrollPane1.setBounds(800, 100, 700, 600);
        initPanel.add(scrollPane1);

        joinSectionButton = new JButton("JOIN SECTION");
        joinSectionButton.setBounds(280, 720, 250, 50);
        joinSectionButton.setForeground(Color.WHITE);
        joinSectionButton.setBackground(Color.BLACK);
        joinSectionButton.setFont(new Font("Verdana", Font.BOLD, 20));
        initPanel.add(joinSectionButton);

        seeConferenceVolumeButton = new JButton("SHOW CONFERENCE VOLUME");
        seeConferenceVolumeButton.setBounds(980, 720, 400, 50);
        seeConferenceVolumeButton.setForeground(Color.WHITE);
        seeConferenceVolumeButton.setBackground(Color.BLACK);
        seeConferenceVolumeButton.setFont(new Font("Verdana", Font.BOLD, 20));
        initPanel.add(seeConferenceVolumeButton);

        registeredCondition = new JLabel("(only registered participants)");
        registeredCondition.setBounds(1060, 760, 300, 40);
        registeredCondition.setForeground(Color.WHITE);
        registeredCondition.setFont(new Font("Verdana", Font.BOLD, 15));
        initPanel.add(registeredCondition);

        icon = new ImageIcon("img/upload.png");
        uploadFileButton = new JButton(icon);
        uploadFileButton.setBackground(new Color(68, 68, 68));
        uploadFileButton.setBounds(210, 720, 50, 50);
        initPanel.add(uploadFileButton);

        filesTable = new JTable(filesData, filesHead);
        JScrollPane scrollPane2 = new JScrollPane(filesTable);
        scrollPane2.setBounds(100, 50, 1300, 700);
        volumePanel.add(scrollPane2);

        openFileButton = new JButton("OPEN FILE");
        openFileButton.setBounds(650, 790, 200, 50);
        openFileButton.setForeground(Color.WHITE);
        openFileButton.setBackground(Color.BLACK);
        openFileButton.setFont(new Font("Verdana", Font.BOLD, 20));
        volumePanel.add(openFileButton);


        frame.add(initPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        uploadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExplorer();
            }
        });

        joinSectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateJoinedSections();
            }
        });
        seeConferenceVolumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loggedUser.getUserId() != null) {
                    frame.remove(initPanel);
                    frame.add(volumePanel);
                    participantPresenter.updateFilesTable();
                    frame.repaint();
                }
            }
        });

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                participantPresenter.openFile();
            }
        });
        updateSections();
        showAllSectionsOfThisParticipant();
    }

    @Override
    public void updateSections() {
        participantPresenter.getSectionsFromDataBaseAndUpdateTable();
    }

    @Override
    public void updateJoinedSections() {
        List<Section> sectionList = participantPresenter.getSectionsFromDataBase();
        if (sectionsTable.getSelectedRow() >= sectionList.size() || sectionsTable.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(frame, "You must select a section");
            return;
        }
        participantPresenter.addParticipantToSection(loggedUser, sectionList.get(sectionsTable.getSelectedRow()).getSectionId());
    }

    @Override
    public void showExplorer() {
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\flore\\Desktop\\Conferences\\files");
        int opened = jFileChooser.showSaveDialog(null);
        if (opened == JFileChooser.APPROVE_OPTION) {
            participantPresenter.setSelectedPresentationFile(jFileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    @Override
    public void showMessageMustSelectAFile() {
        JOptionPane.showMessageDialog(frame, "You have to upload a file before join a section of this conference");
    }

    @Override
    public void showAllSectionsOfThisParticipant() {
        List<Section> sectionList = participantPresenter.getAllSectionsOfThisUser(loggedUser);
        int index = 0;
        for (Section section : sectionList) {
            joinedSectionsData[index][0] = section.getName();
            joinedSectionsData[index][1] = section.getSchedule().getDate();
            joinedSectionsData[index][2] = section.getSchedule().getStartHour();
            joinedSectionsData[index++][3] = section.getSchedule().getEndHour();
        }
        frame.getContentPane().repaint();
    }

    @Override
    public void showMessageAlreadyJoinedThisSection() {
        JOptionPane.showMessageDialog(frame, "You have already joined this section");
    }

    @Override
    public void updateSectionsTable(List<Section> sectionList) {
        int index = 0;
        for (Section section : sectionList) {
            sectionsData[index][0] = section.getName();
            sectionsData[index][1] = section.getSchedule().getDate();
            sectionsData[index][2] = section.getSchedule().getStartHour();
            sectionsData[index++][3] = section.getSchedule().getEndHour();
        }
        frame.getContentPane().repaint();
    }

    @Override
    public void showAllFiles(List<PresentationFile> presentationFiles) {
        int index = 0;
        for (PresentationFile presentationFile : presentationFiles) {
            filesData[index][0] = presentationFile.getFileAddress();
            filesData[index][1] = presentationFile.getParticipant().getName();
            filesData[index++][2] = presentationFile.getSection().getName();
        }
    }

    @Override
    public void showMessageMustSelectAFileToBeOpened() {
        JOptionPane.showMessageDialog(frame, "You must select a file");
    }

    @Override
    public JTable getFilesTable() {
        return filesTable;
    }
}
