package Client;

import java.util.ArrayList;
import java.util.UUID;

public class User {
	private String _id;
	private InforUser InforUser;
	private String createTime;
	private ArrayList<String> timeLogin;
	private ArrayList<String> friend;    //Username not Id
	private ArrayList<String> addFriendRequest; //Username not Id

	public User() {
		this._id = UUID.randomUUID().toString();
		InforUser = new InforUser();
		createTime = new String();
		timeLogin = new ArrayList<String>();
		friend = new ArrayList<String>();
		addFriendRequest = new ArrayList<String>();
	}

	public User(String username, String password, String fullname, String address, String DOB, String gender,
			String email) {
		this._id = UUID.randomUUID().toString();
		InforUser = new InforUser(username, password, fullname, address, DOB, gender, email);
		createTime = new String();
		timeLogin = new ArrayList<String>();
		friend = new ArrayList<String>();
		addFriendRequest = new ArrayList<String>();
	}

	public String getId() {
		return _id;
	}

	public ArrayList<String> getTimeLogin() {
		return timeLogin;
	}

	public ArrayList<String> getFriend() {
		return friend;
	}

	public void setFriend(ArrayList<String> friend) {
		this.friend = friend;
	}

	public void addFriend(String username) {
		this.friend.add(username);
	}

	public void deleteFriend(String username) {
		this.friend.remove(this.friend.indexOf(username));
	}

	public ArrayList<String> getAddFriendRequest() {
		return addFriendRequest;
	}

	public void addAddFriendRequest(String username) {
		this.addFriendRequest.add(username);
	}

	public void deleteAddFriendRequest(String username) {
		this.addFriendRequest.remove(this.addFriendRequest.indexOf(username));
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

