package AdminInterfaces;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import Server.Classes.Group;
import Server.Classes.Message;
import Server.Classes.User;
import Server.Controllers.GroupController;
import Server.Controllers.MessageController;
import Server.Controllers.UserController;

import javax.swing.JTable;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

public class PanelManagement extends JPanel {

	private JTable tableUsers;
	private DefaultTableModel userTableModel;
	private JTextField textFindUser;
	private String[] titleTable;

	private UserController userController;
	private MessageController messageController;
	private GroupController groupController;

	private ArrayList<User> accounts;

	public PanelManagement(Main main) {
		userController = new UserController();
		messageController = new MessageController();
		groupController = new GroupController();
		accounts = userController.getAllUsers();

		initTableModel();
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

		// Filter User
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

		JComboBox comboBoxSorted = new JComboBox();
		comboBoxSorted.setFont(new Font("Dialog", Font.PLAIN, 13));
		comboBoxSorted.setModel(new DefaultComboBoxModel(new String[] { "Tăng dần", "Giảm dần" }));
		comboBoxSorted.setBounds(390, 8, 90, 30);
		add(comboBoxSorted);

		JLabel lblSorted = new JLabel("Sắp xếp:");
		lblSorted.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblSorted.setBounds(335, 8, 65, 30);
		add(lblSorted);

		JButton btnUnblocked = new JButton("Mở tài khoản");
		btnUnblocked.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnUnblocked.setBounds(530, 7, 120, 30);
		add(btnUnblocked);

		// Button Features
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] ObjButtons = { "Tên đăng nhập", " Họ Tên" };

				int promptResult = JOptionPane.showOptionDialog(null, "Tìm kiếm theo?", "Xác nhận",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[1]);

