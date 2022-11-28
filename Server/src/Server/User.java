package Server;

import java.util.ArrayList;

public class User {
	private String password;
	private String fullname;
	private String address;
	private String email;
	private ArrayList<String> friend;
	private ArrayList<String> addFriendRequest;

	public User(String password, String fullname, String address, String email) {
		super();
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setAddFriendRequest(ArrayList<String> addFriendRequest) {
		this.addFriendRequest = addFriendRequest;
	}

	public void addAddFriendRequest(String username) {
		this.addFriendRequest.add(username);
	}
	
	public void deleteAddFriendRequest(String username) {
		this.addFriendRequest.remove(this.addFriendRequest.indexOf(username));
	}
}
