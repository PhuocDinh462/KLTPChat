package AdminForm;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListFriend extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ListFriend() {
		setBounds(0, 0, 430, 460);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 410, 440);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"STT", "T\u00EAn b\u1EA1n b\u00E8"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		scrollPane.setViewportView(table);
	}

}
