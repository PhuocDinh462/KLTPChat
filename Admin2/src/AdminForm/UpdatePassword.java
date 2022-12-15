package AdminForm;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class UpdatePassword extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public UpdatePassword() {
		setBounds(0, 0, 430, 460);
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(170, 75, 220, 25);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Mật khẩu cũ");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNewLabel.setBounds(40, 75, 85, 25);
		add(lblNewLabel);
		
		JLabel lblMtKhuMi = new JLabel("Mật khẩu mới");
		lblMtKhuMi.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblMtKhuMi.setBounds(40, 105, 90, 25);
		add(lblMtKhuMi);
		
		JLabel lblNhpLiMt = new JLabel("Nhập lại mật khẩu");
		lblNhpLiMt.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNhpLiMt.setBounds(40, 135, 120, 25);
		add(lblNhpLiMt);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(170, 105, 220, 25);
		add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(170, 135, 220, 25);
		add(textField_2);
		
		JButton btnNewButton = new JButton("Cập nhật");
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnNewButton.setBounds(170, 170, 100, 25);
		add(btnNewButton);
	}
}
