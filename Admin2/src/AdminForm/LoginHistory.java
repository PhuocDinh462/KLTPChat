package AdminForm;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class LoginHistory extends JPanel {
	private JTable tableLogin;
	private DefaultTableModel logsTableModel;
	Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	Vector<String> titleTable = new Vector<String>();

	// Sign in => save to list
	// Sign out => remove from list
	public LoginHistory() {
		init();
	}
	
	public LoginHistory(ArrayList<User> data) {
		initRowData(data);
		init();
	}
	
	private void init() {
		setLayout(null);
		setBounds(0, 0, 1025, 470);
		setVisible(true);
		tableLogin = new JTable();
		tableLogin.setEnabled(false);

		titleTable.add("STT");
		titleTable.add("Tên đăng nhập");
		titleTable.add("Họ tên");
		titleTable.add("Thời gian đăng nhập");

		Vector<String> obj1 = new Vector<String>();
		obj1.add("1");
		obj1.add("1");
		obj1.add("1");
		obj1.add("1");

		rowData.add(obj1);
		logsTableModel = new DefaultTableModel(rowData, titleTable) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		tableLogin.setModel(logsTableModel);
		tableLogin.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableLogin.getColumnModel().getColumn(0).setMinWidth(35);
		tableLogin.getColumnModel().getColumn(0).setMaxWidth(35);
		tableLogin.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableLogin.getColumnModel().getColumn(1).setMinWidth(200);
		tableLogin.getColumnModel().getColumn(1).setMaxWidth(200);
		tableLogin.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableLogin.getColumnModel().getColumn(2).setMinWidth(120);
		tableLogin.getColumnModel().getColumn(2).setMaxWidth(150);
		tableLogin.getColumnModel().getColumn(3).setPreferredWidth(120);
		tableLogin.getColumnModel().getColumn(3).setMinWidth(120);
		tableLogin.setFont(new Font("Dialog", Font.PLAIN, 12));

		JScrollPane scrollPane = new JScrollPane(tableLogin);
		scrollPane.setBounds(10, 10, 1005, 450);
		add(scrollPane);
	}

	private void addToListUser(User newUser) {
		int numberOfLogin = newUser.getTimeLogin().size();

		// init new status login for user
		Vector<String> newData = new Vector<String>();
		newData.add("1");
		newData.add(newUser.getTimeLogin().get(numberOfLogin - 1));
		newData.add(newUser.getInfor().getUsername());
		newData.add(newUser.getInfor().getFullname());

		// Add to table to display
		rowData.insertElementAt(newData, 0);

		// Reset No.
		for (int i = 0; i < rowData.size(); i++) {
			rowData.get(i).set(0, String.valueOf(i + 1));
		}
	}

	private void initRowData(ArrayList<User> data) {
		for (int i = 0; i < data.size(); i++) {
			User userLogin = data.get(i);
			int k = 0;

			for (int j = 0; j < userLogin.getTimeLogin().size(); j++) {
				for (; k < rowData.size(); k++) {
					try {
						//Real-time check
						if (!compareDates(rowData.get(k).get(1), userLogin.getTimeLogin().get(j)) || k == rowData.size() - 1) {
							Vector<String> newData = new Vector<String>();
							newData.add(String.valueOf(k + 1));
							newData.add(userLogin.getTimeLogin().get(j));
							newData.add(userLogin.getInfor().getUsername());
							newData.add(userLogin.getInfor().getFullname());
							
							rowData.insertElementAt(newData, k++);
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	private boolean compareDates(String psDate1, String psDate2) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date1 = dateFormat.parse(psDate1);
		Date date2 = dateFormat.parse(psDate2);

		if (date2.after(date1)) {
			return true;
		} else {
			return false;
		}
	}
}
