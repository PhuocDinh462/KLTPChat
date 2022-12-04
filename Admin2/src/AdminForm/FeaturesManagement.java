package AdminForm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FeaturesManagement extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnExit;

	public FeaturesManagement() {
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 625, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 170, 460);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JList listFeatures = new JList();
		listFeatures.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JList lst = (JList) e.getSource();
			}
		});
		listFeatures.setBackground(Color.LIGHT_GRAY);
		listFeatures.setFont(new Font("Arial", Font.PLAIN, 18));
		listFeatures.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFeatures.setModel(new AbstractListModel() {
			String[] values = new String[] {"Cập nhật tài khoản", "Xóa tài khoản", "Khóa tài khoản", "Cập nhật mật khẩu", "Lịch sử đăng nhập", "Bạn bè"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listFeatures.setBounds(5, 76, 170, 337);
		panel.add(listFeatures);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setBounds(5, 10, 155, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Tìm kiếm");
		btnNewButton.setBounds(40, 40, 90, 25);
		panel.add(btnNewButton);
		
		btnExit = new JButton("Thoát");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnExit.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnExit.setBounds(40, 420, 90, 25);
		panel.add(btnExit);
		
		JPanel panelMainContent = new JPanel();
		panelMainContent.setBackground(new Color(240, 240, 240));
		panelMainContent.setBounds(175, 0, 430, 460);
		contentPane.add(panelMainContent);
	}
}
