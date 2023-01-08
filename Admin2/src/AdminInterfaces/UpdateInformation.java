package AdminInterfaces;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

import Server.Classes.User;
import Server.Controllers.UserController;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class UpdateInformation extends JPanel {
	private JTextField textFullname;
	private JTextField textAddress;
	private JTextField textEmail;

	private UserController userController;

	private User user;

	/**
	 * Create the panel.
	 */
	public UpdateInformation(User user) {
		this.user = user;
		userController = new UserController();
		setLayout(null);

		setBounds(0, 0, 430, 460);

		JLabel lblFullname = new JLabel("Họ và tên");
		lblFullname.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFullname.setBounds(52, 78, 65, 25);
		add(lblFullname);

		JLabel lblAddress = new JLabel("Địa chỉ");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 14));
		lblAddress.setBounds(52, 113, 50, 25);
		add(lblAddress);

		JLabel lblDOB = new JLabel("Ngày sinh");
		lblDOB.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDOB.setBounds(52, 148, 65, 25);
		add(lblDOB);

		JLabel lblGiiTnh = new JLabel("Giới tính");
		lblGiiTnh.setFont(new Font("Arial", Font.PLAIN, 14));
		lblGiiTnh.setBounds(52, 183, 75, 25);
		add(lblGiiTnh);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEmail.setBounds(52, 218, 50, 25);
		add(lblEmail);

		textFullname = new JTextField(user.getInfor().getFullname());
		textFullname.setFont(new Font("Arial", Font.PLAIN, 13));
		textFullname.setColumns(10);
		textFullname.setBounds(162, 78, 200, 25);
		add(textFullname);

		textAddress = new JTextField(user.getInfor().getAddress());
		textAddress.setFont(new Font("Arial", Font.PLAIN, 13));
		textAddress.setColumns(10);
		textAddress.setBounds(162, 113, 200, 25);
		add(textAddress);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dob = null;
		try {
			dob = formatter.parse(user.getInfor().getDOB());
		} catch (ParseException e1) {

			e1.printStackTrace();
		}

		JDateChooser dateChooser = new JDateChooser(dob);
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setBounds(162, 148, 200, 25);
		add(dateChooser);

		textEmail = new JTextField(user.getInfor().getEmail());
		textEmail.setFont(new Font("Arial", Font.PLAIN, 13));
		textEmail.setColumns(10);
		textEmail.setBounds(162, 218, 200, 25);
		add(textEmail);

		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnUpdate.setBounds(214, 252, 90, 25);
		add(btnUpdate);

		String[] element = { "Nam", "Nữ", "Khác" };
		JComboBox comboBox = new JComboBox(element);
		comboBox.setFont(new Font("Arial", Font.PLAIN, 13));
		comboBox.setBounds(162, 185, 100, 25);
		add(comboBox);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailAddress = textEmail.getText().trim();
				String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
						+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

				if (patternMatches(emailAddress, regexPattern)) {
					user.getInfor().setFullname(textFullname.getText().trim());
					user.getInfor().setAddress(textAddress.getText().trim());
					user.getInfor().setDOB(formatter.format(dateChooser.getDate()));
					user.getInfor().setEmail(textEmail.getText().trim());

					String gender = new String();
					if (comboBox.getSelectedIndex() == 0)
						gender = "Nam";
					else if (comboBox.getSelectedIndex() == 1)
						gender = "Nữ";
					else
						gender = "Khác";

					user.getInfor().setGender(gender);
					userController.update(user.getInfor().getUsername(), user.getInfor());
					String[] ObjButtons = { "OK" };
					JOptionPane.showOptionDialog(null, "Cập nhật thành công!", "Xác nhận", JOptionPane.DEFAULT_OPTION,
							JOptionPane.NO_OPTION, null, ObjButtons, ObjButtons[0]);
				} else {

					String[] ObjButtons = { "OK" };
					JOptionPane.showOptionDialog(null, "Cập nhật thất bại!\nEmail của bạn không đúng", "Lỗi",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, ObjButtons, ObjButtons[0]);
				}
			}
		});
	}

	public static boolean patternMatches(String emailAddress, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(emailAddress).matches();
	}
}
