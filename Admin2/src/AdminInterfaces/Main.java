package AdminInterfaces;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Server.Classes.User;
import Server.Classes.Group;
import Server.Classes.InforUser;
import Server.Classes.Message;
import Server.Controllers.GroupController;
import Server.Controllers.MessageController;
import Server.Controllers.UserController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Main extends JFrame {

	private JPanel contentPane;
	private JPanel panelMainContent;

	private ImageIcon iconManagement = new ImageIcon(Main.class.getResource("/Image/UserManagement.png"));
	private ImageIcon iconLoginHis = new ImageIcon(Main.class.getResource("/Image/LoginHistory.png"));
	private ImageIcon iconGroupChat = new ImageIcon(Main.class.getResource("/Image/GroupChat.png"));
	private ImageIcon iconLogo = new ImageIcon(Main.class.getResource("/Image/Logo.png"));

	private PanelManagement PanelManage;
	private LoginHistory PanelLoginHis;
	private GroupChat PanelGroupChat;

	private UserController userController;
	private GroupController groupController;
	private MessageController messageController;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Attribute: Boolean - Waiting Client Response True if server are waiting for
	 * client's response False if server are not waiting, or just got response
	 */
	private boolean waitingClientResponse;

	/**
	 * Attribute: HashMap - Users Online users list
	 */
	private HashMap<Socket, User> users;

	/**
	 * Attribute: HashMap - Accounts database
	 */

//	private HashMap<String, String> accounts;

	private ArrayList<User> accounts;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		userController = new UserController();
		groupController = new GroupController();
		messageController = new MessageController();

//		accounts = userController.getAllUsers();

		initUI();

	}

	// Setting display component content
	public void menuClicked(JPanel panel) {
		PanelManage.setVisible(false);
		PanelLoginHis.setVisible(false);
		PanelGroupChat.setVisible(false);

		panel.setVisible(true);
	}

	/**
	 * Wait for clients
	 */
	private void waitClients() {
		users = new HashMap<>();
		waitingClientResponse = false;

		try {
			try (ServerSocket serverSocket = new ServerSocket(8080)) {
				while (true) {
					Socket client = serverSocket.accept();
					if (client == null)
						break;
//					Thread receiveClientMessage = new Thread(() -> receiveClientMessages(client));
//					receiveClientMessage.start();
				}
			}
		} catch (Exception exception) {
//			addLogs("Tạo server thất bại.");
		}
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

	private void initUI() {
		setResizable(false);

		// Setting content panel
		setTitle("Administrator");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String[] ObjButtons = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Confirmation",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setBounds(100, 100, 1350, 520);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		// Init component
		PanelManage = new PanelManagement(this);
		PanelLoginHis = new LoginHistory();
		PanelGroupChat = new GroupChat();

		// Panel Logo
		JPanel panelAdmin = new JPanel();
		panelAdmin.setBackground(Color.LIGHT_GRAY);
		panelAdmin.setLayout(null);

		JLabel lblIconLogo = new JLabel("");
		lblIconLogo.setForeground(Color.BLACK);
		lblIconLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconLogo.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIconLogo.setBounds(0, 0, 250, 140);
		Image img = iconLogo.getImage(); // transform it
		Image newimg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		lblIconLogo.setIcon(new ImageIcon(newimg));
		panelAdmin.add(lblIconLogo);

		// Panel users management
		JPanel panelManagement = new JPanel();
		panelManagement.addMouseListener(new PanelButtonMouseAdapter(panelManagement) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(PanelManage);
			}
		});
		panelManagement.setBackground(Color.LIGHT_GRAY);
		panelManagement.setBounds(0, 150, 250, 60);
		panelAdmin.add(panelManagement);
		panelManagement.setLayout(null);

		JLabel lblManagement = new JLabel("   Quản lý người dùng");
		lblManagement.setHorizontalAlignment(SwingConstants.LEFT);
		lblManagement.setForeground(Color.BLACK);
		lblManagement.setFont(new Font("Dialog", Font.BOLD, 14));
		lblManagement.setBounds(5, 0, 245, 60);
		Image img1 = iconManagement.getImage(); // transform it
		Image newimg1 = img1.getScaledInstance(36, 47, java.awt.Image.SCALE_SMOOTH);
		lblManagement.setIcon(new ImageIcon(newimg1));
		panelManagement.add(lblManagement);

		// Panel Login History
		JPanel panelLoginHis = new JPanel();
		panelLoginHis.addMouseListener(new PanelButtonMouseAdapter(panelLoginHis) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(PanelLoginHis);
			}
		});
		panelLoginHis.setBackground(Color.LIGHT_GRAY);
		panelLoginHis.setBounds(0, 210, 250, 60);
		panelAdmin.add(panelLoginHis);
		panelLoginHis.setLayout(null);

		JLabel lblLoginHis = new JLabel("Lịch sử đăng nhập");
		lblLoginHis.setForeground(Color.BLACK);
		lblLoginHis.setFont(new Font("Dialog", Font.BOLD, 14));
		lblLoginHis.setBounds(2, 0, 248, 60);
		Image img2 = iconLoginHis.getImage(); // transform it
		Image newimg2 = img2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		lblLoginHis.setIcon(new ImageIcon(newimg2));
		panelLoginHis.add(lblLoginHis);

		// Panel Group Chat
		JPanel panelGroupChat = new JPanel();
		panelGroupChat.addMouseListener(new PanelButtonMouseAdapter(panelGroupChat) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(PanelGroupChat);
			}
		});
		panelGroupChat.setLayout(null);
		panelGroupChat.setBackground(Color.LIGHT_GRAY);
		panelGroupChat.setBounds(0, 270, 250, 60);
		panelAdmin.add(panelGroupChat);

		JLabel lblGroupChat = new JLabel("  Quản lý nhóm chat");
		lblGroupChat.setForeground(Color.BLACK);
		lblGroupChat.setFont(new Font("Dialog", Font.BOLD, 14));
		lblGroupChat.setBounds(5, 0, 245, 60);
		Image img3 = iconGroupChat.getImage(); // transform it
		Image newimg3 = img3.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		lblGroupChat.setIcon(new ImageIcon(newimg3));
		panelGroupChat.add(lblGroupChat);

		// Panel components content
		panelMainContent = new JPanel();
		panelMainContent.setLayout(null);

		panelMainContent.add(PanelManage);
		panelMainContent.add(PanelLoginHis);
		panelMainContent.add(PanelGroupChat);
		menuClicked(PanelManage);

		// Group Panel
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panelAdmin, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(panelMainContent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panelAdmin, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
				.addComponent(panelMainContent, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE));
		contentPane.setLayout(gl_contentPane);
	}

	public boolean containUser(String username) {
		for (User user : users.values())
			if (user.getInfor().getUsername().equals(username))
				return true;
		return false;
	}

	/**
	 * Update history Login
	 *
	 *
	 */

	/**
	 * Add online user
	 * 
	 * @param socket   Socket
	 * @param username String
	 */
	public void addUserLogin(Socket socket, String username) {
		User getUser = userController.getUserByUsername(username);

		users.put(socket, getUser);
		sendUserList();

		System.out.println(username + "connection success!");
	}

	/**
	 * Remove online user
	 * 
	 * @param socket Socket
	 */
	public void removeUser(Socket socket) {
		users.remove(socket);
		sendUserList();
	}

	// Check friend
	public Boolean checkFriend(String username, String friend, ArrayList<String> friends) {
		for (String e : friends) {
			if (e.equals(friend))
				return true;
		}
		return false;
	}

	/**
	 * Send online users list to every user
	 */
	public void sendUserList() {
//		for (Socket socket : users.keySet()) {
//			StringBuilder userList = new StringBuilder("Command_UserList");
//			for (User user : users.values())
//				if (!user.getInfor().getUsername().equals(users.get(socket).getInfor().getUsername())) {
//					userList.append("`").append(user.getInfor().getUsername());
//				}
//			sendMessage(socket, userList.toString());
//		}

		for (int i = 0; i < users.size(); i++) {

		}
	}

	public ArrayList<User> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<User> accounts) {
		this.accounts = accounts;
	}

