package Server;

import java.util.ArrayList;

public class Message {
	private User user;
	private String timeSent;
	private String content;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(String timeSent) {
		this.timeSent = timeSent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Message() {
		user = new User();
		timeSent = new String();
		content = new String();
	}
	
	public Message(User userTemp, String timeSentTemp, String contentTemp) {
		user = userTemp;
		timeSent= timeSentTemp;
		content = contentTemp;
	}
	
}
