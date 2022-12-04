package Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class FriendList extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable friendListTable;
	private static DefaultTableModel friendListTableModel;

	/**
	 * Create the frame.
	 */
	public FriendList(String[] friendList) {
		addComponents(friendList);
		setTitle("Danh sách bạn bè");
		setResizable(false);
		setVisible(true);
	}

	public void addComponents(String[] friendList) {
		// Content Pane
		setBounds(100, 100, 289, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Friend List Table
		friendListTableModel = new DefaultTableModel();
		friendListTableModel.addColumn("Tên tài khoản");
		friendListTableModel.addColumn("Hủy kết bạn");

		for (int i = 1; i < friendList.length; i++) {
			Object[] rowObjects = { friendList[i], "Hủy kết bạn" };
			friendListTableModel.addRow(rowObjects);
			friendListTable = new JTable(friendListTableModel);
			friendListTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
			friendListTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JTextField()));
		}

		JScrollPane friendListScrollPane = new JScrollPane();
		friendListScrollPane.setBounds(10, 10, 255, 368);
		friendListScrollPane.setViewportView(friendListTable);
		contentPane.add(friendListScrollPane);
	}
}