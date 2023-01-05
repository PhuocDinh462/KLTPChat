package Client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class FriendList extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JTable friendListTable;
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

	public static void deleteRow(int index) {
		if (index >= 0)
			((DefaultTableModel) friendListTable.getModel()).removeRow(index);
	}

	public static void refresh(String[] friendList) {
		if (contentPane == null)
			return;

		// Friend List Table
		friendListTableModel = new DefaultTableModel();
		friendListTableModel.addColumn("Tên tài khoản");
		friendListTableModel.addColumn("Trạng thái");
		friendListTableModel.addColumn("Hủy kết bạn");

		for (int i = 1; i < friendList.length; i++) {
			String[] str = friendList[i].split(":");
			Object[] rowObjects = { str[0], str[1], "Hủy kết bạn:" + "Command_unfriend`" + str[0] };
			friendListTableModel.addRow(rowObjects);
		}

		friendListTable = new JTable(friendListTableModel);

		if (friendList.length > 1) {
			friendListTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
			friendListTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			friendListTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
			friendListTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		}

		JScrollPane friendListScrollPane = new JScrollPane();
		friendListScrollPane.setBounds(10, 10, 393, 368);
		friendListScrollPane.setViewportView(friendListTable);

		contentPane.remove(contentPane.getComponentCount() - 1);
		contentPane.add(friendListScrollPane);
	}

	public static void changeFriendStatusByName(String name, String status) {
		int sizeRow = friendListTable.getRowCount();
		
		for (int i = 0; i < sizeRow; i++)
			if (name.equals(friendListTable.getModel().getValueAt(i, 0)))
		friendListTable.getModel().setValueAt(status, i, 1);
	}

	public void addComponents(String[] friendList) {
		// Content Pane
		setBounds(100, 100, 427, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Friend List Table
		friendListTableModel = new DefaultTableModel();
		friendListTableModel.addColumn("Tên tài khoản");
		friendListTableModel.addColumn("Trạng thái");
		friendListTableModel.addColumn("Hủy kết bạn");

		for (int i = 1; i < friendList.length; i++) {
			String[] str = friendList[i].split(":");
			Object[] rowObjects = { str[0], str[1], "Hủy kết bạn:" + "Command_unfriend`" + str[0] };
			friendListTableModel.addRow(rowObjects);
		}

		friendListTable = new JTable(friendListTableModel);

		if (friendList.length > 1) {
			friendListTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
			friendListTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			friendListTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
			friendListTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		}

		JScrollPane friendListScrollPane = new JScrollPane();
		friendListScrollPane.setBounds(10, 10, 393, 368);
		friendListScrollPane.setViewportView(friendListTable);
		contentPane.add(friendListScrollPane);
	}
}
