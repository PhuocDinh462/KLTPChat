package AdminInterfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import com.toedter.calendar.JDateChooser;

import Server.Classes.User;
import Server.Controllers.UserController;

import java.awt.Font;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class CreateAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textFullname;
	private JTextField textAddress;
	private JTextField textUsername;
	private JTextField textEmail;
	private JPasswordField textPass;
	private JPasswordField textRePass;

	private UserController userController;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public CreateAccount(PanelManagement pm, Main main) {
		userController = new UserController();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		setBounds(0, 0, 460, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFullname = new JLabel("Họ tên");
		lblFullname.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblFullname.setBounds(50, 80, 45, 20);
		contentPane.add(lblFullname);

		JLabel lblGender = new JLabel("Giới tính");
		lblGender.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblGender.setBounds(50, 115, 60, 20);
		contentPane.add(lblGender);

		JLabel lblDob = new JLabel("Ngày sinh");
		lblDob.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblDob.setBounds(50, 150, 70, 20);
		contentPane.add(lblDob);

		JLabel lblAddress = new JLabel("Địa chỉ");
		lblAddress.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblAddress.setBounds(50, 185, 60, 20);
		contentPane.add(lblAddress);

		JLabel lblUsername = new JLabel("Tên đăng nhập");
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblUsername.setBounds(50, 220, 100, 20);
		contentPane.add(lblUsername);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblEmail.setBounds(50, 255, 45, 20);
		contentPane.add(lblEmail);

		JLabel lblPass = new JLabel("Mật khẩu");
		lblPass.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPass.setBounds(50, 290, 70, 14);
		contentPane.add(lblPass);

		JLabel lblRePass = new JLabel("Nhập lại mật khẩu");
		lblRePass.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblRePass.setBounds(50, 325, 110, 20);
		contentPane.add(lblRePass);

		JLabel lblTitle = new JLabel("Thêm tài khoản");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblTitle.setBounds(165, 30, 130, 35);
		contentPane.add(lblTitle);

		textFullname = new JTextField();
		textFullname.setFont(new Font("Dialog", Font.PLAIN, 13));
		textFullname.setBounds(170, 75, 190, 25);
		contentPane.add(textFullname);
		textFullname.setColumns(10);

		textAddress = new JTextField();
		textAddress.setFont(new Font("Dialog", Font.PLAIN, 13));
		textAddress.setColumns(10);
		textAddress.setBounds(170, 180, 190, 25);
		contentPane.add(textAddress);

		textUsername = new JTextField();
		textUsername.setFont(new Font("Dialog", Font.PLAIN, 13));
		textUsername.setColumns(10);
		textUsername.setBounds(170, 215, 190, 25);
		contentPane.add(textUsername);

		textEmail = new JTextField();
		textEmail.setFont(new Font("Dialog", Font.PLAIN, 13));
		textEmail.setColumns(10);
		textEmail.setBounds(170, 250, 190, 25);
		contentPane.add(textEmail);

		JRadioButton rdoMale = new JRadioButton("Nam");
		rdoMale.setFont(new Font("Dialog", Font.PLAIN, 13));
		rdoMale.setBounds(170, 110, 60, 25);
		contentPane.add(rdoMale);

		JRadioButton rdoFemale = new JRadioButton("Nữ");
		rdoFemale.setFont(new Font("Dialog", Font.PLAIN, 13));
		rdoFemale.setBounds(230, 110, 60, 25);
		contentPane.add(rdoFemale);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoMale);
		bg.add(rdoFemale);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(170, 145, 190, 25);
		contentPane.add(dateChooser);

		textPass = new JPasswordField();
		textPass.setBounds(170, 285, 190, 25);
		contentPane.add(textPass);

		textRePass = new JPasswordField();
		textRePass.setBounds(170, 320, 190, 25);
		contentPane.add(textRePass);

		JButton btnCreated = new JButton("Đăng kí");
		btnCreated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean auth = signUpNowButtonEventHandler(textFullname.getText(), getSelectedButtonText(bg),
						dateChooser.getDate(), textAddress.getText(), textUsername.getText(), textEmail.getText(),
						String.valueOf(textPass.getPassword()), String.valueOf(textRePass.getPassword()));

				if (auth) {
					String[] ObjButtons = { "OK" };
					JOptionPane.showOptionDialog(null, "Valid account!", "Confirmation", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, ObjButtons, ObjButtons[0]);
					main.refreshAccount();
					pm.refreshList();
				} else {
					String[] ObjButtons = { "OK" };
					JOptionPane.showOptionDialog(null, "Invalid account", "Confirmation", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, ObjButtons, ObjButtons[0]);
				}
			}
		});
		btnCreated.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnCreated.setBounds(170, 355, 90, 30);
		contentPane.add(btnCreated);

		JButton btnNewButton = new JButton("Thoát");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnNewButton.setBounds(270, 355, 90, 30);
		contentPane.add(btnNewButton);
	}

	Boolean signUpNowButtonEventHandler(String fullname, String gender, Date date, String address, String username,
			String email, String password, String repassword) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		Boolean test = patternMatches(email, regexPattern);

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
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			User newUser = new User(username, password, fullname, address, formatter.format(date), gender, email);

			if (userController.create(newUser)) {
				setVisible(false);
				return true;
			}
		}
		return false;
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

	public static boolean patternMatches(String emailAddress, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(emailAddress).matches();
	}
}
