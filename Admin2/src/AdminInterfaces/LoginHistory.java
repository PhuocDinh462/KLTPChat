package AdminInterfaces;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Server.Classes.User;

public class LoginHistory extends JPanel {
	private JTable tableLogin;
	private DefaultTableModel logsTableModel;
	private int number = 0;

	public LoginHistory() {
		setLayout(null);
		setBounds(0, 0, 1025, 470);
		setVisible(true);
		tableLogin = new JTable();
		tableLogin.setEnabled(false);

		String[] titleTable = { "STT", "Tên đăng nhập", "Họ tên", "Thời gian đăng nhập" };

		logsTableModel = new DefaultTableModel();
		for (int i = 0; i < titleTable.length; i++) {
			logsTableModel.addColumn(titleTable[i]);
		}

		tableLogin.setModel(logsTableModel);
		setColSpaceTable();

		JScrollPane scrollPane = new JScrollPane(tableLogin);
		scrollPane.setBounds(10, 10, 1005, 450);
		add(scrollPane);
	}

	public void addToListUser(User newUser) {
		int numberOfLogin = newUser.getTimeLogin().size();
		Object[] newObj = { ++number, newUser.getInfor().getUsername(), newUser.getInfor().getFullname(),
				newUser.getTimeLogin().get(numberOfLogin - 1) };
		logsTableModel.addRow(newObj);
	}

	public void setColSpaceTable() {
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
	}

//	private void initRowData(ArrayList<User> data) {
//		for (int i = 0; i < data.size(); i++) {
//			User userLogin = data.get(i);
//			int k = 0;
//
//			for (int j = 0; j < userLogin.getTimeLogin().size(); j++) {
//				for (; k < rowData.size(); k++) {
//					try {
//						//Real-time check
//						if (!compareDates(rowData.get(k).get(1), userLogin.getTimeLogin().get(j)) || k == rowData.size() - 1) {
//							Vector<String> newData = new Vector<String>();
//							newData.add(String.valueOf(k + 1));
//							newData.add(userLogin.getTimeLogin().get(j));
//							newData.add(userLogin.getInfor().getUsername());
//							newData.add(userLogin.getInfor().getFullname());
//							
//							rowData.insertElementAt(newData, k++);
//							break;
//						}
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		}
//	}
//
//	private boolean compareDates(String psDate1, String psDate2) throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//		Date date1 = dateFormat.parse(psDate1);
//		Date date2 = dateFormat.parse(psDate2);
//
//		if (date2.after(date1)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
