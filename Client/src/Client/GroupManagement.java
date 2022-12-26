package Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GroupManagement extends JFrame {

	private JPanel contentPane;
	private JTextField inviteTextField;
	private JScrollPane memberScrollPane;
	private JTable memberTable;
	private JButton btniTnNhm;

	/**
	 * Create the frame.
	 */
	public GroupManagement() {
		setBounds(100, 100, 539, 477);
		setResizable(false);
		setVisible(true);
		setTitle("Quản lý nhóm");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton inviteBtn = new JButton("Mời vào nhóm");
		inviteBtn.setBounds(178, 10, 133, 24);
		contentPane.add(inviteBtn);
		
		inviteTextField = new JTextField();
		inviteTextField.setColumns(10);
		inviteTextField.setBounds(10, 12, 158, 21);
		contentPane.add(inviteTextField);
		
		JButton leftBtn = new JButton("🚪");
		leftBtn.setBounds(464, 10, 52, 24);
		contentPane.add(leftBtn);
		
		memberScrollPane = new JScrollPane();
		memberScrollPane.setBounds(10, 52, 506, 378);
		contentPane.add(memberScrollPane);
		
		memberTable = new JTable();
		memberScrollPane.setViewportView(memberTable);
		
		btniTnNhm = new JButton("Đổi tên nhóm");
		btniTnNhm.setBounds(321, 10, 133, 24);
		contentPane.add(btniTnNhm);
	}
}
