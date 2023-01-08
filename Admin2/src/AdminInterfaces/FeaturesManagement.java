package AdminInterfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Server.Classes.User;
import Server.Controllers.UserController;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class FeaturesManagement extends JFrame {

	private ImageIcon iconUser = new ImageIcon(Main.class.getResource("/Image/user.png"));
	private JPanel contentPane;
	private JButton btnExit;

	private UpdateInformation panelUpdate;
	private LoginHistoryAUser panelHis;
	private ListFriend panelListF;
	private UpdatePassword panelPW;

	private UserController userController;

	private User user;

	public FeaturesManagement(String idUser) {
		userController = new UserController();
		this.user = userController.getUserById(idUser);

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

		panelListF = new ListFriend(user);
		panelHis = new LoginHistoryAUser(user);
		panelUpdate = new UpdateInformation(user);
		panelPW = new UpdatePassword(user);

		String[] features = { "Cập nhật tài khoản", "Cập nhật mật khẩu", "Lịch sử đăng nhập", "Bạn bè" };

		btnExit = new JButton("Thoát");
		btnExit.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnExit.setBounds(40, 420, 90, 25);
		panel.add(btnExit);

		JPanel panelUDIF = new JPanel();
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
		Image img = iconUser.getImage(); // transform it
		Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);

		JLabel lblUsername = new JLabel(user.getInfor().getUsername());
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsername.setBounds(40, 60, 90, 20);
		panel.add(lblUsername);

		// Button features
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		panelUDIF.addMouseListener(new PanelButtonMouseAdapter(panelUDIF) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelUpdate);
			}
		});

		panelHistoryLogin.addMouseListener(new PanelButtonMouseAdapter(panelHistoryLogin) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelHis);
			}
		});

		panelUDPass.addMouseListener(new PanelButtonMouseAdapter(panelUDPass) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelPW);
			}
		});

		panelListFriend.addMouseListener(new PanelButtonMouseAdapter(panelListFriend) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelListF);
			}
		});

		JPanel panelUser = new JPanel();
		panelUser.setBackground(SystemColor.window);
		panelUser.setForeground(SystemColor.desktop);
		panelUser.setBounds(0, 0, 170, 100);
		panel.add(panelUser);
		panelUser.setLayout(null);

		JLabel lblIconImg = new JLabel();
		lblIconImg.setBounds(60, 10, 50, 50);
		panelUser.add(lblIconImg);
		lblIconImg.setIcon(new ImageIcon(newimg));
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
