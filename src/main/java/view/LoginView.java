package view;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Setter
@Getter
public class LoginView {
    private JFrame frame;
    private JLabel label;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JTextField mailField;
    private JButton loginButton;
    private JButton enterAsGuestButton;

    public LoginView() {
        frame = new JFrame("ConferencesLogin");
        frame.setSize(600, 300);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(68, 68, 68));

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("img/conference.png"));
            label = new JLabel(new ImageIcon(image));
            label.setBounds(20, 10, 250, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        mailField = new JTextField("mail:");
        mailField.setForeground(Color.gray);
        mailField.setBounds(50, 210, 220, 30);
        mailField.setFont(new Font("Verdana", Font.BOLD, 20));

        enterAsGuestButton = new JButton("ENTER AS GUEST");
        enterAsGuestButton.setBounds(290, 210, 230, 30);
        enterAsGuestButton.setForeground(Color.WHITE);
        enterAsGuestButton.setFont(new Font("Verdana", Font.BOLD, 20));
        enterAsGuestButton.setBackground(Color.BLACK);

        frame.add(mailField);
        frame.add(label);
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(usernameTextField);
        frame.add(passwordTextField);
        frame.add(loginButton);
        frame.add(enterAsGuestButton);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


