package Client;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.net.*;
import java.io.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.MatteBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//BUTTON RENDERER CLASS
class ButtonRenderer extends JButton implements TableCellRenderer {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

//CONSTRUCTOR
	public ButtonRenderer() {
		// SET BUTTON PROPERTIES
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row,
			int col) {

		String[] str = obj.toString().split(":");
		// SET PASSED OBJECT AS BUTTON TEXT
		setText(str[0]);

		return this;
	}
}

//BUTTON EDITOR CLASS
class ButtonEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton btn;
	private String lbl;
	private String command;
	private Boolean clicked;

	public ButtonEditor(JTextField txt) {
		super(txt);

		btn = new JButton();
		btn.setOpaque(true);

		// WHEN BUTTON IS CLICKED
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fireEditingStopped();
			}
		});
	}

//OVERRIDE A COUPLE OF METHODS
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {
		String[] str = obj.toString().split(":");
		lbl = str[0];

		if (str.length > 1)
			command = str[1];

		btn.setText(lbl);

		clicked = true;
		return btn;
	}

//IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
	@Override
	public Object getCellEditorValue() {

		if (clicked) {
			if (!command.isBlank())
				Main.sendMessage(command);
		}
		// SET IT TO FALSE NOW THAT ITS CLICKED
		clicked = false;
		return new String(lbl);
	}

	@Override
	public boolean stopCellEditing() {

		// SET CLICKED TO FALSE FIRST
		clicked = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Enum: Message Status
	 */
	public enum MessageStatus {
		/**
		 * Waiting for response
		 */
		Waiting,

		/**
		 * Failed cause user is offline
		 */
		Failed,

		/**
		 * Send message accepted
		 */
		Accepted
	}

	/**
	 * @Attribute: MessageStatus - messageStatus Status of Message
	 */
	public static MessageStatus messageStatus;

	/**
	 * @Attribute: Socket - server
	 */
	private static Socket server;

	/**
	 * @Attribute: int - port
	 *
	 */
	private static int port = 8080;

	/**
	 * @Attribute: String username of account
	 */
	public static String username;

	/**
	 * @Attribute: String[] - List of users
	 */
	private static String[] users;

	/**
	 * @Attribute: String[] - List of groups
	 */
	private static String[] groups;

	/**
	 * @Attribute: conversationStatus conversationStatus = true: Chat with friend.
	 *             conversationStatus = false: Chat with group.
	 */
	private static boolean conversationStatus;

	/**
	 * @Attribute: JList - usersList Display List of users
	 */
	private static final JList<String> usersList = new JList<>();
	private static final ArrayList<String> friendsList = new ArrayList<String>();
	/**
	 * @Attribute: JList - groupList Display List of groups
	 */
	private static final JList<String> groupList = new JList<>();

	/**
	 * @Attribute: JLabel - conversationTitle Display who user are chatting with
	 */
	private static JLabel conversationTitle;

	/**
	 * @Attribute: JPanel - conversationPanel Display conversation
	 */
	private static JPanel conversationPanel;

	/**
	 * @Attribute: HashMap - conversations List of conversations
	 */
	private static final HashMap<String, JPanel> conversations = new HashMap<>();
	private JTextField addFriendTextField;
	private static JTable addFriendRequestTable;
	private static JTextField messageTextField;
	private static DefaultTableModel addFriendRequestTableModel;
	private static JButton groupBtn;

//	public boolean containUser(String username) {
//		for (User user : users)
//			if (user.getInfor().getUsername().equals(username))
//				return true;
//		return false;
//	}
	/**
	 * Main function
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {
		if (connectServer())
			new SignIn();
		else
			JOptionPane.showMessageDialog(null, "Máy chủ không hoạt động!", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Connect to Server
	 * 
	 * @return boolean: True if server are available, False if server are not
	 *         available
	 */
	private static boolean connectServer() {
		try {
			server = new Socket("localhost", port);
			Thread receiveServerMessagesThread = new Thread(Main::receiveServerMessages);
			receiveServerMessagesThread.start();
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * Default constructor
	 */
	public Main() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sendMessage("Command_CloseConnect");
			}
		});
		addComponents();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("KLTP Chat App - " + username);
		setVisible(true);
		setResizable(false);
	}

	/**
	 * Add components to Main JFrame
	 */
	public void addComponents() {
		// Content Pane
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		// Left Panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		JLabel userTitle = new JLabel("Người liên hệ");
		userTitle.setBounds(10, 10, 180, 24);
		userTitle.setFont(new Font("Arial", Font.BOLD, 20));

		usersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String friendChat = usersList.getSelectedValue();
				conversationStatus = true;
				changeConversation(friendChat, conversationStatus);
				if (friendChat.contains("Tin nhắn mới)")) {
					friendChat = friendChat.replace(" (Tin nhắn mới)", "");
					sendMessage("Command_MessageHistory`" + username + "`" + friendChat);
				} else {
					sendMessage("Command_MessageHistory`" + username + "`" + friendChat);
				}

				groupBtn.setVisible(false);
			}
		});

