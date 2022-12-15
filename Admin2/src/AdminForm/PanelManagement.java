package AdminForm;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import javax.swing.JTable;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;

//BUTTON RENDERER CLASS
class ButtonRenderer extends JButton implements TableCellRenderer {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

//CONSTRUCTOR
	public ButtonRenderer() {
		// SET BUTTON PROPERTIES
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row,
			int col) {

		// SET PASSED OBJECT AS BUTTON TEXT
//		setText((obj == null) ? "" : obj.toString());
		setText("...");
		return this;
	}
}

//BUTTON EDITOR CLASS
class ButtonEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton btn;
	private String lbl;
	private Boolean clicked;

	public ButtonEditor(JTextField txt) {
		super(txt);

		btn = new JButton();
		btn.setOpaque(true);

		// WHEN BUTTON IS CLICKED
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fireEditingStopped();
			}
		});
	}

//OVERRIDE A COUPLE OF METHODS
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {

		// SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
		lbl = (obj == null) ? "" : obj.toString();
		btn.setText(lbl);
		clicked = true;
		return btn;
	}

//IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
	@Override
	public Object getCellEditorValue() {

		if (clicked) {
			// SHOW US SOME MESSAGE
			FeaturesManagement FM = new FeaturesManagement();
		}
		// SET IT TO FALSE NOW THAT ITS CLICKED
		clicked = false;
		return new String(lbl);
	}

	@Override
	public boolean stopCellEditing() {

		// SET CLICKED TO FALSE FIRST
		clicked = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}

public class PanelManagement extends JPanel {

	private JTable tableUsers;
	private DefaultTableModel userTableModel;
	private JTextField textFindUser;

	public PanelManagement() {
		init();
		JScrollPane scrollPane = new JScrollPane(tableUsers);
		scrollPane.setBounds(10, 45, 1050, 415);
		scrollPane.setVisible(true);
		add(scrollPane);

		JTableHeader tHeader = tableUsers.getTableHeader();
		tHeader.setFont(new Font("Tahome", Font.BOLD, 12));

		textFindUser = new JTextField();
		textFindUser.setBounds(15, 8, 200, 30);
		add(textFindUser);
		textFindUser.setColumns(10);

		JButton btnFind = new JButton("Tìm kiếm");
		btnFind.setBounds(225, 11, 90, 25);
		add(btnFind);

		JButton btnAddNewAccount = new JButton("Thêm tài khoản");
		btnAddNewAccount.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnAddNewAccount.setBounds(930, 7, 130, 30);
		add(btnAddNewAccount);

		JButton btnDeleteAccount = new JButton("Xóa tài khoản");
		btnDeleteAccount.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnDeleteAccount.setBounds(800, 7, 120, 30);
		add(btnDeleteAccount);

		JButton btnBlockAccount = new JButton("Khóa tài khoản");
		btnBlockAccount.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnBlockAccount.setBounds(660, 7, 130, 30);
		add(btnBlockAccount);
	}

	private void init() {
		setLayout(null);
		setBounds(0, 0, 1070, 470);
		String[] titleTable = new String[] { "STT", "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính",
				"Email", "Trạng thái", "Ngày tạo", "Lựa chọn", "Chức năng" };
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
		tableUsers.setFont(new Font("Dialog", Font.PLAIN, 12));

		tableUsers.setModel(userTableModel);
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
		tableUsers.getColumnModel().getColumn(9).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(10).setPreferredWidth(80);
		tableUsers.getColumnModel().getColumn(10).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
		tableUsers.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor(new JTextField()));
	}
}
