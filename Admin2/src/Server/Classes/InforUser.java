package Server.Classes;

public class InforUser {
	private String username;
	private String password;
	private String fullname;
	private String address;
	private String DOB;
	private String gender;
	private String email;
	private String dateCreated;
	
	public InforUser() {
		this.username = "";
		this.password = "";
		this.fullname = "";
		this.address = "";
		DOB = "";
		this.gender = "";
		this.email = "";
	}

	public InforUser(String username, String password, String fullname, String address, String DOB, String gender,
			String email) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.DOB = DOB;
		this.gender = gender;
		this.email = email;
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

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
}
