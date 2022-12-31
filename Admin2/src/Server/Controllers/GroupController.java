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
import Server.Classes.Group;
import Server.Classes.InforUser;
import Server.Classes.Message;
import Server.Classes.User;
import Server.Models.GroupModel;

public class GroupController extends GroupModel {
	public Boolean create(Group grp) {
		ArrayList<Group> groups = getAllGroups();

		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getGroupName().equals(grp.getGroupName())) {
				System.out.println("Already have this group name in database");
				return false;
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Document document = new Document("_id", grp.getGroupId()).append("name", grp.getGroupName())
				.append("listUsers", grp.getlistUsers()).append("listManagers", grp.getManagers())
				.append("listMessage", grp.getmessageId()).append("createTime", formatter.format(date));
		CollectionGroup().insertOne(document);
		System.out.println("successful");
		return true;
	}

	public void read() {
		MongoCursor<Document> document = CollectionGroup().find().iterator();
		try {
			while (document.hasNext()) {
				System.out.println(document.next().toJson());
			}
		} finally {
			document.close();
		}
		System.out.println("successful");
	}

	public ArrayList<Group> getAllGroups() {
		MongoCursor<Document> document = CollectionGroup().find().iterator();
		ArrayList<Group> groups = new ArrayList<Group>();
		Gson gson = new Gson();

		try {
			while (document.hasNext()) {
				Document doc = document.next();
				Group addGroup = gson.fromJson(doc.toJson(), Group.class);
				groups.add(addGroup);
			}
		} finally {
			document.close();
		}
		System.out.println("Successful");
		return groups;
	}

	public Boolean addPeopleGroup(String idUser, String idGroup) {

		ArrayList<String> listData = searchListUsers(idGroup);

		if (listData.contains(idUser))
			return false;

		listData.add(idUser);

		CollectionGroup().updateOne(eq("_id", idGroup), combine(set("listUsers", listData)));

		System.out.println("successful");
		return true;
	}
	
	public Boolean addNewMessage(String idMsg, String idGroup) {

		ArrayList<String> listData = searchListMessage(idGroup);

		listData.add(idMsg);

		CollectionGroup().updateOne(eq("_id", idGroup), combine(set("listMessage", listData)));

		System.out.println("successful");
		return true;

	}

	public Boolean removePeopleGroup(String idUser, String idGroup) {

		ArrayList<String> listData = searchListUsers(idGroup);

		for (int i = 0; i < listData.size(); i++) {
			if (listData.get(i).equals(idUser)) {
				listData.remove(i);

				CollectionGroup().updateOne(eq("_id", idGroup), combine(set("listFriend", listData)));
				System.out.println("successful");

				return true;
			}
		}
		return false;
	}

	public ArrayList<String> searchListMessage(String idGroup) {
		Document filterDoc = new Document();
		filterDoc.append("_id", idGroup);
		MongoCursor<Document> document = CollectionGroup().find(filterDoc).iterator();

		ArrayList<String> listData = new ArrayList<String>();
		try {
			listData = (ArrayList<String>) document.next().get("listMessage");
		} finally {
			document.close();
		}
		System.out.println("successful");
		return listData;
	}

	public ArrayList<String> searchListUsers(String idGroup) {
		Document filterDoc = new Document();
		filterDoc.append("_id", idGroup);
		MongoCursor<Document> document = CollectionGroup().find(filterDoc).iterator();

		ArrayList<String> listData = new ArrayList<String>();
		try {
			listData = (ArrayList<String>) document.next().get("listUsers");
		} finally {
			document.close();
		}
		System.out.println("successful");
		return listData;
	}
	
	public ArrayList<String> searchListUsersByGroupName(String GroupName) {
		Document filterDoc = new Document();
		filterDoc.append("name", GroupName);
		MongoCursor<Document> document = CollectionGroup().find(filterDoc).iterator();

		ArrayList<String> listData = new ArrayList<String>();
		try {
			listData = (ArrayList<String>) document.next().get("listUsers");
		} finally {
			document.close();
		}
		System.out.println("successful");
		return listData;
	}

	public void update(String idGroup, String nameGroup) {
		CollectionGroup().updateOne(eq("_id", idGroup), combine(set("name", nameGroup)));
		System.out.println("successful");
	}

	public void deleteGroup(String idGroup) {
		CollectionGroup().deleteMany(eq("_id", idGroup));
		System.out.println("successful");
	}
}