				setListItems(filterBy(textFindUser.getText().trim(), promptResult));
			}
		});

		comboBoxSorted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = comboBoxSorted.getSelectedIndex();
				setListItems(sortUsers(type));
			}
		});

		btnUnblocked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteOrBlockedAccount(10);
				main.refreshAccount();
			}
		});

		btnBlockAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] ObjButtons = { "Yes", "No" };

				int promptResult = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn muốn khóa tài khoản?",
						"Xác nhận", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
						ObjButtons[1]);
				if (promptResult == 0) {
					deleteOrBlockedAccount(1);
					main.refreshAccount();
				}
			}
		});

		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] ObjButtons = { "Yes", "No" };

				int promptResult = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn muốn xóa?", "Xác nhận",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (promptResult == 0) {
					deleteOrBlockedAccount(0);
					main.refreshAccount();
					main.refreshGroups();
				}
			}
		});

		btnAddNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateAccount(PanelManagement.this, main);
			}
		});
	}

	private void initTableModel() {
		setLayout(null);
		setBounds(0, 0, 1070, 470);
		titleTable = new String[] { "STT", "Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email",
				"Trạng thái", "Ngày tạo", "Lựa chọn", "Chức năng" };

		// Create Table
		tableUsers = new JTable();
		tableUsers.setRowSelectionAllowed(false);
		tableUsers.setFillsViewportHeight(true);
		tableUsers.setFont(new Font("Dialog", Font.PLAIN, 12));

		// Initialize row content of the table
		setListItems(accounts);
		setColSpaceTable();
	}

	public void refreshList() {
		accounts = userController.getAllUsers();
		setListItems(accounts);
	}

	public void setColSpaceTable() {
		tableUsers.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableUsers.getColumnModel().getColumn(0).setMaxWidth(35);
		tableUsers.getColumnModel().getColumn(1).setPreferredWidth(110);
		tableUsers.getColumnModel().getColumn(1).setMaxWidth(200);
		tableUsers.getColumnModel().getColumn(2).setPreferredWidth(170);
		tableUsers.getColumnModel().getColumn(2).setMaxWidth(200);
		tableUsers.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableUsers.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableUsers.getColumnModel().getColumn(4).setMaxWidth(150);
		tableUsers.getColumnModel().getColumn(5).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(6).setPreferredWidth(150);
		tableUsers.getColumnModel().getColumn(6).setMaxWidth(250);
		tableUsers.getColumnModel().getColumn(7).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(8).setPreferredWidth(140);
		tableUsers.getColumnModel().getColumn(8).setMaxWidth(170);
		tableUsers.getColumnModel().getColumn(9).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(10).setMaxWidth(75);
		tableUsers.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
		tableUsers.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor(new JTextField()));
	}

	public ArrayList<User> filterBy(String name, int type) {
		ArrayList<User> users = new ArrayList<User>(accounts);
		ArrayList<User> filterUsers = new ArrayList<User>();

		if (type == 0)
			for (User e : users) {
				if (e.getInfor().getUsername().toLowerCase().contains(name.toLowerCase()))
					filterUsers.add(e);
			}
		else
			for (User e : users) {
				if (e.getInfor().getFullname().toLowerCase().contains(name.toLowerCase()))
					filterUsers.add(e);
			}
		System.out.println("Filter complete!");
		return filterUsers;
	}

	public void setListItems(ArrayList<User> users) {
		ArrayList<User> items = new ArrayList<User>(users);

		userTableModel = new DefaultTableModel() {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, Boolean.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		// Initialize title of the table
		for (int i = 0; i < titleTable.length; i++) {
			userTableModel.addColumn(titleTable[i]);
		}

		// Initialize row item
		if (items.isEmpty()) {
			if (!accounts.isEmpty()) {
				String[] ObjButtons = { "Cancel" };
				JOptionPane.showOptionDialog(null, "Không tìm thấy", "Xác nhận", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE, null, ObjButtons, ObjButtons[0]);
			}
		} else {
			int cnt = 0;
			for (User e : items) {
				String status = e.getInfor().getStatus() ? "Online" : "Offline";
				status = e.getInfor().getBlocked() ? "Blocked" : status;

				Object[] obj = { ++cnt, e.getInfor().getUsername(), e.getInfor().getFullname(),
						e.getInfor().getAddress(), e.getInfor().getDOB(), e.getInfor().getGender(),
						e.getInfor().getEmail(), status, e.getCreateTime(), false, "...`" + e.getId() };
				userTableModel.addRow(obj);
			}
		}

		// Set model for table users
		tableUsers.setModel(userTableModel);
		setColSpaceTable();
	}

	public void deleteOrBlockedAccount(int type) {
		int sizeRow = tableUsers.getRowCount();
		Boolean checkChange = false;
		String title = new String();
		ArrayList<String> listUserError = new ArrayList<String>();

		for (int i = 0; i < sizeRow; i++) {
			Boolean selectedUser = (Boolean) tableUsers.getValueAt(i, 9);

			selectedUser = (selectedUser == null || selectedUser == false) ? false : true;

			if (selectedUser == true) {
				String getStatus = (String) tableUsers.getValueAt(i, 7);

				if (getStatus.equals("Online")) {
					listUserError.add((String) tableUsers.getValueAt(i, 1));
					tableUsers.getModel().setValueAt(false, i, 9);
					continue;
				}

				String name = (String) tableUsers.getValueAt(i, 1);
				if (type == 0) {
					title = "Xóa thành công!";
					User userUpdate = userController.getUserByUsername(name);
					deleteInformation(userUpdate);
					userController.deleteByUsername(name);
				} else {
					title = type / 10 == 1 ? "Mở khóa tài khoản thành công!" : "Khóa tài khoản thành công";
					userController.updateBlock(name, type / 10 == 1 ? false : true);
				}
				checkChange = true;
			}
		}

		if (checkChange) {
			accounts = userController.getAllUsers();
			setListItems(accounts);
		} else if (!listUserError.isEmpty()) {
			title = "";
			for (String e : listUserError) {
				title += e;
			}
			title += " đang Online.";
		} else
			title = "Không có gì để cập nhật!";

		String[] ObjButtons = { "OK" };
		JOptionPane.showOptionDialog(null, title, "Xác nhận", JOptionPane.DEFAULT_OPTION, JOptionPane.NO_OPTION, null,
				ObjButtons, ObjButtons[0]);

		System.out.println("Update status done!");
	}

	public void deleteInformation(User userUpdate) {
		ArrayList<Group> groups = new ArrayList<Group>();
		messageController.deleteAllMessagesByReceiver(userUpdate.getInfor().getUsername());
		messageController.deleteAllMessagesBySender(userUpdate.getInfor().getUsername());
		groups = groupController.getAllGroups();
		ArrayList<String> mess = new ArrayList<String>();
		mess = messageController.getAllMesssagesId();
		int i = 0;

		for (User e : accounts) {
			if (e.getFriend().contains(userUpdate.getInfor().getUsername())) {
				userController.deleteFriend(e.getId(), userUpdate.getInfor().getUsername());
			}
		}

		for (Group e : groups) {

			if (e.getlistUsers().contains(userUpdate.getInfor().getUsername())) {
				groupController.removePeopleGroup(userUpdate.getInfor().getUsername(), e.getGroupId());
				e.getlistUsers().remove(i);

				for (String idMess : e.getmessageId()) {
					if (!mess.contains(idMess)) {
						groupController.removeMessageById(idMess, e.getGroupId());
					}
				}
			}
			if (e.getManagers().contains(userUpdate.getInfor().getUsername())) {
				groupController.removeManagerGroup(userUpdate.getInfor().getUsername(), e.getGroupId());
				e.getManagers().remove(i);
			}

			if (e.getlistUsers().isEmpty()) {
				groupController.deleteGroup(e.getGroupId());
			}
			i++;
		}
	}

	private int getAccountIndex(String username) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getInfor().getUsername().equals(username))
				return i;
		}
		return -1;
	}

	public Boolean changeStatusUserByUsername(String username, String status) {
		int sizeRow = tableUsers.getRowCount();
		int index = getAccountIndex(username);
		Boolean online = status.equals("Online") ? true : false;

		accounts.get(index).getInfor().setStatus(online);

		for (int i = 0; i < sizeRow; i++) {
			if (username.equals(tableUsers.getModel().getValueAt(i, 1))) {
				tableUsers.getModel().setValueAt(status, i, 7);
				return true;
			}
		}
		return false;
	}

	/**
	 * Sort list users
	 * 
	 * @param type (0: Increasing, 1: Descending)
	 */
	public ArrayList<User> sortUsers(int type) {
		ArrayList<User> users = new ArrayList<User>(accounts);
		Collections.sort(users, new ComparatorIncreasing());

		if (type == 1)
			Collections.reverse(users);

		System.out.println("Sorted complete!");
		return users;
	}

	public static class ComparatorIncreasing implements Comparator<User> {
		@Override
		public int compare(User s, User t) {
			String[] str1 = s.getInfor().getFullname().split(" ");
			String[] str2 = t.getInfor().getFullname().split(" ");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = (Date) formatter.parse(s.getCreateTime());
				d2 = (Date) formatter.parse(t.getCreateTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// Compare username
			int checkName = 0;
			for (int i = str1.length - 1, j = str2.length - 1; i >= 0; i--, j--) {
				int check;
				if (i < 0 || j < 0) {
					checkName = i < j ? -1 : 1;
					break;
				}
				check = str1[i].compareTo(str2[j]);
				if (check != 0) {
					checkName = check;
					break;
				}
			}

			return (checkName != 0) ? checkName : d1.compareTo(d2);
		}
	}
}

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
	private String[] textButton;
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
		textButton = ((String) obj).split("`");

		btn.setText(textButton[0]);
		clicked = true;
		return btn;
	}

//IF BUTTON CELL VALUE CHANGES, IF CLICKED THAT IS
	@Override
	public Object getCellEditorValue() {

		if (clicked) {
			// SHOW US SOME MESSAGE
			new FeaturesManagement(textButton[1]);

		}
		// SET IT TO FALSE NOW THAT ITS CLICKED
		clicked = false;
		return new String(textButton[0] + "`" + textButton[1]);
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
