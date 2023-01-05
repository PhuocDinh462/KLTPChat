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
import com.mongodb.client.model.UpdateOptions;

import Server.Classes.InforUser;
import Server.Classes.User;
import Server.Models.UserModel;

public class UserController extends UserModel {
	public Boolean create(User newUser) {

		ArrayList<User> users = getAllUsers();

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getInfor().getUsername().equals(newUser.getInfor().getUsername())
					|| users.get(i).getInfor().getEmail().equals(newUser.getInfor().getEmail())) {
				System.out.println("Already have username or email in database");
				return false;
			}
		}
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
		return true;
	}

	public Document getDocumentInforUser(User user) {
		Document document = new Document("username", user.getInfor().getUsername())
				.append("password", user.getInfor().getPassword()).append("fullName", user.getInfor().getFullname())
				.append("dob", user.getInfor().getDOB()).append("gender", user.getInfor().getGender())
				.append("address", user.getInfor().getAddress()).append("email", user.getInfor().getEmail())
				.append("blocked", user.getInfor().getBlocked());
		return document;
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
		System.out.println("Successful");
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
		System.out.println("Successful");
		return users;
	}

	public User getUserById(String id) {
		Document doc = new Document();
		Gson gson = new Gson();
		doc.append("_id", id);
		MongoCursor<Document> document = CollectionUser().find(doc).iterator();

		System.out.println("Successful get User");

		return gson.fromJson(document.next().toJson(), User.class);
	}

	public User getUserByUsername(String username) {
		ArrayList<User> getUsers = getAllUsers();
		String id = "";
		for (User e : getUsers) {
			if (e.getInfor().getUsername().equals(username))
				id = e.getId();
		}
		System.out.println("Successful get User");
		return getUserById(id);
	}

	public void update(String username, InforUser user) {
		User getUser = getUserByUsername(username);
		getUser.setInfor(user);
		Document doc = getDocumentInforUser(getUser);

		CollectionUser().updateOne(eq("_id", getUser.getId()), combine(set("InforUser", doc)));
		System.out.println("successful");
	}

	public void updatePassword(String username, String newPass) {
		User getUser = getUserByUsername(username);
		getUser.getInfor().setPassword(newPass);
		Document doc = getDocumentInforUser(getUser);

		CollectionUser().updateOne(eq("_id", getUser.getId()), combine(set("InforUser", doc)));
		System.out.println("successful");
	}

	public void updateBlock(String username, Boolean blocked) {
		User getUser = getUserByUsername(username);
		if (getUser.getInfor().getBlocked() != blocked) {
			getUser.getInfor().setBlocked(blocked);
			Document doc = getDocumentInforUser(getUser);

			CollectionUser().updateOne(eq("_id", getUser.getId()), combine(set("InforUser", doc)),
					new UpdateOptions().upsert(true));
		}
		System.out.println("successful");
	}

	public void updateStatus(String username, Boolean status) {
		User getUser = getUserByUsername(username);
		getUser.getInfor().setStatus(status);
		Document doc = getDocumentInforUser(getUser);

		CollectionUser().updateOne(eq("_id", getUser.getId()), combine(set("InforUser", doc)),
				new UpdateOptions().upsert(true));

		System.out.println("successful");
	}

	public void deleteByUsername(String username) {
		User getUser = getUserByUsername(username);
		
		CollectionUser().deleteMany(eq("_id", getUser.getId()));
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
		ArrayList<String> listData = getListRequestFriendById(id);

		if (listData.contains(idRequest))
			return false;

		listData.add(idRequest);

		CollectionUser().updateOne(eq("_id", id), combine(set("listAddFriend", listData)));

		System.out.println("successful");
		return true;
	}

	public Boolean deleteRequestFriend(String id, String idRequest) {
		ArrayList<String> listData = getListRequestFriendById(id);

		for (int i = 0; i < listData.size(); i++) {
			if (listData.get(i).equals(idRequest)) {
				listData.remove(i);

				CollectionUser().updateOne(eq("_id", id), combine(set("listAddFriend", listData)));

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
		ArrayList<String> listData = getHistoryLoginById(id);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		listData.add(formatter.format(date));

		CollectionUser().updateOne(eq("_id", id), combine(set("historyLogin", listData)));

		System.out.println("successful");
	}
}
