package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Search extends JFrame {

	private static JPanel contentPane;
	private static JTable msgHistoryTable;
	private JTextField SenderTextField;
	private JTextField contentTextField;
	private static DefaultTableModel msgHistoryTableModel;
	private static JScrollPane msgHistoryScrollPane;
	private static String name;

	/**
	 * Create the frame.
	 */
	public Search(String name, boolean conversationStatus) {
		Search.name = name;
		setBounds(100, 100, 628, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setVisible(true);
		setTitle("Tìm kiếm - " + name);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNgiGi = new JLabel("Người gửi");
		lblNgiGi.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNgiGi.setBounds(10, 19, 78, 13);
		contentPane.add(lblNgiGi);

		SenderTextField = new JTextField();
		SenderTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		SenderTextField.setColumns(10);
		SenderTextField.setBounds(77, 15, 118, 20);
		contentPane.add(SenderTextField);

		contentTextField = new JTextField();
		contentTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		contentTextField.setColumns(10);
		contentTextField.setBounds(285, 15, 259, 20);
		contentPane.add(contentTextField);
		contentTextField.grabFocus();

		JLabel lblNiDung = new JLabel("Nội dung");
		lblNiDung.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNiDung.setBounds(225, 19, 78, 13);
		contentPane.add(lblNiDung);

		JButton searchButton = new JButton("🔎︎");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!contentTextField.getText().isBlank()) {
					if (conversationStatus) {
						if (SenderTextField.getText().isBlank())
							Main.sendMessage("Command_SearchMsgHistoryAll`" + name + "`" + contentTextField.getText());
						else
							Main.sendMessage("Command_SearchMsgHistoryBySender`" + name + "`"
									+ SenderTextField.getText() + "`" + contentTextField.getText());
					}

					else
						Main.sendMessage("Command_SearchMsgHistoryInGroup`" + name + "`" + SenderTextField.getText()
								+ "`" + contentTextField.getText());
					
					contentTextField.setText("");
				}
			}
		});
		searchButton.setBounds(554, 14, 50, 23);
		contentPane.add(searchButton);
		getRootPane().setDefaultButton(searchButton);

		// Msg history Table:
		msgHistoryTableModel = new DefaultTableModel();
		msgHistoryTableModel.addColumn("Người gửi");
		msgHistoryTableModel.addColumn("Thời gian");
		msgHistoryTableModel.addColumn("Nội dung");

		msgHistoryTable = new JTable(msgHistoryTableModel);
		msgHistoryTable.getColumnModel().getColumn(0).setMinWidth(120);
		msgHistoryTable.getColumnModel().getColumn(0).setMaxWidth(120);
		msgHistoryTable.getColumnModel().getColumn(1).setMinWidth(120);
		msgHistoryTable.getColumnModel().getColumn(1).setMaxWidth(120);
		msgHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		msgHistoryTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		msgHistoryTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		msgHistoryTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		msgHistoryScrollPane = new JScrollPane(msgHistoryTable);
		msgHistoryScrollPane.setBounds(10, 49, 594, 377);
		msgHistoryScrollPane.setViewportView(msgHistoryTable);

		contentPane.add(msgHistoryScrollPane);
	}

	public static void refresh(String[] msgHistoryList) {
		if (contentPane == null)
			return;

		// Msg history Table:
		msgHistoryTableModel = new DefaultTableModel();
		msgHistoryTableModel.addColumn("Người gửi");
		msgHistoryTableModel.addColumn("Thời gian");
		msgHistoryTableModel.addColumn("Nội dung");

		for (int i = 1; i < msgHistoryList.length; i++) {
			String[] str = msgHistoryList[i].split("¿");
			Object[] rowObjects = { str[0], str[1], str[2] };
			msgHistoryTableModel.addRow(rowObjects);
		}

		msgHistoryTable = new JTable(msgHistoryTableModel);
		msgHistoryTable.getColumnModel().getColumn(0).setMinWidth(120);
		msgHistoryTable.getColumnModel().getColumn(0).setMaxWidth(120);
		msgHistoryTable.getColumnModel().getColumn(1).setMinWidth(120);
		msgHistoryTable.getColumnModel().getColumn(1).setMaxWidth(120);
		msgHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		msgHistoryTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		msgHistoryTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		msgHistoryTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		msgHistoryScrollPane = new JScrollPane(msgHistoryTable);
		msgHistoryScrollPane.setBounds(10, 49, 594, 377);
		msgHistoryScrollPane.setViewportView(msgHistoryTable);

		contentPane.remove(contentPane.getComponentCount() - 1);
		contentPane.add(msgHistoryScrollPane);
	}
}
