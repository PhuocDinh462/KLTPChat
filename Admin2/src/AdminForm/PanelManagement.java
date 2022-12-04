package AdminForm;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class PanelManagement extends JPanel {

	private JTable tableUsers;
	private DefaultTableModel userTableModel;
	private JTextField textFindUser;

	public PanelManagement() {
		setLayout(null);
		setBounds(0, 0, 1040, 470);

		String[] titleTable = new String[] { "STT", "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính",
				"Email", "Trạng thái", "Ngày tạo" };
		Object[][] listItem = new Object[][] {
				{ 1, "abl", "clong", "a135b Tran Hung Dao", "11/10/2002", "Male", "leoit811@gmail.com", "Offline",
						"2022/09/12 00:00" },
				{ 2, "wbl", "blong", "o135b Tran Hung Dao", "12/11/2001", "Female", "leoit811@gmail.com", "Online",
						"2014/05/11 00:00" },
				{ 3, "cbl", "along", "c135b Tran Hung Dao", "12/10/2023", "Male", "leoit811@gmail.com", "Offline",
						"2014/10/02 00:00" } };
		userTableModel = new DefaultTableModel(listItem, titleTable);

		tableUsers = new JTable();
		tableUsers.setRowSelectionAllowed(false);
		tableUsers.setFillsViewportHeight(true);
		tableUsers.setFont(new Font("Tahome", Font.PLAIN, 14));

		tableUsers.setModel(new DefaultTableModel(listItem, titleTable));

		setTable();

		JScrollPane scrollPane = new JScrollPane(tableUsers);
		scrollPane.setBounds(10, 45, 1020, 415);
		scrollPane.setVisible(true);
		add(scrollPane);

		JTableHeader tHeader = tableUsers.getTableHeader();
		tHeader.setFont(new Font("Tahome", Font.BOLD, 14));

		JButton btnOrther = new JButton("Các chức năng khác");
		btnOrther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FeaturesManagement FM = new FeaturesManagement();
			
			}
		});
		btnOrther.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnOrther.setBounds(845, 8, 170, 30);
		add(btnOrther);

		textFindUser = new JTextField();
		textFindUser.setBounds(15, 8, 200, 30);
		add(textFindUser);
		textFindUser.setColumns(10);

		JButton btnFind = new JButton("Tìm kiếm");
		btnFind.setBounds(225, 11, 90, 25);
		add(btnFind);

		JButton btnNewButton_1 = new JButton("Thêm tài khoản");
		btnNewButton_1.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnNewButton_1.setBounds(660, 8, 130, 30);
		add(btnNewButton_1);
	}

	private void setTable() {
		tableUsers.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableUsers.getColumnModel().getColumn(0).setMaxWidth(35);
		tableUsers.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableUsers.getColumnModel().getColumn(1).setMaxWidth(200);
		tableUsers.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableUsers.getColumnModel().getColumn(2).setMaxWidth(200);
		tableUsers.getColumnModel().getColumn(3).setPreferredWidth(170);
		tableUsers.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableUsers.getColumnModel().getColumn(4).setMaxWidth(150);
		tableUsers.getColumnModel().getColumn(5).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(6).setPreferredWidth(150);
		tableUsers.getColumnModel().getColumn(6).setMaxWidth(250);
		tableUsers.getColumnModel().getColumn(7).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(8).setPreferredWidth(140);
		tableUsers.getColumnModel().getColumn(8).setMaxWidth(170);
	}
}
