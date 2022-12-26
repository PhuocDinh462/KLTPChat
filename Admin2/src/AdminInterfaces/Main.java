package AdminInterfaces;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Server.Classes.Group;
import Server.Classes.Message;
import Server.Classes.User;
import Server.Controllers.GroupController;
import Server.Controllers.MessageController;
import Server.Controllers.UserController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * @Attribute: Boolean - Waiting Client Response True if server are waiting for
	 *             client's response False if server are not waiting, or just got
	 *             response
	 */
	private boolean waitingClientResponse;

	/**
	 * @Attribute: int - port
	 *
	 */
	private static int port = 8080;

	/**
	 * @Attribute: HashMap - Users Online users list
	 */
	private HashMap<Socket, User> users;

	/**
	 * @Attribute: HashMap - Accounts database
	 */
	private ArrayList<User> accounts;

	/**
	 * @Attribute: HashMap - Groups database
	 */
	private ArrayList<Group> groups;

	/**
	 * @Attribute: username
	 */
	private int getAccountIndex(String username) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getInfor().getUsername().equals(username))
				return i;
		}
		return -1;
	}

	private boolean containUsername(String username) {
		for (User user : accounts) {
			if (user.getInfor().getUsername().equals(username))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Main();
					frame.setVisible(true);
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
		accounts = userController.getAllUsers();
		groups = groupController.getAllGroups();
		initUI();
		Thread openServer = new Thread(() -> waitClients());
		openServer.start();
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
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				System.out.println("\nServer đang chạy tại port " + port + "...");
				while (true) {
					Socket client = serverSocket.accept();
					if (client == null)
						break;
					Thread receiveClientMessage = new Thread(() -> receiveClientMessages(client));
					receiveClientMessage.start();
				}
			}
		} catch (Exception exception) {
			System.out.println("Không thể tạo server vì có một server khác đang chạy!");
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

				if (PromptResult == 0) {
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
		PanelManage = new PanelManagement();
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
	 * Add online user
	 * 
	 * @param socket   Socket
	 * @param username String
	 */
	public void addUserLogin(Socket socket, String username) {
		User getUser = userController.getUserByUsername(username);

		users.put(socket, getUser);
		sendUserList(socket, getUser);
		sendGroupList(socket, getUser);

		System.out.println(username + "connection success!");
	}

	/**
	 * Remove online user
	 * 
	 * @param socket Socket
	 */
	public void removeUser(Socket socket) {
		users.remove(socket);
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
	 * Send user list to user
	 * 
	 * @param socket - Socket
	 * @param User   - user
	 */
	public void sendUserList(Socket socket, User user) {
		StringBuilder userList = new StringBuilder("Command_UserList");

		for (int i = 0; i < user.getFriend().size(); i++)
			userList.append("`").append(user.getFriend().get(i));

		sendMessage(socket, userList.toString());
	}

	/**
	 * Send group list to user
	 * 
	 * @param socket - Socket
	 * @param User   - user
	 */
	public void sendGroupList(Socket socket, User user) {
		StringBuilder groupList = new StringBuilder("Command_GroupList");

		for (int i = 0; i < groups.size(); i++)
			if (groups.get(i).getlistUsers().contains(user.getInfor().getUsername()))
				groupList.append("`").append(groups.get(i).getGroupName());

		sendMessage(socket, groupList.toString());
	}

	public ArrayList<User> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<User> accounts) {
		this.accounts = accounts;
	}

	public GroupController getGroupController() {
		return groupController;
	}

	public MessageController getMessageController() {
		return messageController;
	}

	public UserController getUserController() {
		return userController;
	}

//
//	/**
//	 * Send a message to client
//	 * 
//	 * @param client  Socket
//	 * @param message String
//	 */
	public void sendMessage(Socket client, String message) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();

		} catch (Exception exception) {
			removeUser(client);
		}
	}