//
//	/**
//	 * Send a message to client
//	 * 
//	 * @param client  Socket
//	 * @param message String
//	 */
//	public void sendMessage(Socket client, String message) {
//		try {
////			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
////			bufferedWriter.write(message);
////			bufferedWriter.newLine();
////			bufferedWriter.flush();
//
//		} catch (Exception exception) {
//			addLogs("Client ngắt kết nối");
//			removeUser(client);
//		}
//	}

//	/**
//	 * Receive and Process Message from client
//	 * 
//	 * @param client Socket
//	 */

//	private void receiveClientMessages(Socket client) {
//		try {
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//
//			while (true) {
//				String receivedMessage = bufferedReader.readLine();
//
//				if (receivedMessage.contains("Command_CloseConnect")) {
//					String[] str = receivedMessage.split("`");						
//					addLogs(client, str[1] + " đăng xuất");
//					break;
//
//				} else if (receivedMessage.contains("Command_SignedIn")) {
//					String[] str = receivedMessage.split("`");
//					addUserLogin(client, str[1]);
//					addLogs(client, str[1] + " đã đăng nhập");
//
//				} else if (receivedMessage.contains("Command_AccountVerify")) {
//					String[] str = receivedMessage.split("`");
//					String query = accounts.get(str[1]);
//					if (query == null) {
//						sendMessage(client, "Command_AccountVerifyFailed");
//					} else if (query.equals(str[2])) {
//						if (containUser(str[1]))
//							sendMessage(client, "Command_AccountVerifyAlready");
//						else
//							sendMessage(client, "Command_AccountVerifyAccepted");
//					} else {
//						sendMessage(client, "Command_AccountVerifyFailed");
//					}
//
//				} else if (receivedMessage.contains("Command_CreateAccount")) {
//					String[] str = receivedMessage.split("`");
//					String query = accounts.get(str[1]);
//					if (query == null) {
//						accounts.put(str[1], str[2]);
//						saveAccounts();
//						sendMessage(client, "Command_CreateAccountAccepted");
//					} else {
//						sendMessage(client, "Command_CreateAccountFailed");
//					}
//
//				} else if (receivedMessage.contains("Command_SendMessage")) {
//					String[] str = receivedMessage.split("`");
//					if (containUser(str[1])) {
//						for (Socket socket : users.keySet()) {
//							if (users.get(socket).getInfor().getUsername().equals(str[1]))
//								sendMessage(socket,
//										"Command_Message`" + users.get(client).getInfor().getUsername() + "`" + str[2]);
//						}
//						sendMessage(client, "Command_SendMessageAccepted");
//					} else {
//						sendMessage(client, "Command_SendMessageFailed");
//					}
//
//				} else if (receivedMessage.contains("Command_Accepted")) {
//					waitingClientResponse = false;
//
//				} else if (receivedMessage.contains("Command_SendFile")) {
//					String[] str = receivedMessage.split("`");
//					if (containUser(str[1])) {
//						sendMessage(client, "Command_SendMessageAccepted");
//
//						DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
//						byte[] data = new byte[dataInputStream.readInt()];
//						dataInputStream.readFully(data, 0, data.length);
//
//						for (Socket socket : users.keySet()) {
//							if (users.get(socket).getInfor().getUsername().equals(str[1])) {
//								waitingClientResponse = true;
//								sendMessage(socket,
//										"Command_File`" + users.get(client).getInfor().getUsername() + "`" + str[2]);
//								while (waitingClientResponse)
//									System.out.print("");
//								DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//								dataOutputStream.writeInt(data.length);
//								dataOutputStream.write(data);
//							}
//						}
//					} else {
//						sendMessage(client, "Command_SendMessageFailed");
//					}
//
//				} else if (receivedMessage.contains("Command_AddFriendRequest")) {
//					// ******************
//					String[] str = receivedMessage.split("`");
//					if (str[1].equals(users.get(client).getInfor().getUsername()))
//						sendMessage(client, "Command_AddFriendRequestSelf");
//
//					else if (users.get(client).getFriend().contains(str[1]))
//						sendMessage(client, "Command_AddFriendRequestIsFriend");
//
//					else if (accounts.containsKey(str[1])) {
//						for (Socket socket : users.keySet())
//							if (users.get(socket).getInfor().getUsername().equals(str[1])) {
//								if (!users.get(socket).getAddFriendRequest()
//										.contains(users.get(client).getInfor().getUsername())) {
//									users.get(socket).addAddFriendRequest(users.get(client).getInfor().getUsername());
////									String str2 = "Command_NewAddFriendRequest`";								
////									for (int i = 0; i < users.get(socket).getAddFriendRequest().size() - 1; i++)
////										str2 += users.get(socket).getAddFriendRequest().get(i) + "`";
////									if(users.get(socket).getAddFriendRequest().size() > 0)
////										str2 += users.get(socket).getAddFriendRequest().get(users.get(socket).getAddFriendRequest().size() - 1);
////									
////									sendMessage(socket, str2);
//									sendMessage(socket, "Command_NewAddFriendRequest`"
//											+ users.get(client).getInfor().getUsername());
//								}
//							}
//						sendMessage(client, "Command_AddFriendRequestAccepted");
//					} else
//						sendMessage(client, "Command_AddFriendRequestFailed");
//
//				} else if (receivedMessage.contains("Command_AcceptAddFriendRequest")) {
//					String[] str = receivedMessage.split("`");
//					users.get(client).addFriend(str[1]);
//					sendMessage(client, "Command_deleteAddFriendRequest`"
//							+ users.get(client).getAddFriendRequest().indexOf(str[1]));
//					users.get(client).deleteAddFriendRequest(str[1]);
//
//					for (Socket socket : users.keySet())
//						if (users.get(socket).getInfor().getUsername().equals(str[1])) {
//							users.get(socket).addFriend(users.get(client).getInfor().getUsername());
//
//						}
//
//				} else if (receivedMessage.contains("Command_deleteAddFriendRequest")) {
//					String[] str = receivedMessage.split("`");
//					sendMessage(client, "Command_deleteAddFriendRequest`"
//							+ users.get(client).getAddFriendRequest().indexOf(str[1]));
//					users.get(client).deleteAddFriendRequest(str[1]);
//
//				} else if (receivedMessage.contains("Command_ShowFriendListRequest")) {
//					String str = "Command_ShowFriendListRequest`";
//
//					for (int i = 0; i < users.get(client).getFriend().size() - 1; i++)
//						str += users.get(client).getFriend().get(i) + "`";
//					if (users.get(client).getFriend().size() > 0)
//						str += users.get(client).getFriend().get(users.get(client).getFriend().size() - 1);
//
//					sendMessage(client, str);
//
//				} else if (receivedMessage.contains("Command_unfriend")) {
//					String[] str = receivedMessage.split("`");
//					sendMessage(client, "Command_unfriend`" + users.get(client).getFriend().indexOf(str[1]));
//					users.get(client).deleteFriend(str[1]);
//					for (Socket socket : users.keySet())
//						if (users.get(socket).getInfor().getUsername().equals(str[1])) {
//							sendMessage(socket, "Command_unfriend`" + users.get(socket).getFriend()
//									.indexOf(users.get(client).getInfor().getUsername()));
//							users.get(socket).deleteFriend(users.get(client).getInfor().getUsername());
//						}
//
//				} else {
//					addLogs(client, receivedMessage);
//				}
//			}
//
//			bufferedReader.close();
//			removeUser(client);
//			client.close();
//		} catch (Exception exception) {
//			addLogs(client, "Client ngắt kết nối");
//			removeUser(client);
//		}
//	}
}
