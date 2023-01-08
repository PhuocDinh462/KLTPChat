package AdminInterfaces;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Server.Classes.User;
import Server.Controllers.UserController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdatePassword extends JPanel {
	private JPasswordField textOldPass;
	private JPasswordField textNewPass;
	private JPasswordField textRePass;

	private UserController userController;
	private User user;

	/**
	 * Create the panel.
	 */
	public UpdatePassword(User user) {
		userController = new UserController();
		this.user = user;

		setBounds(0, 0, 430, 460);
		setLayout(null);

		JLabel lblOldPass = new JLabel("Mật khẩu cũ");
		lblOldPass.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblOldPass.setBounds(40, 75, 85, 25);
		add(lblOldPass);

		JLabel lblNewPass = new JLabel("Mật khẩu mới");
		lblNewPass.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNewPass.setBounds(40, 105, 90, 25);
		add(lblNewPass);

		JLabel lblRePass = new JLabel("Nhập lại mật khẩu");
		lblRePass.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblRePass.setBounds(40, 135, 120, 25);
		add(lblRePass);

		textOldPass = new JPasswordField();
		textOldPass.setBounds(170, 75, 220, 25);
		add(textOldPass);
		textOldPass.setColumns(10);

		textNewPass = new JPasswordField();
		textNewPass.setColumns(10);
		textNewPass.setBounds(170, 105, 220, 25);
		add(textNewPass);

		textRePass = new JPasswordField();
		textRePass.setColumns(10);
		textRePass.setBounds(170, 135, 220, 25);
		add(textRePass);

		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = new String();
				String error = "Error";
				if (!String.valueOf(textOldPass.getPassword()).equals(user.getInfor().getPassword())) {
					title = "Mật khẩu cũ của bạn không đúng!";
				} else if (String.valueOf(textNewPass.getPassword()).equals("")) {
					title = "Bạn chưa nhập mật khẩu mới!";
				} else if (!String.valueOf(textNewPass.getPassword()).equals(String.valueOf(textRePass.getPassword()))) {
					title = "Mật khẩu không trùng khớp!";
				} else {
					title = "Cập nhật mật khẩu thành công!";
					error = "";
					user.getInfor().setPassword(String.valueOf(textNewPass.getPassword()));
					userController.updatePassword(user.getInfor().getUsername(), String.valueOf(textNewPass.getPassword()));
					textOldPass.setText("");
					textNewPass.setText("");
					textRePass.setText("");
				}

				String[] ObjButtons = { "OK" };

				JOptionPane.showOptionDialog(null, title, error != "" ? error : "Xác nhận", JOptionPane.DEFAULT_OPTION,
						error != "" ? JOptionPane.ERROR_MESSAGE : JOptionPane.NO_OPTION, null, ObjButtons,
						ObjButtons[0]);
			}
		});
		btnUpdate.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnUpdate.setBounds(170, 170, 100, 25);
		add(btnUpdate);
	}
}
