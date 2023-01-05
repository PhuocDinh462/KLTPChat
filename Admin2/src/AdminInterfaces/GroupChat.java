package AdminInterfaces;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

import AdminInterfaces.PanelManagement.ComparatorIncreasing;
import Server.Classes.Group;
import Server.Classes.User;
import Server.Controllers.GroupController;
import Server.Controllers.UserController;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GroupChat extends JPanel {
	private JTable tableAllGroup;
	private DefaultTableModel groupsListTableModel;
	private DefaultTableModel membersListTableModel;
	private JTextField textFind;
	private JTable tableAGroup;

	private GroupController groupController;

	private ArrayList<Group> groups;

	private Group groupSelection;

	public GroupChat() {
		groupController = new GroupController();
		groups = groupController.getAllGroups();
		groupSelection = new Group();

		setLayout(null);
		setBounds(0, 0, 1025, 470);
		tableAllGroup = new JTable();

		// Display data on table
		setListGroups(groups);

		JScrollPane scrollPaneAllGroup = new JScrollPane(tableAllGroup);
		scrollPaneAllGroup.setBounds(10, 45, 380, 415);
		add(scrollPaneAllGroup);

		textFind = new JTextField();
		textFind.setColumns(10);
		textFind.setBounds(400, 10, 200, 30);
		add(textFind);

		JButton btnFilter = new JButton("Tìm kiếm");
		btnFilter.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnFilter.setBounds(455, 45, 90, 25);
		add(btnFilter);

		tableAGroup = new JTable();
		tableAGroup.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, },
				new String[] { "STT", "Tên đăng nhập", "Vai trò trong nhóm" }));
		setColSpaceGroupTable();

		JScrollPane scrollPaneAGroup = new JScrollPane(tableAGroup);
		scrollPaneAGroup.setBounds(605, 45, 410, 415);
		add(scrollPaneAGroup);

		JLabel lblTable1 = new JLabel("Danh sách các nhóm chat");
		lblTable1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblTable1.setBounds(100, 10, 180, 35);
		add(lblTable1);

		JLabel lblTable2 = new JLabel("Danh sách thành viên của nhóm chat");
		lblTable2.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblTable2.setBounds(730, 10, 255, 35);
		add(lblTable2);

		JComboBox comboBoxAll = new JComboBox();
		comboBoxAll.setFont(new Font("Dialog", Font.PLAIN, 13));
		comboBoxAll.setModel(new DefaultComboBoxModel(new String[] { "Tăng dần", "Giảm dần" }));
		comboBoxAll.setBounds(285, 10, 90, 30);
		add(comboBoxAll);

		JComboBox comboBoxOne = new JComboBox();
		comboBoxOne.setModel(new DefaultComboBoxModel(new String[] { "Tăng dần", "Giảm dần" }));
		comboBoxOne.setFont(new Font("Dialog", Font.PLAIN, 13));
		comboBoxOne.setBounds(620, 9, 90, 30);
		add(comboBoxOne);

		comboBoxAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = comboBoxAll.getSelectedIndex();
				setListGroups(sortUsers(type));
			}
		});

		comboBoxOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = comboBoxOne.getSelectedIndex();
				Group g = new Group(groupSelection);

				Collections.sort(g.getlistUsers());
				if (type == 1) {
					Collections.reverse(g.getlistUsers());
				}

				setListMembers(g);
			}
		});

		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] ObjButtons = { "Group", "Member" };

				int promptResult = JOptionPane.showOptionDialog(null, "Tìm kiếm?", "Xác nhận",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (promptResult == 0)
					setListGroups(filterGroup(textFind.getText().trim()));
				else
					setListMembers(filterMember(textFind.getText().trim()));
			}
		});

		ListSelectionModel cellSelectionModel = tableAllGroup.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selectedData = null;

				int[] selectedRow = tableAllGroup.getSelectedRows();

				for (int i = 0; i < selectedRow.length; i++) {
					selectedData = (String) tableAllGroup.getValueAt(selectedRow[i], 1);
				}

				for (Group g : groups) {
					if (g.getGroupName().equals(selectedData)) {
						groupSelection = new Group(g);
						setListMembers(g);
					}
				}
			}

		});
	}

	// Function handler
	public ArrayList<Group> filterGroup(String name) {
		ArrayList<Group> listGroup = new ArrayList<Group>(groups);
		ArrayList<Group> filterGroup = new ArrayList<Group>();

		for (Group e : listGroup) {
			if (e.getGroupName().toLowerCase().contains(name.toLowerCase()))
				filterGroup.add(e);
		}
		return filterGroup;
	}

	public Group filterMember(String name) {
		Group g = new Group(groupSelection);
		ArrayList<String> filterMember = new ArrayList<String>();

		for (String e : g.getlistUsers()) {
			if (e.toLowerCase().contains(name.toLowerCase()))
				filterMember.add(e);
		}
		g.setlistUsers(filterMember);
		return g;
	}

	public void setListGroups(ArrayList<Group> g) {
		ArrayList<Group> items = new ArrayList<Group>(g);
		groupsListTableModel = new DefaultTableModel();
		String[] titleTable = { "STT", "Tên Group", "Ngày tạo" };
		for (int i = 0; i < titleTable.length; i++) {
			groupsListTableModel.addColumn(titleTable[i]);
		}

		// Initialize row item
		if (items.isEmpty()) {
			if (!groups.isEmpty()) {
				String[] ObjButtons = { "Cancel" };
				JOptionPane.showOptionDialog(null, "Không tìm thấy", "Xác nhận", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE, null, ObjButtons, ObjButtons[0]);
			}
		} else {
			int cnt = 0;
			for (Group e : items) {
				Object[] obj = { ++cnt, e.getGroupName(), e.getCreateTime() };
				groupsListTableModel.addRow(obj);
			}
			tableAllGroup.setModel(groupsListTableModel);
			setColSpaceAllGroupsTable();
		}
	}

	public void setColSpaceAllGroupsTable() {
		tableAllGroup.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableAllGroup.getColumnModel().getColumn(0).setMinWidth(35);
		tableAllGroup.getColumnModel().getColumn(0).setMaxWidth(35);
		tableAllGroup.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableAllGroup.getColumnModel().getColumn(1).setMinWidth(120);
		tableAllGroup.getColumnModel().getColumn(1).setMaxWidth(150);
		tableAllGroup.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableAllGroup.getColumnModel().getColumn(2).setMinWidth(200);
		tableAllGroup.setFont(new Font("Dialog", Font.PLAIN, 12));
		tableAllGroup.setBounds(10, 100, 355, 410);
	}

	public void setListMembers(Group g) {
		ArrayList<String> items = new ArrayList<String>(g.getlistUsers());
		ArrayList<String> role = new ArrayList<String>(g.getManagers());
		membersListTableModel = new DefaultTableModel();

		String[] titleTable = { "STT", "Tên đăng nhập", "Vai trò trong nhóm" };
		for (int i = 0; i < titleTable.length; i++) {
			membersListTableModel.addColumn(titleTable[i]);
		}

		// Initialize row item
		if (items.isEmpty()) {
			if (!groups.isEmpty()) {
				String[] ObjButtons = { "Cancel" };
				JOptionPane.showOptionDialog(null, "Không tìm thấy", "Xác nhận", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE, null, ObjButtons, ObjButtons[0]);
			}
		} else {
			int cnt = 0;
			for (String e : items) {
				String r = new String();
				if (role.contains(e))
					r = "Quản lý nhóm";
				else
					r = "Thành viên";
				Object[] obj = { ++cnt, e, r };
				membersListTableModel.addRow(obj);
			}
			tableAGroup.setModel(membersListTableModel);
			setColSpaceGroupTable();
		}
	}

	public void setColSpaceGroupTable() {
		tableAGroup.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableAGroup.getColumnModel().getColumn(0).setMaxWidth(35);
		tableAGroup.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableAGroup.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableAGroup.getColumnModel().getColumn(2).setMaxWidth(150);
	}

	public void refreshTable() {
		groups = groupController.getAllGroups();
		setListGroups(groups);
		System.out.println("Refresh complete!");
	}

	/**
	 * Sort list name of group
	 * 
	 * @param type (0: Increasing, 1: Descending)
	 */
	public ArrayList<Group> sortUsers(int type) {
		ArrayList<Group> group = new ArrayList<Group>(groups);
		Collections.sort(group, new ComparatorIncreasing());

		if (type == 1)
			Collections.reverse(group);

		System.out.println("Sorted complete!");
		return group;
	}

	public static class ComparatorIncreasing implements Comparator<Group> {
		@Override
		public int compare(Group s, Group t) {
			String[] str1 = s.getGroupName().split(" ");
			String[] str2 = t.getGroupName().split(" ");
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
