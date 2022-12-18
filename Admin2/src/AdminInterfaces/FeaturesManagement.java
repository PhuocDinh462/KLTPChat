package AdminInterfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FeaturesManagement extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnExit;

	private UpdateInformation panelUpdate;
	private LoginHistoryAUser panelHis;
	private ListFriend panelListF;
	private UpdatePassword panelPW;

	public FeaturesManagement() {
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 625, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 170, 460);
		contentPane.add(panel);
		panel.setLayout(null);
		
		panelListF = new ListFriend();
		panelHis = new LoginHistoryAUser();
		panelUpdate = new UpdateInformation();
		panelPW = new UpdatePassword();

		String[] features = { "Cập nhật tài khoản", "Cập nhật mật khẩu", "Lịch sử đăng nhập", "Bạn bè" };

		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setBounds(5, 10, 155, 25);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Tìm kiếm");
		btnNewButton.setBounds(40, 40, 90, 25);
		panel.add(btnNewButton);

		btnExit = new JButton("Thoát");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnExit.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnExit.setBounds(40, 420, 90, 25);
		panel.add(btnExit);

		JPanel panelUDIF = new JPanel();
		panelUDIF.addMouseListener(new PanelButtonMouseAdapter(panelUDIF) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelUpdate);
			}
		});
		panelUDIF.setBackground(Color.LIGHT_GRAY);
		panelUDIF.setForeground(Color.WHITE);
		panelUDIF.setBounds(0, 100, 170, 40);
		panel.add(panelUDIF);
		panelUDIF.setLayout(null);

		JLabel lblUpdate = new JLabel("Cập nhật thông tin");
		lblUpdate.setFont(new Font("Dialog", Font.BOLD, 14));
		lblUpdate.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpdate.setBounds(10, 0, 150, 40);
		panelUDIF.add(lblUpdate);

		JPanel panelUDPass = new JPanel();
		panelUDPass.addMouseListener(new PanelButtonMouseAdapter(panelUDPass) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelPW);
			}
		});
		panelUDPass.setBackground(Color.LIGHT_GRAY);
		panelUDPass.setForeground(Color.WHITE);
		panelUDPass.setLayout(null);
		panelUDPass.setBounds(0, 140, 170, 40);
		panel.add(panelUDPass);

		JLabel lblCpNhtMt = new JLabel("Cập nhật mật khẩu");
		lblCpNhtMt.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpNhtMt.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCpNhtMt.setBounds(10, 0, 150, 40);
		panelUDPass.add(lblCpNhtMt);

		JPanel panelHistoryLogin = new JPanel();
		panelHistoryLogin.addMouseListener(new PanelButtonMouseAdapter(panelHistoryLogin) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelHis);
			}
		});
		panelHistoryLogin.setBackground(Color.LIGHT_GRAY);
		panelHistoryLogin.setForeground(Color.WHITE);
		panelHistoryLogin.setLayout(null);
		panelHistoryLogin.setBounds(0, 180, 170, 40);
		panel.add(panelHistoryLogin);

		JLabel lblLchSng = new JLabel("Lịch sử đăng nhập");
		lblLchSng.setHorizontalAlignment(SwingConstants.LEFT);
		lblLchSng.setFont(new Font("Dialog", Font.BOLD, 14));
		lblLchSng.setBounds(10, 0, 150, 40);
		panelHistoryLogin.add(lblLchSng);

		JPanel panelListFriend = new JPanel();
		panelListFriend.addMouseListener(new PanelButtonMouseAdapter(panelListFriend) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelListF);
			}
		});
		panelListFriend.setBackground(Color.LIGHT_GRAY);
		panelListFriend.setForeground(Color.WHITE);
		panelListFriend.setLayout(null);
		panelListFriend.setBounds(0, 220, 170, 40);
		panel.add(panelListFriend);

		JLabel lblBnB = new JLabel("Bạn bè");
		lblBnB.setHorizontalAlignment(SwingConstants.LEFT);
		lblBnB.setFont(new Font("Dialog", Font.BOLD, 14));
		lblBnB.setBounds(10, 0, 150, 40);
		panelListFriend.add(lblBnB);

		JPanel panelMainContent = new JPanel();
		panelMainContent.setLayout(null);
		panelMainContent.setBackground(new Color(240, 240, 240));
		panelMainContent.setBounds(175, 0, 430, 460);
		panelMainContent.add(panelListF);
		panelMainContent.add(panelHis);
		panelMainContent.add(panelUpdate);
		panelMainContent.add(panelPW);
		menuClicked(panelUpdate);
		contentPane.add(panelMainContent);

	}

	public void menuClicked(JPanel panel) {
		panelUpdate.setVisible(false);
		panelHis.setVisible(false);
		panelListF.setVisible(false);
		panelPW.setVisible(false);
		panel.setVisible(true);
	}

	private class PanelButtonMouseAdapter extends MouseAdapter {
		JPanel panel;

		public PanelButtonMouseAdapter(JPanel panel) {
			this.panel = panel;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			panel.setBackground(new Color(240, 240, 240));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			panel.setBackground(Color.LIGHT_GRAY);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			panel.setBackground(new Color(160, 160, 160));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			panel.setBackground(new Color(240, 240, 240));
		}
	}
}
