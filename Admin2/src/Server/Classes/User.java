package Server.Classes;

import java.util.ArrayList;
import java.util.UUID;

public class User {
	private String _id;
	private InforUser InforUser;
	private String createTime;
	private ArrayList<String> historyLogin;
	private ArrayList<String> listFriend; // Username not Id
	private ArrayList<String> listAddFriend; // Username not Id

	public User() {
		this._id = UUID.randomUUID().toString();
		InforUser = new InforUser();
		createTime = new String();
		historyLogin = new ArrayList<String>();
		listFriend = new ArrayList<String>();
		listAddFriend = new ArrayList<String>();
	}

	public User(String username, String password, String fullname, String address, String DOB, String gender,
			String email) {
		this._id = UUID.randomUUID().toString();
		InforUser = new InforUser(username, password, fullname, address, DOB, gender, email);
		createTime = new String();
		historyLogin = new ArrayList<String>();
		listFriend = new ArrayList<String>();
		listAddFriend = new ArrayList<String>();
	}

	public String getId() {
		return _id;
	}

	public ArrayList<String> getTimeLogin() {
		return historyLogin;
	}

	public ArrayList<String> getFriend() {
		return listFriend;
	}

	public void setFriend(ArrayList<String> friend) {
		this.listFriend = friend;
	}

	public void addFriend(String username) {
		this.listFriend.add(username);
	}

	public void deleteFriend(String username) {
		this.listFriend.remove(this.listFriend.indexOf(username));
	}

	public ArrayList<String> getListAddFriend() {
		return listAddFriend;
	}

	public void addAddFriendRequest(String username) {
		this.listAddFriend.add(username);
	}

	public void deleteAddFriendRequest(String username) {
		this.listAddFriend.remove(this.listAddFriend.indexOf(username));
	}

	public InforUser getInfor() {
		return InforUser;
	}

	public void setInfor(InforUser userInf) {
		this.InforUser = userInf;
	}

	public String getCreateTime() {
		return createTime;
	}
}
