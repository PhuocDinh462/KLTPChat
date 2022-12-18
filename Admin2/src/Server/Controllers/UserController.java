package Server.Controllers;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;

import Server.Classes.InforUser;
import Server.Classes.Message;
import Server.Classes.User;
import Server.Models.UserModel;

public class UserController extends UserModel {
	public void create(User newUser) {

		ArrayList<String> listFriend = new ArrayList<String>();
		ArrayList<String> listAddFriend = new ArrayList<String>();
		ArrayList<String> historyLogin = new ArrayList<String>();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Document document = new Document("_id", newUser.getId())
				.append("InforUser", new Document("username", newUser.getInfor().getUsername())
						.append("password", newUser.getInfor().getPassword())
						.append("fullName", newUser.getInfor().getFullname()).append("dob", newUser.getInfor().getDOB())
						.append("gender", newUser.getInfor().getGender())
						.append("address", newUser.getInfor().getAddress())
						.append("email", newUser.getInfor().getEmail())
						.append("blocked", newUser.getInfor().getBlocked()))
				.append("createTime", formatter.format(date)).append("listFriend", listFriend)
				.append("listAddFriend", listAddFriend).append("historyLogin", historyLogin);

		CollectionUser().insertOne(document);

		System.out.println("successful");
	}

	public void read() {

		MongoCursor<Document> document = CollectionUser().find().iterator();

		try {
			while (document.hasNext()) {
				System.out.println(document.next().toJson());
			}
		} finally {
			document.close();
		}
		System.out.print("Successfull");
	}

	public ArrayList<User> getAllUsers() {
		MongoCursor<Document> document = CollectionUser().find().iterator();
		ArrayList<User> users = new ArrayList<User>();
		Gson gson = new Gson();

		try {
			while (document.hasNext()) {
				Document doc = document.next();

				InforUser addUserInfor = gson.fromJson(((Document) doc.get("InforUser")).toJson(), InforUser.class);

				User addUser = gson.fromJson(doc.toJson(), User.class);
				addUser.setInfor(addUserInfor);

				users.add(addUser);
			}
		} finally {
			document.close();
		}
		System.out.print("Successful");
		return users;
	}

	public User getUserById(String id) {
		Document doc = new Document();
		Gson gson = new Gson();
		doc.append("_id", id);
		MongoCursor<Document> document = CollectionUser().find(doc).iterator();

		System.out.print("Successful");

		return gson.fromJson(document.next().toJson(), User.class);
	}

	public User getUserByUsername(String username) {
		Document doc = new Document();
		Gson gson = new Gson();
		doc.append("InforUser", new Document("username", username));
		MongoCursor<Document> document = CollectionUser().find(doc).iterator();

		System.out.print("Successful");

		return gson.fromJson(document.next().toJson(), User.class);
	}

	public void update(String id, InforUser user) {
		CollectionUser().updateOne(eq("_id", id),
				combine(set("username", user.getUsername()), set("fullName", user.getFullname()),
						set("dob", user.getDOB()), set("gender", user.getGender()), set("address", user.getAddress()),
						set("email", user.getEmail())));
		System.out.println("successful");
	}

	public void updatePassword(String id, String newPass) {
		CollectionUser().updateOne(eq("_id", id), combine(set("password", newPass)));
		System.out.println("successful");
	}

	public void updateBlock(String id, Boolean blocked) {
		CollectionUser().updateOne(eq("_id", id), combine(set("blocked", blocked)));
		System.out.println("successful");
	}

	public void delete(String id) {
		CollectionUser().deleteMany(eq("_id", id));
		System.out.println("successful");
	}

	public ArrayList<String> getListFriendById(String id) {
		Document filterDoc = new Document();
		filterDoc.append("_id", id);
		MongoCursor<Document> document = CollectionUser().find(filterDoc).iterator();

		ArrayList<String> listData = new ArrayList<String>();
		try {
			listData = (ArrayList<String>) document.next().get("listFriend");
		} finally {
			document.close();
		}
		return listData;
	}

	public Boolean addFriend(String id, String idRequest) {
		ArrayList<String> listData = getListFriendById(id);

		if (listData.contains(idRequest))
			return false;

		listData.add(idRequest);

		CollectionUser().updateOne(eq("_id", id), combine(set("listFriend", listData)));

		System.out.println("successful");
		return true;
	}

	public Boolean deleteFriend(String id, String idRequest) {
		ArrayList<String> listData = getListFriendById(id);

		for (int i = 0; i < listData.size(); i++) {
			if (listData.get(i).equals(idRequest)) {
				listData.remove(i);

				CollectionUser().updateOne(eq("_id", id), combine(set("listFriend", listData)));

				System.out.println("successful");
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getListRequestFriendById(String id) {
		Document filterDoc = new Document();
		filterDoc.append("_id", id);
		MongoCursor<Document> document = CollectionUser().find(filterDoc).iterator();

		ArrayList<String> listData = new ArrayList<String>();
		try {
			listData = (ArrayList<String>) document.next().get("listAddFriend");
		} finally {
			document.close();
		}
		return listData;
	}

	public Boolean addRequestFriend(String id, String idRequest) {
		ArrayList<String> listData = getListFriendById(id);

		if (listData.contains(idRequest))
			return false;

		listData.add(idRequest);

		CollectionUser().updateOne(eq("_id", id), combine(set("listAddFriend", listData)));

		System.out.println("successful");
		return true;
	}

	public Boolean deleteRequestFriend(String id, String idRequest) {
		ArrayList<String> listData = getListFriendById(id);

		for (int i = 0; i < listData.size(); i++) {
			if (listData.get(i).equals(idRequest)) {
				listData.remove(i);

				CollectionUser().updateOne(eq("_id", id), combine(set("listAddFriend", listData)));

				System.out.println("successful");
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getHistoryLoginById(String id) {
		Document filterDoc = new Document();
		filterDoc.append("_id", id);
		MongoCursor<Document> document = CollectionUser().find(filterDoc).iterator();

		ArrayList<String> listData = new ArrayList<String>();
		try {
			listData = (ArrayList<String>) document.next().get("historyLogin");
		} finally {
			document.close();
		}
		return listData;
	}

	public void addLogin(String id) {
		ArrayList<String> listData = getListFriendById(id);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		listData.add(formatter.format(date));

		CollectionUser().updateOne(eq("_id", id), combine(set("historyLogin", listData)));

		System.out.println("successful");
	}
}