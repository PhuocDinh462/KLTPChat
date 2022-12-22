package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
	private String _id;
	private String name;
	private String createTime;
	private ArrayList<String> listManagers; // Id
	private ArrayList<String> listUsers; // Id
	private ArrayList<String> listMessage; // Id

	public Group() {
		_id = UUID.randomUUID().toString();
		name = new String();
		createTime = new String();
		listManagers = new ArrayList<String>();
		listUsers = new ArrayList<String>();
		listMessage = new ArrayList<String>();
	}

	public Group(Group newGroup) {
		_id = newGroup.getGroupId();
		name = newGroup.getGroupName();
		createTime = newGroup.getCreateTime();
		listManagers = newGroup.getManagers();
		listUsers = newGroup.getlistUsers();
		listMessage = newGroup.getmessageId();
	}

	public Group(String groupName, ArrayList<String> managers, ArrayList<String> listUsers,
			ArrayList<String> messageId) {
		this._id = UUID.randomUUID().toString();
		this.name = groupName;
		createTime = new String();
		this.listManagers = managers;
		this.listUsers = listUsers;
		this.listMessage = messageId;
	}
	public Group(String groupName, String idUser) {
		this._id = UUID.randomUUID().toString();
		this.name = groupName;
		createTime = new String();
		this.listManagers = new ArrayList<String>();
		this.listUsers = new ArrayList<String>();
		this.listManagers.add(idUser);
		this.listUsers.add(idUser);
		this.listMessage = new ArrayList<String>();
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

	public ArrayList<String> getManagers() {
		return listManagers;
	}

	public void setManagers(ArrayList<String> managers) {
		this.listManagers = managers;
	}

	public ArrayList<String> getlistUsers() {
		return listUsers;
	}

	public void setlistUsers(ArrayList<String> listUsers) {
		this.listUsers = listUsers;
	}

	public ArrayList<String> getmessageId() {
		return listMessage;
	}

	public void setmessageId(ArrayList<String> messageId) {
		this.listMessage = messageId;
	}

	public String getCreateTime() {
		return createTime;
	}

}
