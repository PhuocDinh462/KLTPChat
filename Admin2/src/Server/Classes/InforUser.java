package Server.Classes;

public class InforUser {

	private String username;
	private String password;
	private String fullName;
	private String address;
	private String dob;
	private String gender;
	private String email;
	private Boolean blocked;
	private Boolean online;

	public InforUser() {

		this.username = "";
		this.password = "";
		this.fullName = "";
		this.address = "";
		this.dob = "";
		this.gender = "";
		this.email = "";
		this.blocked = false;
		this.online = false;
	}

	public InforUser(String username, String password, String fullname, String address, String DOB, String gender,
			String email) {

		this.username = username;
		this.password = password;
		this.fullName = fullname;
		this.address = address;
		this.dob = DOB;
		this.gender = gender;
		this.email = email;
		this.blocked = false;
		this.online = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullName;
	}

	public void setFullname(String fullName) {
		this.fullName = fullName;
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

	public String getDOB() {
		return dob;
	}

	public void setDOB(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Boolean getStatus() {
		return online;
	}

	public void setStatus(Boolean status) {
		this.online = status;
	}
}