//		groupList.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				super.mouseClicked(e);
//				String groupChat = groupList.getSelectedValue();
//				conversationStatus = false;
//				changeConversation(groupChat, conversationStatus);
//				sendMessage("Command_MessageHistory`" + username + "`" + groupChat);
//				groupBtn.setVisible(true);
//			}
//		});
		groupList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String groupChat = groupList.getSelectedValue();
				conversationStatus = false;
				changeConversation(groupChat, conversationStatus);
				if (groupChat.contains("Tin nhắn mới)")) {
					groupChat = groupChat.replace(" (Tin nhắn mới)", "");
					sendMessage("Command_MessageGroupHistory`" + username + "`" + groupChat);
				} else {
					sendMessage("Command_MessageGroupHistory`" + username + "`" + groupChat);
				}
				groupBtn.setVisible(true);
			}
		});

		JScrollPane userScroll = new JScrollPane(usersList);
		userScroll.setBounds(10, 34, 180, 354);
		userScroll.setBorder(new EmptyBorder(10, 0, 0, 0));

		JPanel userPanel = new JPanel();
		userPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		userPanel.setLayout(null);
		userPanel.add(userTitle);
		userPanel.add(userScroll);
		userPanel.setPreferredSize(new Dimension(200, 650));

		leftPanel.add(userPanel);

		// Middle Panel
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		conversationTitle = new JLabel(" ");
		conversationTitle.setBounds(10, 10, 295, 24);
		conversationTitle.setFont(new Font("Arial", Font.BOLD, 20));

		conversationPanel = new JPanel(new BorderLayout());
		conversationPanel.setBackground(Color.WHITE);

		JScrollPane messageScroll = new JScrollPane(conversationPanel);
		messageScroll.setBounds(10, 34, 589, 548);
		messageScroll.setBorder(new EmptyBorder(10, 0, 10, 0));
		messageTextField = new JTextField(20);
		JButton sendButton = new JButton("Gửi");
		sendButton.addActionListener(e -> {
			sendButtonEventHandler(messageTextField.getText());
		});
		getRootPane().setDefaultButton(sendButton);

		JPanel messagePanel = new JPanel();
		messagePanel.setBounds(10, 582, 589, 21);
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
		messagePanel.add(messageTextField);
		messagePanel.add(Box.createHorizontalStrut(5));
		messagePanel.add(sendButton);
		middlePanel.setLayout(null);

		middlePanel.add(conversationTitle);
		middlePanel.add(messageScroll);
		middlePanel.add(messagePanel);

		// Right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(192, 192, 192)));

		JLabel friendTitle = new JLabel("Kết bạn");
		friendTitle.setBounds(10, 10, 180, 24);
		friendTitle.setFont(new Font("Arial", Font.BOLD, 20));

		userScroll.setBorder(new EmptyBorder(10, 0, 0, 0));

		JLabel groupTitle = new JLabel("Nhóm");
		groupTitle.setFont(new Font("Arial", Font.BOLD, 20));
		groupTitle.setBounds(10, 415, 180, 24);
		userPanel.add(groupTitle);

		JScrollPane groupScroll = new JScrollPane((Component) null);
		groupScroll.setBorder(new EmptyBorder(10, 0, 0, 0));
		groupScroll.setBounds(10, 437, 180, 134);
		userPanel.add(groupScroll);

		groupList.setVisibleRowCount(10);
		groupScroll.setColumnHeaderView(groupList);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(192, 192, 192));
		separator.setBounds(10, 403, 180, 2);
		userPanel.add(separator);

		JButton createGroupButton = new JButton("Tạo nhóm");
		createGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateGroup(friendsList);
			}
		});
		createGroupButton.setBounds(10, 582, 180, 21);
		userPanel.add(createGroupButton);

		JPanel friendPanel = new JPanel();
		friendPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		friendPanel.setLayout(null);
		friendPanel.add(friendTitle);
		friendPanel.setPreferredSize(new Dimension(300, 650));

		rightPanel.add(friendPanel, BorderLayout.WEST);

		addFriendTextField = new JTextField();
		addFriendTextField.setBounds(10, 44, 145, 21);
		friendPanel.add(addFriendTextField);
		addFriendTextField.setColumns(10);

		JButton addFriendButton = new JButton("Gửi yêu cầu");
		addFriendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!addFriendTextField.getText().isBlank()) {
					sendMessage("Command_AddFriendRequest`" + addFriendTextField.getText());
					addFriendTextField.setText("");
				}
			}
		});
		addFriendButton.setBounds(178, 41, 112, 24);
		friendPanel.add(addFriendButton);

		JLabel lblLiMiKt = new JLabel("Lời mời kết bạn");
		lblLiMiKt.setFont(new Font("Arial", Font.BOLD, 20));
		lblLiMiKt.setBounds(10, 107, 180, 24);
		friendPanel.add(lblLiMiKt);

		// Add friend request table
		addFriendRequestTableModel = new DefaultTableModel();
		addFriendRequestTableModel.addColumn("Tên tài khoản");
		addFriendRequestTableModel.addColumn("Đồng ý");
		addFriendRequestTableModel.addColumn("Xóa");

		addFriendRequestTable = new JTable(addFriendRequestTableModel);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		addFriendRequestTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		addFriendRequestTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
		addFriendRequestTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JTextField()));
		addFriendRequestTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
		addFriendRequestTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));

		JScrollPane addFriendRequestScrollPane = new JScrollPane(addFriendRequestTable);
		addFriendRequestScrollPane.setBounds(10, 141, 280, 462);
		addFriendRequestScrollPane.setViewportView(addFriendRequestTable);

		friendPanel.add(addFriendRequestScrollPane);

		JButton showFriendButton = new JButton("Bạn bè");
		showFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage("Command_ShowFriendList");
			}
		});
		showFriendButton.setBounds(178, 75, 112, 24);
		friendPanel.add(showFriendButton);

		// Add components to content pane and Settings
		contentPane.add(leftPanel, BorderLayout.LINE_START);
		contentPane.add(middlePanel, BorderLayout.CENTER);

		JButton searchButton = new JButton("🔎︎");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!conversationTitle.getText().isBlank())
					new Search(conversationTitle.getText(), conversationStatus);
			}
		});
		searchButton.setBounds(549, 10, 50, 23);
		middlePanel.add(searchButton);

		JButton deleteButton = new JButton("🗑");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!conversationTitle.getText().isBlank()) {
					Object[] object = {
							"Bạn có chắc muốn xóa toàn bộ tin nhắn với " + conversationTitle.getText() + " không?" };
					int option = JOptionPane.showConfirmDialog(null, object, "Xóa toàn bộ tin nhắn",
							JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						// nếu mà đang ko chat vs ai
						JPanel chatPanel = new JPanel();
						chatPanel.setBackground(Color.WHITE);
						chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
						conversations.remove(conversationTitle.getText());

						conversationPanel.removeAll();
						conversationPanel.add(chatPanel, BorderLayout.PAGE_START);
						conversationPanel.revalidate();
						conversationPanel.repaint();

						sendMessage("Command_DeleteAllMsg`" + conversationTitle.getText());
					}
				}
			}
		});
		deleteButton.setBounds(489, 10, 50, 23);
		middlePanel.add(deleteButton);

		groupBtn = new JButton("👥");
		groupBtn.setVisible(false);
		groupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conversationStatus == false && !conversationTitle.getText().isBlank())
					sendMessage("Command_ShowGroupManagement`" + conversationTitle.getText());
			}
		});
		groupBtn.setBounds(429, 10, 50, 23);
		middlePanel.add(groupBtn);
		contentPane.add(rightPanel, BorderLayout.LINE_END);

		JButton changePasswordBtn = new JButton("🔧");
		changePasswordBtn.addActionListener(e -> changePasswordBtnEventHandler());
		changePasswordBtn.setBounds(240, 10, 50, 23);
		friendPanel.add(changePasswordBtn);

		setPreferredSize(new Dimension(1125, 650));
		setContentPane(contentPane);
		pack();
	}

	/**
	 * Change password Button Event Handler
	 * 
	 */
	void changePasswordBtnEventHandler() {
		JPasswordField password = new JPasswordField();
		JPasswordField newPassword = new JPasswordField();
		JPasswordField reNewPassword = new JPasswordField();
		Object[] object = { "Nhập mật khẩu hiện tại:", password, "Nhập mật khẩu mới:", newPassword,
				"Nhập lại mật khẩu mới:", reNewPassword };

		int option = JOptionPane.showConfirmDialog(null, object, "Đổi mật khẩu", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			if (String.valueOf((password.getPassword())).isBlank()
					|| String.valueOf((newPassword.getPassword())).isBlank()
					|| String.valueOf((reNewPassword.getPassword())).isBlank()) {
				JOptionPane.showMessageDialog(this, "Bạn chưa nhập đầy đủ thông tin!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}

			else if (!String.valueOf((newPassword.getPassword()))
					.equals(String.valueOf((reNewPassword.getPassword())))) {
				JOptionPane.showMessageDialog(this, "Mật khẩu và mật khẩu nhập lại không trùng khớp!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}

			else if (String.valueOf((password.getPassword())).equals(String.valueOf((newPassword.getPassword())))) {
				JOptionPane.showMessageDialog(this, "Mật khẩu mới giống mật khẩu cũ!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}

			else {
				sendMessage("Command_ChangePassword`" + String.valueOf((password.getPassword())) + "`"
						+ String.valueOf((newPassword.getPassword())));
			}
		}
	}

	/**
	 * File Button Event Handler Send a file
	 */
	private void fileButtonEventHandler() {
		JFileChooser fileChooser = new JFileChooser();

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				// Read file
				FileInputStream fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
				byte[] data = fileInputStream.readAllBytes();
				fileInputStream.close();

				// Send file
				messageStatus = MessageStatus.Waiting;
				sendMessage("Command_SendFile`" + conversationTitle.getText() + "`"// command+ người nhận + nội dung
						+ fileChooser.getSelectedFile().getName());
				while (messageStatus == MessageStatus.Waiting)
					System.out.print("");

				if (messageStatus == MessageStatus.Accepted) {
					DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
					dataOutputStream.writeInt(data.length);
					dataOutputStream.write(data);

					conversations.get(conversationTitle.getText())
							.add(new ChatBubble(ChatBubble.BubbleType.Mine, fileChooser.getSelectedFile().getName()));
					revalidate();
				} else {
					JOptionPane.showMessageDialog(this, "Người dùng không hoạt động.", "Gửi file thất bại.",
							JOptionPane.WARNING_MESSAGE);
				}

			} catch (Exception exception) {
				System.out.println("Gửi file thất bại.");
			}
		}
	}

	/**
	 * Send Button Event Handler Send a message
	 * 
	 * @param message String
	 */
	private void sendButtonEventHandler(String message) {
		if (message.isBlank()) {
			JOptionPane.showMessageDialog(this, "Tin nhắn trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
		} else if (conversations.get(conversationTitle.getText()) == null) {
			JOptionPane.showMessageDialog(this, "Chọn người nhận tin nhắn trước khi gửi.", "Lỗi",
					JOptionPane.WARNING_MESSAGE);
		} else {
//			messageStatus = MessageStatus.Waiting;
			if (conversationStatus == true) {
				sendMessage("Command_SendMessage`" + conversationTitle.getText() + "`" + message + "`" + username);
				conversations.get(conversationTitle.getText()).add(new ChatBubble(ChatBubble.BubbleType.Mine, message));

			}

			else {
				sendMessage("Command_SendGroupMessage`" + conversationTitle.getText() + "`" + message + "`" + username);
				conversations.get(conversationTitle.getText())
						.add(new ChatBubbleGroup(ChatBubbleGroup.BubbleType.Mine, message, username));
			}
			revalidate();

//			while (messageStatus == MessageStatus.Waiting)
//				System.out.print("tin nhắn đang chờ");

//			if (messageStatus == MessageStatus.Accepted) {
//				if (conversationStatus == true)
//					conversations.get(conversationTitle.getText())
//							.add(new ChatBubble(ChatBubble.BubbleType.Mine, message));
//				else
//					conversations.get(conversationTitle.getText())
//							.add(new ChatBubbleGroup(ChatBubbleGroup.BubbleType.Mine, message, username));
//				revalidate();
//			} else {
//				JOptionPane.showMessageDialog(this, "Người dùng không hoạt động.", "Lỗi", JOptionPane.WARNING_MESSAGE);
//			}
		}
	}

	/**
	 * Change conversation when user click on users/groups list
	 * 
	 * @param conversationUser String: Selected user
	 */
	private void changeConversation(String conversationUser, boolean conversationStatus) {
		System.out.print("check conversation: " + conversationUser);

		if (conversationStatus == true) {
			for (int i = 0; i < users.length; i++) {
				if (users[i].contains(conversationUser)) {
					users[i] = users[i].replace(" (Tin nhắn mới)", "");

					conversationUser = users[i];
//				JPanel chatPanel = new JPanel();
//				chatPanel.setBackground(Color.WHITE);
//				chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
				}
			}
			usersList.setListData(users);
		}

		else {
			for (int i = 0; i < groups.length; i++) {
				if (groups[i].contains(conversationUser)) {
					groups[i] = groups[i].replace(" (Tin nhắn mới)", "");
					conversationUser = groups[i];
				}
			}
			groupList.setListData(groups);
		}

		conversationTitle.setText(conversationUser);

		JPanel chatPanel = conversations.get(conversationUser);
		// nếu mà đang ko chat vs ai
		chatPanel = new JPanel();
		chatPanel.setBackground(Color.WHITE);
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		conversations.put(conversationUser, chatPanel);

		conversationPanel.removeAll();
		conversationPanel.add(chatPanel, BorderLayout.PAGE_START);
		conversationPanel.revalidate();
		conversationPanel.repaint();
	}

	/**
	 * Add a new message to corresponding conversation
	 * 
	 * @param username   String
	 * @param content    String
	 * @param bubbleType BubbleType
	 */
	public static void addNewMessage(String username, String content, ChatBubble.BubbleType bubbleType,
			boolean chatwithUser) {
		System.out.println("code 631: gia tri dau vao: " + username + " " + content);

		if (chatwithUser == true) {
			for (int i = 0; i < users.length; i++) {
				if (users[i].contains(username) && !users[i].contains(" (Tin nhắn mới)")) {
					if (!conversationTitle.getText().equals(users[i]))
						users[i] = users[i] + " (Tin nhắn mới)";
				}
			}
			usersList.setListData(users);
		}

		else {
			for (int i = 0; i < groups.length; i++) {
				if (groups[i].contains(username) && !groups[i].contains(" (Tin nhắn mới)")) {
					if (!conversationTitle.getText().equals(groups[i]))
						groups[i] = groups[i] + " (Tin nhắn mới)";
				}
			}
			groupList.setListData(groups);
		}

		if (conversations.get(username) == null) {
			JPanel chatPanel = new JPanel();
			chatPanel.setBackground(Color.WHITE);
			chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
			conversations.put(username, chatPanel);
		}
		String[] str = content.split("~");
		if (str.length == 2) {
			System.out.println("Username: " + username + " Message: " + content);
			conversations.get(username).add(new ChatBubble(bubbleType, str[0], str[1]));
		} else {
			System.out.println("Username: " + username + " Message: " + content);
			conversations.get(username).add(new ChatBubble(bubbleType, content));
		}

		conversationPanel.revalidate();
		System.out.println("code addmessage: Them tin nhan thanh cong");
	}

	public static void addNewGroupMessage(String GroupName, String content, ChatBubbleGroup.BubbleType bubbleType,
			String username) {
		for (int i = 0; i < groups.length; i++) {
			if (groups[i].contains(GroupName) && !groups[i].contains(" (Tin nhắn mới)")) {
				if (!conversationTitle.getText().equals(groups[i]))
					groups[i] = groups[i] + " (Tin nhắn mới)";
			}
		}
		groupList.setListData(groups);

		if (conversations.get(GroupName) == null) {
			JPanel chatPanel = new JPanel();
			chatPanel.setBackground(Color.WHITE);
			chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
			conversations.put(GroupName, chatPanel);
		}

		String[] str = content.split("~");
		conversations.get(GroupName).add(new ChatBubbleGroup(bubbleType, str[0], username, str[1]));
		conversationPanel.revalidate();
		System.out.println("code 648: Them tin nhan thanh cong");
	}

	/**
	 * Send a message to Server
	 * 
	 * @param message String
	 */
	public static void sendMessage(String message) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (Exception exception) {
			System.out.println("Gửi tin nhắn thất bại: " + exception);
		}
	}

	/**
	 * Receive message from server and Process the command
	 */
	private static void receiveServerMessages() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream()));

			while (true) {
				String receivedMessage = bufferedReader.readLine() + "";
				if (receivedMessage != null) {
					System.out.println("610 " + receivedMessage);
				}

				if (receivedMessage.contains("Command_CloseConnect")) {
					bufferedReader.close();
					server.close();
					break;
				} else if (receivedMessage.contains("Command_Disconnection")) {
					String[] ObjButtons = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null,
							"Server đã ngắt kết nối. Bạn có muốn đóng chương trình không?", "Notification",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == 0) {
						System.exit(0);
					}

				} else if (receivedMessage.contains("Command_UserList")) {
					String[] str = receivedMessage.split("`");
					users = new String[str.length - 1];
					System.arraycopy(str, 1, users, 0, str.length - 1);
					usersList.removeAll();
					usersList.setListData(users);
//					if(username)
					for (String e : users) {
						friendsList.add(e);
					}
//					System.out.print(usersList.ge);

				}

				else if (receivedMessage.contains("Command_GroupList")) {
					String[] str = receivedMessage.split("`");
					groups = new String[str.length - 1];
					System.arraycopy(str, 1, groups, 0, str.length - 1);
					groupList.removeAll();
					groupList.setListData(groups);
				}

				else if (receivedMessage.contains("Command_AccountVerifyAccepted")) {
					SignIn.status = SignIn.SignInStatus.Accepted;

				} else if (receivedMessage.contains("Command_AccountVerifyAlready")) {
					SignIn.status = SignIn.SignInStatus.Already;

				} else if (receivedMessage.contains("Command_AccountVerifyFailed")) {
					SignIn.status = SignIn.SignInStatus.Failed;

				} else if (receivedMessage.contains("Command_AccountVerifyBlocked")) {
					SignIn.status = SignIn.SignInStatus.Blocked;

				} else if (receivedMessage.contains("Command_CreateAccountAccepted")) {
					SignUp.status = SignUp.SignUpStatus.Accepted;

				} else if (receivedMessage.contains("Command_CreateAccountFailed")) {
					SignUp.status = SignUp.SignUpStatus.Failed;

				} else if (receivedMessage.contains("Command_CreateGroupAccepted")) {
//					String[] str = receivedMessage.split("`");
//					groups = new String[str.length - 1];
//					System.arraycopy(str, 1, groups, 0, str.length - 1);
//					groupList.removeAll();
//					groupList.setListData(groups);
					CreateGroup.status = CreateGroup.CreateGroupStatus.Accepted;
				} else if (receivedMessage.contains("Command_CreateGroupFailed")) {
					CreateGroup.status = CreateGroup.CreateGroupStatus.Failed;
				} else if (receivedMessage.contains("Command_SendMessageAccepted")) {
					messageStatus = MessageStatus.Accepted;
					messageTextField.setText("");

				} else if (receivedMessage.contains("Command_SendMessageFailed")) {
					messageStatus = MessageStatus.Failed;

				} else if (receivedMessage.contains("Command_Message")) {
					String[] str = receivedMessage.split("`");
					addNewMessage(str[1], str[2], ChatBubble.BubbleType.Others, true);

				} else if (receivedMessage.contains("Command_File")) {
					sendMessage("Command_Accepted");
					String[] str = receivedMessage.split("`");

					DataInputStream dataInputStream = new DataInputStream(server.getInputStream());
					byte[] data = new byte[dataInputStream.readInt()];
					dataInputStream.readFully(data, 0, data.length);

					FileOutputStream fileOutputStream = new FileOutputStream(str[2]);
					fileOutputStream.write(data);
					fileOutputStream.close();

					addNewMessage(str[1], str[2], ChatBubble.BubbleType.File, true);

				} else if (receivedMessage.contains("Command_AddFriendRequestAccepted")) {
					JOptionPane.showMessageDialog(null, "Gửi lời mời kết bạn thành công.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (receivedMessage.contains("Command_AddFriendRequestFailed")) {
					JOptionPane.showMessageDialog(null, "Tên người dùng không tồn tại.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (receivedMessage.contains("Command_AddFriendRequestSelf")) {
					JOptionPane.showMessageDialog(null, "Không thể tự kết bạn với chính mình.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (receivedMessage.contains("Command_AddFriendRequestIsFriend")) {
					JOptionPane.showMessageDialog(null, "Bạn đã là bạn với người này rồi.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);

				} else if (receivedMessage.contains("Command_NewAddFriendRequest")) {
					String[] str = receivedMessage.split("`");
					for (int i = 1; i < str.length; i++) {
						Object[] rowObjects = { str[i], "Đồng ý:" + "Command_AcceptAddFriendRequest`" + str[i],
								"Xóa:" + "Command_deleteAddFriendRequest`" + str[i] };
						addFriendRequestTableModel.addRow(rowObjects);
					}

				} else if (receivedMessage.contains("Command_deleteAddFriendRequest")) {
					String[] str = receivedMessage.split("`");
					((DefaultTableModel) addFriendRequestTable.getModel()).removeRow(Integer.parseInt(str[1]));

				} else if (receivedMessage.contains("Command_ShowFriendList")) {
					String[] str = receivedMessage.split("`");
					new FriendList(str);

				} else if (receivedMessage.contains("Command_unfriend")) {
					String[] str = receivedMessage.split("`");
					FriendList.deleteRow(Integer.parseInt(str[1]));

				} else if (receivedMessage.contains("Command_SendMessageAccepted")) {
					messageStatus = MessageStatus.Accepted;

				} else if (receivedMessage.contains("Command_SendMessageFailed")) {
					messageStatus = MessageStatus.Failed;

				} else if (receivedMessage.contains("Command_Message")) {
					String[] str = receivedMessage.split("`");
					addNewMessage(str[1], str[2], ChatBubble.BubbleType.Others, true);

				} else if (receivedMessage.contains("Command_File")) {
					sendMessage("Command_Accepted");
					String[] str = receivedMessage.split("`");

					DataInputStream dataInputStream = new DataInputStream(server.getInputStream());
					byte[] data = new byte[dataInputStream.readInt()];
					dataInputStream.readFully(data, 0, data.length);

					FileOutputStream fileOutputStream = new FileOutputStream(str[2]);
					fileOutputStream.write(data);
					fileOutputStream.close();

					addNewMessage(str[1], str[2], ChatBubble.BubbleType.File, true);

				} else if (receivedMessage.contains("Command_SendHistoryMessage")) {
					System.out.println("\nget data base: " + receivedMessage);
					String[] str = receivedMessage.split("`");
					for (int index = 2; index < str.length; index++) {
						String[] mess = str[index].split("~");
						System.out.println("\n code 955: " + "username: " + str[1] + " " + mess[0] + " " + mess[2]
								+ " time" + mess[3] + " senderDl: " + mess[4] + " receivedl: " + mess[5]);
						if (str[1].equals(mess[0])) {
							System.out.println("code 819: mot tin nhan vua duoc them vao");
							// nguoi gui = usernam thi them vao ben phai
							if (mess[4].equals("false")) {
								addNewMessage(mess[1], mess[2] + "~" + mess[3], ChatBubble.BubbleType.Mine, true);
							}

						} else if (!str[1].equals(mess[0])) {
							System.out.println("code 965: mot tin nhan vua duoc them vao");
							// nguoc lai la ben trai
							if (mess[5].equals("false")) {
								addNewMessage(mess[0], mess[2] + "~" + mess[3], ChatBubble.BubbleType.Others, true);
							}

						}
					}

				} else if (receivedMessage.contains("Command_SendGroupHistoryMessage")) {
					System.out.println("\nget data base: " + receivedMessage);
					String[] str = receivedMessage.split("`");
					for (int i = 3; i < str.length; i++) {

						String[] mess = str[i].split("~");
						if (str[1].equals(mess[0])) {
							addNewGroupMessage(str[2], mess[1] + "~" + mess[2], ChatBubbleGroup.BubbleType.Mine,
									mess[0]);
						} else {
							addNewGroupMessage(str[2], mess[1] + "~" + mess[2], ChatBubbleGroup.BubbleType.Others,
									mess[0]);
						}
					}
				}

				else if (receivedMessage.contains("Command_ForgotPasswordFail")) {
					SignIn.forgotPasswordStatus = SignIn.ForgotPasswordStatus.Failed;
				}

				else if (receivedMessage.contains("Command_ForgotPasswordInvalid")) {
					SignIn.forgotPasswordStatus = SignIn.ForgotPasswordStatus.Invalid;
				}

				else if (receivedMessage.contains("Command_ForgotPasswordSuccessful")) {
					SignIn.forgotPasswordStatus = SignIn.ForgotPasswordStatus.Successful;
				}

				else if (receivedMessage.contains("Command_ChangePasswordFailed")) {
					JOptionPane.showMessageDialog(null, "Mật khẩu hiện tại không đúng.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_ChangePasswordSuccessful")) {
					JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}

				else if (receivedMessage.contains("Command_GroupMessage")) {
					String[] str = receivedMessage.split("`");
					System.out.println("qweqweqweqwe:" + receivedMessage);
					addNewGroupMessage(str[1], str[2], ChatBubbleGroup.BubbleType.Others, str[3]);

				}

				else if (receivedMessage.contains("Command_ShowGroupManagement")) {
					String[] str = receivedMessage.split("`");
					new GroupManagement(conversationTitle.getText(), str);
				}

				else if (receivedMessage.contains("Command_ChangeGroupNameFail")) {
					JOptionPane.showMessageDialog(null, "Tên nhóm đã tồn tại, vui lòng chọn tên khác!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_NotPermitted")) {
					JOptionPane.showMessageDialog(null, "Bạn không có đủ thẩm quyền để làm việc này!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_ChangeGroupNameSuccessful")) {
					JOptionPane.showMessageDialog(null, "Đổi tên nhóm thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}

				else if (receivedMessage.contains("Command_ChangeGroupNameSetConversationTitle")) {
					String[] str = receivedMessage.split("`");
					conversationTitle.setText(str[1]);
				}

				else if (receivedMessage.contains("Command_YouNotIn")) {
					JOptionPane.showMessageDialog(null, "Bạn không ở trong nhóm này!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_Invite2GroupFail")) {
					JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_Invite2GroupAlreadyInGroup")) {
					JOptionPane.showMessageDialog(null, "Người dùng đã ở trong nhóm!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_Invite2GroupSucessful")) {
					JOptionPane.showMessageDialog(null, "Mời thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}

				else if (receivedMessage.contains("Command_RefreshGroupManagementTable")) {
					String[] str = receivedMessage.split("`");
					GroupManagement.refresh(str);
				}

				else if (receivedMessage.contains("Command_LeftTheGroupSetConverSationTitle")) {
					conversationTitle.setText("");
					groupBtn.setVisible(false);
				}

				else if (receivedMessage.contains("Command_LeftTheGroupDeleteFromGroup")) {
					String[] str = receivedMessage.split("`");
					Object[] object = { "Bạn có chắc muốn rời khỏi nhóm " + str[1] + " không?" };
					int option = JOptionPane.showConfirmDialog(null, object, "Rời khỏi nhóm",
							JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						sendMessage("Command_LeftTheGroup`" + str[1]);
					}
				}

				else if (receivedMessage.contains("Command_TheyNotIn")) {
					JOptionPane.showMessageDialog(null, "Người dùng không ở trong nhóm này!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_ChangeRoleSelf")) {
					JOptionPane.showMessageDialog(null, "Không thể phân quyền cho chính mình!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (receivedMessage.contains("Command_SearchMsgHistory")) {
					String[] str = receivedMessage.split("`");
					Search.refresh(str);
				}
				
				else if (receivedMessage.contains("Command_changeFriendStatus")) {
					String[] str = receivedMessage.split("`");
					FriendList.changeFriendStatusByName(str[1], str[2]);
				}

				else {
					System.out.println(receivedMessage);
				}
			}

		} catch (Exception exception) {
			System.out.println("Lỗi hệ thống: " + exception);
		}
	}
}