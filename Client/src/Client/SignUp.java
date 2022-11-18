package Client;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;


public class SignUp extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Enum: Sign Up Status
     */
    public enum SignUpStatus {
        /**
         * Waiting for response
         */
        Waiting,

        /**
         * Failed cause account are already signed up
         */
        Failed,

        /**
         * Sign up successful
         */
        Accepted
    }

    /**
     * Attribute: SignUpStatus - status
     * The status of Sign Up Request
     */
    public static SignUpStatus status;

    /**
     * Default constructor
     */
    public SignUp() {
        addComponents();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Đăng ký");
        setResizable(false);
        setVisible(true);
    }

    /**
     * Add components to Sign Up JFrame
     */
    public void addComponents() {
        // Content Pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(6, 1, 0, 5));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Back to Sign In Button
        JButton backButton = new JButton("Trở về");
        backButton.addActionListener(e -> backButtonEventHandler());
        backButton.setFocusPainted(false);

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));
        backPanel.add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Đăng ký", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));

        // Username Panel
        JTextField usernameTextField = new JTextField(15);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.add(new JLabel("Tên tài khoản"));
        usernamePanel.add(Box.createHorizontalStrut(40));
        usernamePanel.add(usernameTextField);

        // Password Panel
        JPasswordField passwordField = new JPasswordField(15);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.add(new JLabel("Mật khẩu"));
        passwordPanel.add(Box.createHorizontalStrut(66));
        passwordPanel.add(passwordField);

        // Re-Password Panel
        JPasswordField repasswordField = new JPasswordField(15);

        JPanel repasswordPanel = new JPanel();
        repasswordPanel.setLayout(new BoxLayout(repasswordPanel, BoxLayout.X_AXIS));
        repasswordPanel.add(new JLabel("Nhập lại mật khẩu"));
        repasswordPanel.add(Box.createHorizontalStrut(18));
        repasswordPanel.add(repasswordField);

        // Sign Up Now Button
        JButton signUpNowButton = new JButton("Đăng ký");
        signUpNowButton.addActionListener(e -> signUpNowButtonEventHandler(
                usernameTextField.getText(),
                Arrays.toString(passwordField.getPassword()),
                Arrays.toString(repasswordField.getPassword())));
        signUpNowButton.setFocusPainted(false);

        // Add components to Content Pane and Settings
        contentPane.add(backPanel);
        contentPane.add(titleLabel);
        contentPane.add(usernamePanel);
        contentPane.add(passwordPanel);
        contentPane.add(repasswordPanel);
        contentPane.add(signUpNowButton);

        setContentPane(contentPane);
        pack();
    }

    /**
     * Back Button Event Handler: Open Sign In JFrame
     */
    void backButtonEventHandler() {
        new SignIn();
        dispose();
    }

    /**
     * Sign Up Now Button Event Handler
     * Check for validity of information, send Sign Up Request and Display result to user
     * @param username String
     * @param password String
     * @param repassword String
     */
    void signUpNowButtonEventHandler(String username, String password, String repassword) {
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên tài khoản!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khẩu!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (repassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập lại mật khẩu!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else if (!password.equals(repassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu và mật khẩu nhập lại không trùng khớp!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            status = SignUp.SignUpStatus.Waiting;
            Main.sendMessage("Command_CreateAccount`" + username + "`" + password);
            while (status == SignUp.SignUpStatus.Waiting) System.out.print("");

            if (status == SignUp.SignUpStatus.Accepted) {
                Main.sendMessage("Command_SignedIn`" + username);
                new Main();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tên tài khoản đã tồn tại!",
                        "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
