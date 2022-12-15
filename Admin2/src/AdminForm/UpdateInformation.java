package AdminForm;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

public class UpdateInformation extends JPanel {
	private JTextField textUsername;
	private JTextField textFullname;
	private JTextField textAddress;
	private JTextField textDOB;
	private JTextField textGender;
	private JTextField textEmail;

	/**
	 * Create the panel.
	 */
	public UpdateInformation() {
		setLayout(null);

		setBounds(0, 0, 430, 460);

		JLabel lblUsername = new JLabel("Tên đăng nhập");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsername.setBounds(63, 76, 100, 25);
		add(lblUsername);

		JLabel lblFullname = new JLabel("Họ và tên");
		lblFullname.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFullname.setBounds(63, 111, 65, 25);
		add(lblFullname);

		JLabel lblAddress = new JLabel("Địa chỉ");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 14));
		lblAddress.setBounds(63, 146, 50, 25);
		add(lblAddress);

		JLabel lblDOB = new JLabel("Ngày sinh");
		lblDOB.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDOB.setBounds(63, 181, 65, 25);
		add(lblDOB);

		JLabel lblGiiTnh = new JLabel("Giới tính");
		lblGiiTnh.setFont(new Font("Arial", Font.PLAIN, 14));
		lblGiiTnh.setBounds(63, 216, 75, 25);
		add(lblGiiTnh);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEmail.setBounds(63, 251, 50, 25);
		add(lblEmail);

		textUsername = new JTextField();
		textUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		textUsername.setBounds(173, 76, 200, 25);
		add(textUsername);
		textUsername.setColumns(10);

		textFullname = new JTextField();
		textFullname.setFont(new Font("Arial", Font.PLAIN, 13));
		textFullname.setColumns(10);
		textFullname.setBounds(173, 111, 200, 25);
		add(textFullname);

		textAddress = new JTextField();
		textAddress.setFont(new Font("Arial", Font.PLAIN, 13));
		textAddress.setColumns(10);
		textAddress.setBounds(173, 146, 200, 25);
		add(textAddress);

		textDOB = new JTextField();
		textDOB.setFont(new Font("Arial", Font.PLAIN, 13));
		textDOB.setColumns(10);
		textDOB.setBounds(173, 181, 200, 25);
		add(textDOB);

		textGender = new JTextField();
		textGender.setFont(new Font("Arial", Font.PLAIN, 13));
		textGender.setColumns(10);
		textGender.setBounds(173, 216, 200, 25);
		add(textGender);

		textEmail = new JTextField();
		textEmail.setFont(new Font("Arial", Font.PLAIN, 13));
		textEmail.setColumns(10);
		textEmail.setBounds(173, 251, 200, 25);
		add(textEmail);

		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.setBounds(223, 286, 90, 25);
		add(btnUpdate);

	}

}
