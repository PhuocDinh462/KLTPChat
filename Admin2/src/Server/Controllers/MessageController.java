package Server.Controllers;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import Server.Classes.Message;
import Server.Models.MessageModel;

public class MessageController extends MessageModel {
	public void create(Message msg) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy@HH:mm:ss");
		Date date = new Date();
		
		Document document = new Document("_id", msg.getId()).append("senderId", msg.getSenderId())
				.append("receiverId", msg.getReceiverId()).append("content", msg.getContent())
				.append("index", msg.getIndex())
				.append("createTime", formatter.format(date)).append("senderDelete", false)
				.append("receiverDelete", false);

		CollectionMessage().insertOne(document);
		System.out.println("successful");
	}

	public void read() {

		MongoCursor<Document> document = CollectionMessage().find().iterator();

		try {
			while (document.hasNext()) {
				System.out.println(document.next().toJson());
			}
		} finally {
			document.close();
		}
		System.out.print("Successfull");
	}

	public void deleteById(String id) {
		CollectionMessage().deleteMany(eq("_id", id));
		System.out.println("successful");
	}

	public void deleteWhenRemoveFriend(String senderId, String receiverId) {
		Document doc = new Document();
		doc.append("senderId", senderId);
		doc.append("receiverId", receiverId);
		CollectionMessage().deleteMany(doc);

		System.out.print("Successfull");
	}

	// @param id: Message
	public void deleteByMsgSend(String id) {
		Message msg = new Message();
		msg = findMessageById(id);

		if (msg.getReceiverDelete()) {
			deleteById(id);
		} else {
			CollectionMessage().updateOne(eq("_id", id), combine(set("senderDelete", true)));
		}
		System.out.print("Successfull");
	}

	// @param id: Message
	public void deleteByMsgReceive(String id) {
		Message msg = new Message();
		msg = findMessageById(id);

		if (msg.getSenderDelete()) {
			deleteById(id);
		} else {
			CollectionMessage().updateOne(eq("_id", id), combine(set("receiverDelete", true)));
		}
		System.out.print("Successful");
	}

	public Message findMessageById(String id) {
		Document doc = new Document();
		doc.append("_id", id);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();
		Gson gson = new Gson();
		System.out.print("Successful");
		return gson.fromJson(document.next().toJson(), Message.class);
	}

	public ArrayList<Message> findMessageBySender(String sender, String receiver) {
		Document doc = new Document();
		doc.append("senderId", sender);
		doc.append("receiverId", receiver);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();
		ArrayList<Message> mess = new ArrayList<Message>();
		Gson gson = new Gson();

		try {
			while (document.hasNext()) {
				Message addMess = gson.fromJson(document.next().toJson(), Message.class);
				mess.add(addMess);
			}
		} finally {
			document.close();
		}
		System.out.println("Successful");
		return mess;
	}
	
	public String findIndexBySender(String sender, String receiver) {
		String indexTemp = "0";
		Document doc = new Document();
		doc.append("senderId", sender);
		doc.append("receiverId", receiver);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();
		ArrayList<Message> mess = new ArrayList<Message>();
		Gson gson = new Gson();
		if(document.hasNext()) {
			System.out.println("Dữ liệu có tồn tại");
			try {
					Message addMess = gson.fromJson(document.next().toJson(), Message.class);
					mess.add(addMess);
			} finally {
				indexTemp = mess.get(0).getIndex();
				document.close();
			}
		}else {
			System.out.println("Dữ liệu chưa tồn tại");
		}
	
		System.out.println("Successful");
		return indexTemp;
	}
	
	public ArrayList<Message> findMessageByIndex(String indexTemp) {
		Document doc = new Document();
		doc.append("index", indexTemp);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();
		ArrayList<Message> mess = new ArrayList<Message>();
		Gson gson = new Gson();

		try {
			while (document.hasNext()) {
				Message addMess = gson.fromJson(document.next().toJson(), Message.class);
				mess.add(addMess);
			}
		} finally {
			document.close();
		}
		System.out.println("Successful");
		return mess;
	}
	
	
	public ArrayList<Message> findMessageByGroup(String GroupName) {
		Document doc = new Document();
		doc.append("receiverId", GroupName);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();
		ArrayList<Message> mess = new ArrayList<Message>();
		Gson gson = new Gson();

		try {
			while (document.hasNext()) {
				Message addMess = gson.fromJson(document.next().toJson(), Message.class);
				mess.add(addMess);
			}
		} finally {
			document.close();
		}
		System.out.println("Successful");
		return mess;
	}
}
