package AdminInterfaces;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Server.Classes.User;

public class LoginHistoryAUser extends JPanel {
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public LoginHistoryAUser(User user) {
		setBounds(0, 0, 430, 460);
		setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 410, 440);
		add(scrollPane);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		setListHistoryLogin(user);
	}

	public void setListHistoryLogin(User u) {
		ArrayList<String> his = u.getTimeLogin();
		DefaultTableModel model = new DefaultTableModel();
		String[] titleTable = { "STT", "Thời gian đăng nhập" };

		// Initialize title of the table
		for (int i = 0; i < titleTable.length; i++) {
			model.addColumn(titleTable[i]);
		}

		for (int i = 0; i < his.size(); i++) {
			Object[] data = { i + 1, his.get(i) };
			model.addRow(data);
		}

		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		scrollPane.setViewportView(table);
		System.out.println("Display history login complete!");
	}
}
