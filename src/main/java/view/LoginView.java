package view;

import model.User;
import presenter.LoginPresenter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginView implements ILoginView{
    private JFrame frame;
    private JLabel label;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton enterAsGuestButton;
    private LoginPresenter loginPresenter;

    public LoginView() throws IOException{
        loginPresenter = new LoginPresenter(this);
        init();
    }

    @Override
    public void updateOnSuccess() {
        JOptionPane.showMessageDialog(frame, "Successfully logged in!");
    }

    @Override
    public void updateOnFail() {
        JOptionPane.showMessageDialog(frame, "The account does not exist. You can enter as guest!");
    }

    @Override
    public void showUserInterface(User user) {
        switch (user.getRole()) {
            case "participant":
                frame.dispose();
                new ParticipantView(user);
                break;
            case "organizer":
                frame.dispose();
                new OrganizerView();
                break;
            case "admin":
                frame.dispose();
                new AdminView();
                break;
        }
    }

    private void init() throws IOException {
        frame = new JFrame("ConferencesLogin");
        frame.setSize(600, 300);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(68, 68,68));

        BufferedImage image = ImageIO.read(new File("img/conference.png"));
        label = new JLabel(new ImageIcon(image));
        label.setBounds(20, 10, 250, 200);

        usernameLabel = new JLabel("username:");
        usernameLabel.setBounds(280, 50, 120, 30);
        usernameLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        usernameLabel.setForeground(Color.black);

        passwordLabel = new JLabel("password:");
        passwordLabel.setBounds(280, 90, 120, 30);
        passwordLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        passwordLabel.setForeground(Color.black);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(405, 50, 170, 30);
        usernameTextField.setFont(new Font("Verdana", Font.BOLD, 20));

        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(405, 90, 170, 30);
        passwordTextField.setFont(new Font("Verdana", Font.BOLD, 20));

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(350, 140, 120, 30);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Verdana", Font.BOLD, 20));
        loginButton.setBackground(Color.BLACK);

        enterAsGuestButton = new JButton("ENTER AS GUEST");
        enterAsGuestButton.setBounds(290, 190, 230, 30);
        enterAsGuestButton.setForeground(Color.WHITE);
        enterAsGuestButton.setFont(new Font("Verdana", Font.BOLD, 20));
        enterAsGuestButton.setBackground(Color.BLACK);

        frame.add(label);
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(usernameTextField);
        frame.add(passwordTextField);
        frame.add(loginButton);
        frame.add(enterAsGuestButton);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPresenter.checkUserIsRegistered(usernameTextField.getText(), passwordTextField.getText());
            }
        });
        enterAsGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ParticipantView(null);
            }
        });
    }
}
