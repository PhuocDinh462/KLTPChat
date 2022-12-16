package Server.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
	private String groupName;
	private String groupId;
	private ArrayList<String> listManagers;
	private ArrayList<String> listUsers;
	private ArrayList<String> messageId;

	public Group(){
		groupId  = UUID.randomUUID().toString();
		groupName = new String();
		listManagers = new ArrayList<String>();
		listUsers = new ArrayList<String>();
		messageId = new ArrayList<String>();
	}
	public Group(String groupName, ArrayList<String> managers, ArrayList<String> listUsers, ArrayList<String> messageId) {
		this.groupId  = UUID.randomUUID().toString();
		this.listManagers = managers;
		this.listUsers = listUsers;
		this.messageId = messageId;
	}
	public String getGroupId() {
		return groupId;
	}
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}
