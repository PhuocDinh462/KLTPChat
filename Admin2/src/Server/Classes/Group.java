package Server.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
	private String _id;
	private String name;
	private String createTime;
	private ArrayList<String> listManagers;	//Id
	private ArrayList<String> listUsers;	//Id
	private ArrayList<String> messageId;	//Id

	public Group() {
		_id = UUID.randomUUID().toString();
		name = new String();
		createTime = new String();
		listManagers = new ArrayList<String>();
		listUsers = new ArrayList<String>();
		messageId = new ArrayList<String>();
	}

	public Group(String groupName, ArrayList<String> managers, ArrayList<String> listUsers,
			ArrayList<String> messageId) {
		this._id = UUID.randomUUID().toString();
		this.name = groupName;
		createTime = new String();
		this.listManagers = managers;
		this.listUsers = listUsers;
		this.messageId = messageId;
	}

	public String getGroupId() {
		return _id;
	}

	public String getGroupName() {
		return name;
	}

	public void setGroupName(String groupName) {
		this.name = groupName;
	}

	public List<String> getManagers() {
		return listManagers;
	}

	public void setManagers(ArrayList<String> managers) {
		this.listManagers = managers;
	}

	public List<String> getlistUsers() {
		return listUsers;
	}

	public void setlistUsers(ArrayList<String> listUsers) {
		this.listUsers = listUsers;
	}

	public List<String> getmessageId() {
		return messageId;
	}

	public void setmessageId(ArrayList<String> messageId) {
		this.messageId = messageId;
	}

	public String getCreateTime() {
		return createTime;
	}

}
