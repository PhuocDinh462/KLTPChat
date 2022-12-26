package Client;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignIn extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Enum: SignInStatus
	 */
	public enum SignInStatus {
		/**
		 * Waiting for response
		 */
		Waiting,

		/**
		 * Failed cause Wrong information
		 */
		Failed,

		/**
		 * Failed cause Account are already signed in another device
		 */
		Already,

		/**
		 * Sign in successful
		 */
		Accepted,

		/**
		 * Account has been blocked
		 */
		Blocked
	}

	/**
	 * @Attribute: SignInStatus - status The status of Sign In Request
	 */
	public static SignInStatus status;

	/**
	 * @Enum: ForgotPasswordStatus
	 */
	public enum ForgotPasswordStatus {
		/**
		 * Waiting for response
		 */
		Waiting,

		/**
		 * Failed cause Wrong information
		 */
		Failed,

		/**
		 * Successful
		 */
		Successful,

		/**
		 * Invalid email
		 */
		Invalid,
	}

	/**
	 * @Attribute: ForgotPasswordStatus - forgotPasswordStatus The status of forgot
	 *             password Request
	 */
	public static ForgotPasswordStatus forgotPasswordStatus;

	/**
	 * Default Constructor
	 */
	public SignIn() {
		addComponents();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Đăng nhập");
		setResizable(false);
		setVisible(true);
	}

	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordField;

	/**
	 * Add components to SignIn JFrame
	 */
	public void addComponents() {
		// Content Pane
		setBounds(100, 100, 289, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Title Label
		JLabel titleLabel = new JLabel("KLTP Chat", SwingConstants.CENTER);
		titleLabel.setBounds(47, 15, 180, 24);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

		// Add components to Content Pane and Settings
		contentPane.add(titleLabel);

		JLabel lblNewLabel = new JLabel("Tên đăng nhập");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 71, 90, 13);
		contentPane.add(lblNewLabel);

		JLabel lblMtKhu = new JLabel("Mật khẩu");
		lblMtKhu.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMtKhu.setBounds(10, 107, 90, 13);
		contentPane.add(lblMtKhu);

		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		usernameTextField.setBounds(110, 65, 155, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 48, 255, 2);
		contentPane.add(separator);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordField.setBounds(110, 102, 155, 20);
		contentPane.add(passwordField);

		JButton signInBtn = new JButton("Đăng nhập");
		signInBtn.setFont(new Font("Arial", Font.BOLD, 12));
		signInBtn.setBounds(10, 138, 255, 25);
		getRootPane().setDefaultButton(signInBtn);
		signInBtn.addActionListener(
				e -> signInBtnEventHandler(usernameTextField.getText(), String.valueOf((passwordField.getPassword()))));
		signInBtn.setFocusPainted(false);
		contentPane.add(signInBtn);

		JButton signUpBtn = new JButton("Đăng ký");
		signUpBtn.setFont(new Font("Arial", Font.BOLD, 12));
		signUpBtn.setBounds(143, 175, 122, 25);
		signUpBtn.addActionListener(e -> signUpBtnEventHandler());
		signUpBtn.setFocusPainted(false);
		contentPane.add(signUpBtn);

		JButton forgotPasswordBtn = new JButton("Quên mật khẩu");
		forgotPasswordBtn.addActionListener(e -> forgotPasswordBtnEventHandler());
		forgotPasswordBtn.setFont(new Font("Arial", Font.BOLD, 12));
		forgotPasswordBtn.setBounds(10, 175, 122, 25);
		contentPane.add(forgotPasswordBtn);
	}
	
	
	/**
	 * Forgot password Button Event Handler
	 * 
	 */
	void forgotPasswordBtnEventHandler() {
		JTextField username = new JTextField();
		Object[] object = { "Nhập tên tài khoản của bạn:", username };

		int option = JOptionPane.showConfirmDialog(null, object, "Quên mật khẩu", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			if (username.getText().isBlank()) {
				JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}

			else {
				forgotPasswordStatus = ForgotPasswordStatus.Waiting;
				Main.sendMessage("Command_ForgotPassword`" + username.getText());
				while (forgotPasswordStatus == ForgotPasswordStatus.Waiting)
					System.out.print("");

				if (forgotPasswordStatus == ForgotPasswordStatus.Successful) {
					JOptionPane.showMessageDialog(this,
							"Mật khẩu mới đã được gửi đến email của bạn. Vui lòng kiểm tra email.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (forgotPasswordStatus == ForgotPasswordStatus.Failed) {
					JOptionPane.showMessageDialog(this, "Tên tài khoản không tồn tại!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else if (forgotPasswordStatus == ForgotPasswordStatus.Invalid) {
					JOptionPane.showMessageDialog(this, "Email của tài khoản này không hợp lệ!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Sign In Button Event Handler Check for validity of information, send Sign In
	 * Request and Display result to user
	 * 
	 * @param username String
	 * @param password String
	 */
	void signInBtnEventHandler(String username, String password) {
		if (username.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else {
			status = SignInStatus.Waiting;
			Main.sendMessage("Command_AccountVerify`" + username + "`" + password);
			while (status == SignInStatus.Waiting)
				System.out.print("");

			if (status == SignInStatus.Accepted) {
				Main.username = username;
				Main.sendMessage("Command_SignedIn`" + username);
				new Main();
				dispose();
			} else if (status == SignInStatus.Already) {
				JOptionPane.showMessageDialog(this, "Tài khoản đang được đăng nhập trên thiết bị khác!", "Lỗi",
						JOptionPane.WARNING_MESSAGE);
			} else if (status == SignInStatus.Blocked) {
				JOptionPane.showMessageDialog(this, "Tài khoản đã bị khóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.", "Lỗi",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Sign Up Now Button Event Handler: Open SignUp JFrame
	 */
	void signUpBtnEventHandler() {
		new SignUp();
		dispose();
	}
}
