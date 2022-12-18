package AdminInterfaces;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LoginHistoryAUser extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public LoginHistoryAUser() {
		setBounds(0, 0, 430, 460);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 410, 440);
		add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"STT", "Th\u1EDDi gian \u0111\u0103ng nh\u1EADp"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		scrollPane.setViewportView(table);
	}
}
