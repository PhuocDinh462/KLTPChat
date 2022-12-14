package Server.Classes;

public class Message {
	private String userId;
	private String destinationId;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Message() {
		userId = new String();
		content = new String();
	}

	public Message(String userIdTemp, String timeSentTemp, String contentTemp) {
		userId = userIdTemp;
		content = contentTemp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

}
