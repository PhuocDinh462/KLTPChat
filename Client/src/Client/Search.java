package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Search extends JFrame {

	private JPanel contentPane;
	private JTable msgHistoryTable;
	private JTextField SenderTextField;
	private JTextField contentTextField;

	/**
	 * Create the frame.
	 */
	public Search() {
		setBounds(100, 100, 628, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setVisible(true);
		setTitle("Tìm kiếm");

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane msgHistoryScrollPane = new JScrollPane();
		msgHistoryScrollPane.setBounds(10, 49, 594, 377);
		contentPane.add(msgHistoryScrollPane);
		
		msgHistoryTable = new JTable();
		msgHistoryScrollPane.setViewportView(msgHistoryTable);
		
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
		searchButton.setBounds(554, 14, 50, 23);
		contentPane.add(searchButton);
		getRootPane().setDefaultButton(searchButton);
	}
}
