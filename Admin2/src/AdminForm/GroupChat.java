package AdminForm;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GroupChat extends JPanel {
	private JTable tableAllGroup;
	private DefaultTableModel groupsTableModel;
	private DefaultTableModel listTableModel;
	private JTextField textFind;
	private JTable tableAGroup;

	public GroupChat() {
		setLayout(null);
		setBounds(0, 0, 1025, 470);
		tableAllGroup = new JTable();
		String[] titleTable1 = new String[] { "No.", "Group name", "Date created" };
		groupsTableModel = new DefaultTableModel(new Object[][] { { 1, "klpt", "12/10" } }, titleTable1) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		tableAllGroup.setModel(new DefaultTableModel(new Object[][] { { new Integer(1), "klpt", "12/10" }, },
				new String[] { "STT", "T\u00EAn nh\u00F3m chat", "Ng\u00E0y t\u1EA1o nh\u00F3m" }));
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

		JScrollPane scrollPaneAllGroup = new JScrollPane(tableAllGroup);
		scrollPaneAllGroup.setBounds(10, 45, 380, 415);
		add(scrollPaneAllGroup);

		textFind = new JTextField();
		textFind.setColumns(10);
		textFind.setBounds(397, 10, 200, 30);
		add(textFind);

		JButton btnDisplay = new JButton("Hiển thị");
		btnDisplay.setBounds(445, 45, 90, 25);
		add(btnDisplay);

		JScrollPane scrollPaneAGroup = new JScrollPane();
		scrollPaneAGroup.setBounds(605, 45, 410, 415);
		add(scrollPaneAGroup);

		tableAGroup = new JTable();
		tableAGroup.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"STT", "H\u1ECD t\u00EAn", "Vai tr\u00F2 trong nh\u00F3m"
			}
		));
		tableAGroup.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableAGroup.getColumnModel().getColumn(0).setMaxWidth(35);
		tableAGroup.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableAGroup.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableAGroup.getColumnModel().getColumn(2).setMaxWidth(150);
		scrollPaneAGroup.setViewportView(tableAGroup);
		
		JLabel lblTable1 = new JLabel("Danh sách các nhóm chat");
		lblTable1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblTable1.setBounds(100, 10, 180, 35);
		add(lblTable1);
		
		JLabel lblTable2 = new JLabel("Danh sách thành viên của nhóm chat");
		lblTable2.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblTable2.setBounds(685, 10, 255, 35);
		add(lblTable2);
		String[] titleTable2 = new String[] { "No.", "Admin", "Member" };
		listTableModel = new DefaultTableModel(new Object[][] { { 1, "long1", "long2" } }, titleTable2) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

	}
}
