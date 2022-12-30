package Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Client.Main.MessageStatus;
import Client.SignUp.SignUpStatus;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Color;

public class CreateGroup extends JFrame {

	public enum CreateGroupStatus {
		/**
		 * Waiting for response
		 */
		Waiting,

		/**
		 * Failed
		 */
		Failed,

		/**
		 * Create successful
		 */
		Accepted
	}

	/**
	 * Attribute: SignUpStatus - status The status of Sign Up Request
	 */
	public static CreateGroupStatus status;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField groupNameTextField;
	private static ArrayList<String> groupMembers = new ArrayList<String>();

	/**
	 * Create the frame.
	 * 
	 * @param userslist2
	 */
	public CreateGroup(ArrayList<String> friendsList) {
		setBounds(100, 100, 355, 444);
		setTitle("Tạo nhóm");
		setResizable(false);
		setVisible(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblToNhm = new JLabel("Tạo nhóm", SwingConstants.CENTER);
		lblToNhm.setFont(new Font("Arial", Font.BOLD, 20));
		lblToNhm.setBounds(70, 13, 204, 24);
		contentPane.add(lblToNhm);

		JLabel lblTnNhm = new JLabel("Tên nhóm");
		lblTnNhm.setFont(new Font("Arial", Font.BOLD, 12));
		lblTnNhm.setBounds(10, 47, 67, 24);
		contentPane.add(lblTnNhm);

		groupNameTextField = new JTextField();
		groupNameTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		groupNameTextField.setBounds(78, 50, 255, 19);
		contentPane.add(groupNameTextField);
		groupNameTextField.setColumns(10);

		JScrollPane friendScroll = new JScrollPane((Component) null);
		friendScroll.setBorder(new EmptyBorder(10, 0, 0, 0));
		friendScroll.setBounds(10, 114, 150, 238);
		contentPane.add(friendScroll);

		DefaultListModel<String> listModelFriend = new DefaultListModel<String>();
		DefaultListModel<String> listModelGroup = new DefaultListModel<String>();

		for (String item : friendsList) {
			listModelFriend.addElement(item);
		}
		JList<String> friendList = new JList<String>(listModelFriend);
		friendList.setVisibleRowCount(10);
		friendScroll.setViewportView(friendList);

		friendList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int i = friendList.getSelectedIndex();
				Boolean check = false;
				for (int k = 0; i < listModelGroup.size(); k++) {
					if (listModelGroup.get(k) == friendList.getModel().getElementAt(i)) {
						check = true;
						break;
					}
				}
				if (!check) {
					listModelGroup.addElement(friendList.getModel().getElementAt(i));
					groupMembers.add(friendList.getModel().getElementAt(i));
				} else {
					JOptionPane.showMessageDialog(null, "Đã tồn tại người dùng trong danh sách", "Lỗi",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JScrollPane groupMemberScroll = new JScrollPane((Component) null);
		groupMemberScroll.setBorder(new EmptyBorder(10, 0, 0, 0));
		groupMemberScroll.setBounds(183, 114, 150, 238);
		contentPane.add(groupMemberScroll);

		JList<String> groupMemberList = new JList<String>(listModelGroup);
		groupMemberList.setVisibleRowCount(10);
		groupMemberScroll.setRowHeaderView(groupMemberList);

		groupMemberList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int i = groupMemberList.getSelectedIndex();
				System.out.println(i);
				System.out.println(groupMembers.get(0));
				System.out.println(groupMembers.size());

				for (int j = 0; j < groupMembers.size(); i++) {
					if (groupMembers.get(j) == groupMemberList.getModel().getElementAt(i)) {
						System.out.println(groupMembers.get(0));
						groupMembers.remove(j);
						System.out.println(groupMembers.size());
					}
				}
				System.out.println(groupMemberList.getModel().getElementAt(0));
				String[] ListData = new String[groupMemberList.getModel().getSize()];
				for (int t = 0; i < ListData.length; i++) {
					if (t == i) {

					} else {
						ListData[i] = groupMemberList.getModel().getElementAt(t);
					}
				}
				groupMemberList.setListData(ListData);
				System.out.println(groupMemberList.getModel().getElementAt(0));
				groupMemberScroll.setRowHeaderView(groupMemberList);
			}
		});

		JLabel lblDanhSchBn = new JLabel("Danh sách bạn bè");
		lblDanhSchBn.setFont(new Font("Arial", Font.BOLD, 12));
		lblDanhSchBn.setBounds(10, 97, 204, 24);
		contentPane.add(lblDanhSchBn);

		JLabel lblDanhSchThnh = new JLabel("Thành viên nhóm");
		lblDanhSchThnh.setFont(new Font("Arial", Font.BOLD, 12));
		lblDanhSchThnh.setBounds(183, 97, 204, 24);
		contentPane.add(lblDanhSchThnh);

		JButton createGroupBtn = new JButton("Tạo nhóm");
		createGroupBtn.setBounds(10, 362, 323, 35);
		contentPane.add(createGroupBtn);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 85, 323, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(170, 124, 3, 228);
		contentPane.add(separator_1);

		createGroupBtn
				.addActionListener(e -> createGroupButtonEventHandler(groupNameTextField.getText(), groupMembers));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {

				for (int i = 0; i < groupMembers.size(); i++) {
					groupMembers.remove(i);
				}
			}
		});

	}

	void createGroupButtonEventHandler(String groupName, ArrayList<String> members) {

		if (groupName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên nhóm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (members.size() == 0) {
			JOptionPane.showMessageDialog(this, "Bạn chưa thêm bạn bè vào nhóm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else {
			status = CreateGroup.CreateGroupStatus.Waiting;
			String msg = ("Command_CreateNewGroup`" + groupName);
			for (int i = 0; i < members.size(); i++) {
				msg = (msg + "`" + members.get(i));
			}
			Main.sendMessage(msg);

			while (status == CreateGroup.CreateGroupStatus.Waiting)
				System.out.print("");

			if (status == CreateGroup.CreateGroupStatus.Accepted) {
				JOptionPane.showMessageDialog(this, "Tạo thành công!", "Thành công", JOptionPane.OK_OPTION);
			} else {
				JOptionPane.showMessageDialog(this, "Tạo thất bại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
