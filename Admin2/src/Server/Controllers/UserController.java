package Server.Controllers;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;

import Server.Classes.InforUser;
import Server.Models.UserModel;

public class UserController extends UserModel {
	public void create(InforUser Information) {
		
		System.out.println(Information.getFullname());
		
		ArrayList<String> listFriend = new ArrayList<String>();
		ArrayList<String> listAddFriend = new ArrayList<String>();
		ArrayList<String> historyLogin = new ArrayList<String>();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Document document = new Document("userName", Information.getUsername())
				.append("password", Information.getPassword()).append("dob", Information.getDOB())
				.append("gender", Information.getGender()).append("address", Information.getAddress())
				.append("email", Information.getEmail()).append("listFriend", listFriend)
				.append("listAddFriend", listAddFriend).append("createTime", formatter.format(date))
				.append("historyLogin", historyLogin);
		System.out.println("aaaaaaa");
		CollectionUser().insertOne(document);
//		DBObject person = new BasicDBObject("userName", Information.getUsername())
//				.append("password", Information.getPassword()).append("dob", Information.getDOB())
//				.append("gender", Information.getGender()).append("address", Information.getAddress())
//				.append("email", Information.getEmail()).append("listFriend", listFriend)
//				.append("listAddFriend", listAddFriend).append("createTime", formatter.format(date))
//				.append("historyLogin", historyLogin);
//		DBObject updateDoc = new BasicDBObject("$set",person);
//		CollectionUser().insert(updateDoc);
		System.out.println("successful");
	}
//
//	public void read() {
//		
//		MongoCursor<Document> document = CollectionUser().find().iterator();
//		
////		try {
////			while (document.hasNext()) {
////				System.out.println(document.next().toJson());
////			}
////		} finally {
////			document.close();
////		}
//		System.out.print("Successfull");
//	}
//
//	public void addPeopleRoom(String idRoom, String idUser) {
//		ArrayList<String> document = new ArrayList<String>();
//		document = (ArrayList<String>) CollectionUser().find().iterator().next().get("listRoom");
//		document.add(idRoom);
//		CollectionUser().updateOne(eq("id", idUser), combine(set("listRoom", document)));
//	}
//
//	public void update(String id, String name) {
//		CollectionUser().updateOne(eq("_id", id), combine(set("name", name)));
//		System.out.println("successful");
//	}
//
//	public void delete(String id) {
//		CollectionUser().deleteMany(eq("_id", id));
//	}
//
//	public ArrayList<String> searchListFriend(String id) {
//		Document filterDoc = new Document();
//		filterDoc.append("id", id);
//		MongoCursor<Document> document = CollectionUser().find(filterDoc).iterator();
//		ArrayList<String> listData = new ArrayList<String>();
//		try {
//			while (document.hasNext()) {
//				Document doc = document.next();
//				listData.add((String) doc.get("listFriend").toString());
//			}
//		} finally {
//			document.close();
//		}
//		return listData;
//	}
//
//	public ArrayList<String> searchNameFriend(String id) {
//		Document filterDoc = new Document();
//		filterDoc.append("id", id);
//		MongoCursor<Document> document = CollectionUser().find(filterDoc).iterator();
//		ArrayList<String> listData = new ArrayList<String>();
//		try {
//			while (document.hasNext()) {
//				Document doc = document.next();
//				listData.add((String) doc.get("fullName").toString());
//			}
//		} finally {
//			document.close();
//		}
//		return listData;
//	}
//
//	public ArrayList<String> getListNameGrToId(String id) {
//		Document filterDoc = new Document();
//		filterDoc.append("id", id);
//		MongoCursor<Document> document = CollectionUser().find(filterDoc).iterator();
//		ArrayList<String> listData = new ArrayList<String>();
//		try {
//			while (document.hasNext()) {
//				Document doc = document.next();
//				listData.add((String) doc.get("listRoom").toString());
//			}
//		} finally {
//			document.close();
//		}
//		return listData;
//	}
}