//	public void sendMessage(Socket client, Message message) {
//		try {
//			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//			 String array[] = new String[message.size()];// ArrayList to String Array conversion                
//	            for(int j =0;j<message.size();j++){  
//	              array[j] = message.get(j);  
//	            }  
//			bufferedWriter.write(message);
//			bufferedWriter.newLine();
//			bufferedWriter.flush();
//
//		} catch (Exception exception) {
//			removeUser(client);
//		}
//	}
//	/**
//	 * Receive and Process Message from client
//	 * 
//	 * @param client Socket
//	 */

	private void receiveClientMessages(Socket client) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

			while (true) {
				String receivedMessage = bufferedReader.readLine() + "";
				if (receivedMessage != null) {
					System.out.println(receivedMessage);
				}
				if (receivedMessage.contains("Command_CloseConnect")) {
					sendMessage(client, "Command_CloseConnect");
					int i = getAccountIndex(users.get(client).getInfor().getUsername());
					accounts.get(i).getInfor().setStatus(false);
					bufferedReader.close();
					removeUser(client);
					client.close();

				} else if (receivedMessage.contains("Command_SignedIn")) {
					String[] str = receivedMessage.split("`");
					addUserLogin(client, str[1]);

					// Gửi danh sách lời mời kết bạn cho client:
					String str2 = "Command_NewAddFriendRequest`";
					ArrayList<String> listData = users.get(client).getListAddFriend();
					for (String string : listData) {
						str2 += string + "`";
					}
					sendMessage(client, str2);
				}

				else if (receivedMessage.contains("Command_AccountVerify")) {
					String[] str = receivedMessage.split("`");
					int i = getAccountIndex(str[1]);
					if (i == -1) {
						sendMessage(client, "Command_AccountVerifyFailed");
					} else if (accounts.get(i).getInfor().getPassword().equals(str[2])) {
						if (containUser(str[1]))
							sendMessage(client, "Command_AccountVerifyAlready");
						else if (accounts.get(i).getInfor().getBlocked())
							sendMessage(client, "Command_AccountVerifyBlocked");
						else {
							sendMessage(client, "Command_AccountVerifyAccepted");
							accounts.get(i).getInfor().setStatus(true);
							accounts.get(i).getTimeLogin().add(
									DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));
							userController.addLogin(accounts.get(i).getId());
						}
					} else {
						sendMessage(client, "Command_AccountVerifyFailed");
					}

				}

				else if (receivedMessage.contains("Command_CreateAccount")) {
					String[] str = receivedMessage.split("`");

					User createUser = new User(str[1], str[2], str[3], str[4], str[5], str[6], str[7]);
					Boolean created = userController.create(createUser);
					if (created) {
						sendMessage(client, "Command_CreateAccountAccepted");
					} else {
						sendMessage(client, "Command_CreateAccountFailed");
					}

				}

				else if (receivedMessage.contains("Command_AddFriendRequest")) {
					// ******************
					String[] str = receivedMessage.split("`");
					if (str[1].equals(users.get(client).getInfor().getUsername()))
						sendMessage(client, "Command_AddFriendRequestSelf");

					else if (users.get(client).getFriend().contains(str[1]))
						sendMessage(client, "Command_AddFriendRequestIsFriend");

					else if (containUsername(str[1])) {
						// Gửi lời mời kết bạn đến socket của client nếu người đó đang onl:
						for (Socket socket : users.keySet())
							if (users.get(socket).getInfor().getUsername().equals(str[1])) {
								if (!users.get(socket).getListAddFriend()
										.contains(users.get(client).getInfor().getUsername())) {
									users.get(socket).addAddFriendRequest(users.get(client).getInfor().getUsername());

									sendMessage(socket, "Command_NewAddFriendRequest`"
											+ users.get(client).getInfor().getUsername());
								}
							}

						// Lưu vào account:
						accounts.get(getAccountIndex(str[1]))
								.addAddFriendRequest(users.get(client).getInfor().getUsername());

						sendMessage(client, "Command_AddFriendRequestAccepted");

						// Lưu vào db:
						userController.addRequestFriend(userController.getUserByUsername(str[1]).getId(),
								users.get(client).getInfor().getUsername());
					} else
						sendMessage(client, "Command_AddFriendRequestFailed");

				}

				else if (receivedMessage.contains("Command_AcceptAddFriendRequest")) {
					String[] str = receivedMessage.split("`");

					// Xóa lời mời kết bạn trên interface:
					sendMessage(client,
							"Command_deleteAddFriendRequest`" + users.get(client).getListAddFriend().indexOf(str[1]));

					// Xóa lời mời kết bạn trong users:
					users.get(client).deleteAddFriendRequest(str[1]);

					// Xóa lời mời kết bạn trong accounts:
					accounts.get(getAccountIndex(users.get(client).getInfor().getUsername()))
							.deleteAddFriendRequest(str[1]);

					// Xóa lời mời kết bạn trong db:
					userController.deleteRequestFriend(users.get(client).getId(), str[1]);

					// Thêm bạn vào accounts:
					accounts.get(getAccountIndex(str[1])).addFriend(users.get(client).getInfor().getUsername());
					accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())).addFriend(str[1]);

					for (Socket socket : users.keySet())
						if (users.get(socket).getInfor().getUsername().equals(str[1])) {
							// Thêm bạn vào users:
							users.get(socket).addFriend(users.get(client).getInfor().getUsername());
							users.get(client).addFriend(str[1]);

							// Thêm vào danh sách người liên hệ:
							sendUserList(socket,
									accounts.get(getAccountIndex(users.get(socket).getInfor().getUsername())));
						}

					// Thêm vào danh sách người liên hệ:
					sendUserList(client, accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())));

					// Thêm bạn vào db:
					userController.addFriend(accounts.get(getAccountIndex(str[1])).getId(),
							users.get(client).getInfor().getUsername());
					userController.addFriend(
							accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())).getId(), str[1]);
				}

				else if (receivedMessage.contains("Command_deleteAddFriendRequest")) {
					String[] str = receivedMessage.split("`");

					// Xóa lời mời kết bạn trong users:
					sendMessage(client,
							"Command_deleteAddFriendRequest`" + users.get(client).getListAddFriend().indexOf(str[1]));
					users.get(client).deleteAddFriendRequest(str[1]);

					// Xóa lời mời kết bạn trong accounts:
					accounts.get(getAccountIndex(users.get(client).getInfor().getUsername()))
							.deleteAddFriendRequest(str[1]);

					// Xóa lời mời kết bạn trong db:
					userController.deleteRequestFriend(users.get(client).getId(), str[1]);
				}

				// Hiển thị danh sách bạn bè
				else if (receivedMessage.contains("Command_ShowFriendList")) {
					String str = "Command_ShowFriendList`";

					for (int i = 0; i < accounts.get(getAccountIndex(users.get(client).getInfor().getUsername()))
							.getFriend().size(); i++)
						str += accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())).getFriend()
								.get(i) + "`";

					sendMessage(client, str);

				}

				// Hủy kết bạn
				else if (receivedMessage.contains("Command_unfriend")) {
					String[] str = receivedMessage.split("`");

					// Xóa bạn trên interface:
					sendMessage(client,
							"Command_unfriend`"
									+ accounts.get(getAccountIndex(users.get(client).getInfor().getUsername()))
											.getFriend().indexOf(str[1]));

					// Xóa bạn trong account:
					accounts.get(getAccountIndex(str[1])).deleteFriend(users.get(client).getInfor().getUsername());
					accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())).deleteFriend(str[1]);

					// Xóa bạn trong users:
					users.get(client).deleteFriend(str[1]);

					for (Socket socket : users.keySet())
						if (users.get(socket).getInfor().getUsername().equals(str[1])) {
							// Xóa bạn trên interface:
							sendMessage(socket,
									"Command_unfriend`"
											+ accounts.get(getAccountIndex(users.get(socket).getInfor().getUsername()))
													.getFriend().indexOf(users.get(client).getInfor().getUsername()));

							// Xóa bạn trong users:
							users.get(socket).deleteFriend(users.get(client).getInfor().getUsername());
							users.get(client).deleteFriend(users.get(socket).getInfor().getUsername());

							// Xóa trong danh sách người liên hệ:
							sendUserList(socket,
									accounts.get(getAccountIndex(users.get(socket).getInfor().getUsername())));
						}

					// Xóa trong danh sách người liên hệ:
					sendUserList(client, accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())));

					// Xóa bạn trong db:
					userController.deleteFriend(accounts.get(getAccountIndex(str[1])).getId(),
							users.get(client).getInfor().getUsername());
					userController.deleteFriend(
							accounts.get(getAccountIndex(users.get(client).getInfor().getUsername())).getId(), str[1]);
				}

				else if (receivedMessage.contains("Command_CreateNewGroup")) {
					String[] str = receivedMessage.split("`");
					String userID = users.get(client).getId();
					Group createGroup = new Group(str[1], userID);
					Boolean created = groupController.create(createGroup);
					if (created) {
						sendMessage(client, "Command_CreateGroupAccepted");
					} else {
						sendMessage(client, "Command_CreateGroupFailed");
					}
					//
				} else if (receivedMessage.contains("Command_SendMessage")) {
					String[] str = receivedMessage.split("`");
					if (containUsername(str[1])) {
						System.out.print("người nhận: " + str[1] + " người gửi: " + str[3]);
						for (Socket socket : users.keySet()) {
							if (users.get(socket).getInfor().getUsername().equals(str[1])) {
								sendMessage(socket, "Command_Message`" + users.get(client).getInfor().getUsername()// lấy
																													// thông
																													// tin
																													// người
																													// gửi
																													// tin
																													// nhắn
																													// kèm
																													// vào
																													// message
										+ "`" + str[2]);// gửi dạng command + người nhận + nội dung
								// lưu message gửi thành công vào database
								messageController.create(new Message(str[3], str[1], str[2]));
							}

						}
						sendMessage(client, "Command_SendMessageAccepted");

					} else {
						sendMessage(client, "Command_SendMessageFailed");
					}
					// gửi File
				} else if (receivedMessage.contains("Command_SendFile")) {
					String[] str = receivedMessage.split("`");
					if (containUser(str[1])) {
						sendMessage(client, "Command_SendMessageAccepted");// server kiểm tra tồn tại người nhận hay ko
																			// có thì trả về lệnh hệ thống
																			// messageaccepted

						DataInputStream dataInputStream = new DataInputStream(client.getInputStream());//
						byte[] data = new byte[dataInputStream.readInt()];
						dataInputStream.readFully(data, 0, data.length);

						for (Socket socket : users.keySet()) {
							if (users.get(socket).getInfor().getUsername().equals(str[1])) {
								waitingClientResponse = true;
								sendMessage(socket,
										"Command_File`" + users.get(client).getInfor().getUsername() + "`" + str[2]);
								while (waitingClientResponse)
									System.out.print("");
								DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
								dataOutputStream.writeInt(data.length);
								dataOutputStream.write(data);
							}
						}
					} else {
						sendMessage(client, "Command_SendMessageFailed");
					}
				} else if (receivedMessage.contains("Command_MessageHistory")) {
					String[] str = receivedMessage.split("`");
					MessageController messGetFdataBase = new MessageController();
					if (str[2].equals("(Tin nhắn mới)")) {
						str[2] = str[2].replace(" (Tin nhắn mới)", "");
					}

					ArrayList<Message> historyMess = messGetFdataBase.findMessageBySender(str[1], str[2]);
					Object[] objArr = historyMess.toArray();
					String[] stringArray = Arrays.copyOf(objArr, objArr.length, String[].class);
					System.out.print("710" + stringArray);
					for (Socket socket : users.keySet()) {
						if (users.get(socket).getInfor().getUsername().equals(str[1])) {
							sendMessage(socket, "Command_SendHistoryMessage`" + "`" + str[1] + "`" + stringArray);
						}
					}

				}

				else if (receivedMessage.contains("Command_ForgotPassword")) {
					String[] str = receivedMessage.split("`");
					int i = getAccountIndex(str[1]);
					System.out.println(str[1]);
					System.out.println("i = " + i);
					System.out.println(accounts.get(1).getInfor().getUsername());

					if (i == -1)
						sendMessage(client, "Command_ForgotPasswordFail");
					else {
						String email = accounts.get(i).getInfor().getEmail();
						if (email.isBlank())
							sendMessage(client, "Command_ForgotPasswordInvalid");
						else {
							String newPassword = ForgotPassword.randomPassword(6);
							accounts.get(i).getInfor().setPassword(newPassword);
							userController.updatePassword(str[1], newPassword);
							ForgotPassword.sendEmail("kltpchat@gmail.com", "bxqokcenihyxekfr", email, "Quên mật khẩu",
									"Chào " + str[1] + ".\nMật khẩu mới của bạn là: " + newPassword);
							sendMessage(client, "Command_ForgotPasswordSuccessful");
						}
					}
				}

				else if (receivedMessage.contains("Command_ChangePassword")) {
					String[] str = receivedMessage.split("`");
					String username = users.get(client).getInfor().getUsername();
					int i = getAccountIndex(username);

					if (!accounts.get(i).getInfor().getPassword().equals(str[1]))
						sendMessage(client, "Command_ChangePasswordFailed");

					else {
						// Đổi mật khẩu trong accounts:
						accounts.get(i).getInfor().setPassword(str[2]);
						// Đổi mật khẩu trên db:
						userController.updatePassword(username, str[2]);
						// Gửi thông báo về client:
						sendMessage(client, "Command_ChangePasswordSuccessful");
					}
				}

			}

		} catch (Exception exception) {
			removeUser(client);
		}
	}
}
