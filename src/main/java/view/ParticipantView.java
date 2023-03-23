package view;


import model.User;
import presenter.ParticipantPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


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
        participantPresenter = new ParticipantPresenter(this);
        participantPresenter.createUser(user);
        try {
            initParticipantView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initParticipantView() throws IOException {
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

        backButton = new JButton(new ImageIcon("img/back.png"));
        backButton.setBounds(1500, 50, 40, 40);
        backButton.setBackground(new Color(68, 68, 68));
        volumePanel.add(backButton);


        frame.add(initPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        uploadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                participantPresenter.setSelectedPresentationFile();
            }
        });

        joinSectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                participantPresenter.updateJoinedSections();
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(volumePanel);
                frame.add(initPanel);
                frame.repaint();
            }
        });
        participantPresenter.getSectionsFromDataBaseAndUpdateTable();
        participantPresenter.showAllSectionsOfThisParticipant();
        frame.repaint();

    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public JTable getFilesTable() {
        return filesTable;
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }

    @Override
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @Override
    public Object[][] getFilesData() {
        return filesData;
    }

    @Override
    public Object[][] getJoinedSectionsData() {
        return joinedSectionsData;
    }

    @Override
    public JTable getSectionsTable() {
        return sectionsTable;
    }

    @Override
    public Object[][] getSectionsData() {
        return sectionsData;
    }
}
