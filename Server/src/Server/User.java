package Server;

import java.util.ArrayList;

public class User {
	private InforUser infor;
	private ArrayList<String> timeLogin;
	private ArrayList<String> friend;
	private ArrayList<String> addFriendRequest;

	public User() {
		infor = new InforUser();
	}

	public User(String username, String password, String fullname, String address, String DOB, String gender,
			String email) {
		infor = new InforUser(username, password, fullname, address, DOB, gender, email);
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

//	public void setAddFriendRequest(ArrayList<String> addFriendRequest) {
//		this.addFriendRequest = addFriendRequest;
//	}

	public void addAddFriendRequest(String username) {
		this.addFriendRequest.add(username);
	}

	public void deleteAddFriendRequest(String username) {
		this.addFriendRequest.remove(this.addFriendRequest.indexOf(username));
	}

	public InforUser getInfor() {
		return infor;
	}
}
