package Client;

import javax.swing.*;
import java.util.*;
import java.util.regex.Pattern;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	 * Attribute: SignUpStatus - status The status of Sign Up Request
	 */
	public static SignUpStatus status;
	private JTextField fullnameTextField;
	private JPanel contentPane;
	private JTextField addressTextField;
	private JTextField usernameTextField;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	private JPasswordField repasswordField;

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

	// check valid email
	public static boolean patternMatches(String emailAddress, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(emailAddress).matches();
	}

	public static String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}

	/**
	 * Add components to Sign Up JFrame
	 */

	public void addComponents() {
		// Content Pane
		setBounds(100, 100, 321, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton backButton = new JButton("Trở về");
		backButton.addActionListener(e -> backButtonEventHandler());
		backButton.setFocusPainted(false);
		backButton.setBounds(10, 10, 85, 21);
		contentPane.add(backButton);
		
		JLabel lblngK = new JLabel("Đăng ký", SwingConstants.CENTER);
		lblngK.setFont(new Font("Arial", Font.BOLD, 20));
		lblngK.setBounds(65, 35, 180, 24);
		contentPane.add(lblngK);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 69, 287, 1);
		contentPane.add(separator);
		
		JLabel lblHVTn = new JLabel("Họ và tên");
		lblHVTn.setFont(new Font("Arial", Font.PLAIN, 12));
		lblHVTn.setBounds(10, 89, 77, 13);
		contentPane.add(lblHVTn);
		
		fullnameTextField = new JTextField();
		fullnameTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		fullnameTextField.setColumns(10);
		fullnameTextField.setBounds(128, 84, 169, 20);
		contentPane.add(fullnameTextField);
		
		JLabel lblGiiTnh = new JLabel("Giới tính");
		lblGiiTnh.setFont(new Font("Arial", Font.PLAIN, 12));
		lblGiiTnh.setBounds(10, 121, 77, 13);
		contentPane.add(lblGiiTnh);
		
		JRadioButton radioBtn1 = new JRadioButton("Nam");
		JRadioButton radioBtn2 = new JRadioButton("Nữ");
		radioBtn1.setBounds(128, 114, 53, 22);
		radioBtn2.setBounds(183, 114, 53, 22);
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtn1);
		bg.add(radioBtn2);
		contentPane.add(radioBtn1);
		contentPane.add(radioBtn2);
		
		String gender = new String();
		if (radioBtn1.isSelected()) {
			gender = "Nam";
		} else if (radioBtn2.isSelected()) {
			gender = "Nữ";
		} else {
			gender = "";
		}
		
		JLabel lblNgySinh = new JLabel("Ngày sinh");
		lblNgySinh.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNgySinh.setBounds(10, 154, 77, 13);
		contentPane.add(lblNgySinh);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(128, 152, 172, 20);
		contentPane.add(dateChooser);
		
		addressTextField = new JTextField();
		addressTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		addressTextField.setColumns(10);
		addressTextField.setBounds(128, 196, 169, 20);
		contentPane.add(addressTextField);
		
		JLabel lblaCh = new JLabel("Địa chỉ");
		lblaCh.setFont(new Font("Arial", Font.PLAIN, 12));
		lblaCh.setBounds(10, 201, 77, 13);
		contentPane.add(lblaCh);
		
		JLabel lblTnTiKhon = new JLabel("Tên tài khoản");
		lblTnTiKhon.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTnTiKhon.setBounds(10, 245, 77, 13);
		contentPane.add(lblTnTiKhon);
		
		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		usernameTextField.setColumns(10);
		usernameTextField.setBounds(128, 240, 169, 20);
		contentPane.add(usernameTextField);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmail.setBounds(10, 289, 77, 13);
		contentPane.add(lblEmail);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		emailTextField.setColumns(10);
		emailTextField.setBounds(128, 284, 169, 20);
		contentPane.add(emailTextField);
		
		JLabel lblMtKhu = new JLabel("Mật khẩu");
		lblMtKhu.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMtKhu.setBounds(10, 333, 77, 13);
		contentPane.add(lblMtKhu);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(128, 328, 169, 19);
		contentPane.add(passwordField);
		
		repasswordField = new JPasswordField();
		repasswordField.setBounds(128, 372, 169, 19);
		contentPane.add(repasswordField);
		
		JLabel lblNhpLiMt = new JLabel("Nhập lại mật khẩu");
		lblNhpLiMt.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNhpLiMt.setBounds(10, 377, 102, 13);
		contentPane.add(lblNhpLiMt);
		
		JButton signUpNowButton = new JButton("Đăng ký");
		signUpNowButton.setBounds(10, 416, 287, 21);
		signUpNowButton.addActionListener(e -> signUpNowButtonEventHandler(fullnameTextField.getText(),
		SignUp.getSelectedButtonText(bg), dateChooser.getDate(), addressTextField.getText(),
		usernameTextField.getText(), emailTextField.getText(), String.valueOf(passwordField.getPassword()),
//        Array.toString(passwordField.getPassword()),
		String.valueOf(repasswordField.getPassword())));
signUpNowButton.setFocusPainted(false);
		contentPane.add(signUpNowButton);
	}

	/**
	 * Back Button Event Handler: Open Sign In JFrame
	 */
	void backButtonEventHandler() {
		new SignIn();
		dispose();
	}

	/**
	 * Sign Up Now Button Event Handler Check for validity of information, send Sign
	 * Up Request and Display result to user
	 * 
	 * @param username   String
	 * @param password   String
	 * @param repassword String
	 */
	void signUpNowButtonEventHandler(String fullname, String gender, Date date, String address, String username,
			String email, String password, String repassword) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		Boolean test = SignUp.patternMatches(email, regexPattern);
		
		if (fullname.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập họ tên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (gender == null) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (date == null) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập ngày sinh!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (address.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập địa chỉ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (username.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập email!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (repassword.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập lại mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (!password.equals(repassword)) {
			JOptionPane.showMessageDialog(this, "Mật khẩu và mật khẩu nhập lại không trùng khớp!", "Lỗi",
					JOptionPane.WARNING_MESSAGE);
		} else if (!test) {
			JOptionPane.showMessageDialog(this, "Email nhập không hợp lệ ", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else {
			status = SignUp.SignUpStatus.Waiting;

//            User createUser = new User(username, password, fullname, address, date.toString(),gender, email);
//            Main.sendMessage("Command_CreateAccount`" + username + "`" + password);
			Main.sendMessage("Command_CreateAccount`" + username + "`" + password + "`" + fullname + "`" + address + "`"
					+ date.toString() + "`" + gender + "`" + email);
			while (status == SignUp.SignUpStatus.Waiting)
				System.out.print("");

			if (status == SignUp.SignUpStatus.Accepted) {
				Main.sendMessage("Command_SignedIn`" + username);
				Main.username = username;
				new Main();
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}