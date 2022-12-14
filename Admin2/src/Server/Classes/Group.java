package Server.Classes;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private String groupName;
	private List<String> managers;
	private List<String> userId;
	private List<String> messageId;

	Group() {
		groupName = new String();
		managers = new ArrayList<String>();
		userId = new ArrayList<String>();
		messageId = new ArrayList<String>();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<String> getManagers() {
		return managers;
	}

	public void setManagers(List<String> managers) {
		this.managers = managers;
	}

	public List<String> getuserId() {
		return userId;
	}

	public void setuserId(List<String> userId) {
		this.userId = userId;
	}

	public List<String> getmessageId() {
		return messageId;
	}

	public void setmessageId(List<String> messageId) {
		this.messageId = messageId;
	}

}
