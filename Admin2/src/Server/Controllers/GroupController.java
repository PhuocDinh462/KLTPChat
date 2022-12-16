package Server.Controllers;

import static com.mongodb.client.model.Filters.eq;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import Server.Classes.Group;
import Server.Models.GroupModel;

public class GroupController extends GroupModel {
	public void create(Group grp) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Document document = new Document("name", grp.getGroupName()).append("managers", grp.getManagers())
				.append("users", grp.getuserId()).append("messages", grp.getmessageId())
				.append("createTime", formatter.format(date));

		CollectionGroup().insertOne(document);
		System.out.println("successful");
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
		System.out.print("Successfull");
	}

	public void delete(String id) {
		CollectionGroup().deleteMany(eq("id", id));
	}

	public void findMessageByContent(String content) {
		Document doc = new Document();
		doc.append("content", content);
		MongoCursor<Document> document = CollectionGroup().find(doc).iterator();

		try {
			while (document.hasNext()) {
				System.out.println(document.next().toJson());
			}
		} finally {
			document.close();
		}

		System.out.print("Successfull");
	}

	public void findMessageById(String id) {
		Document doc = new Document();
		doc.append("id", id);
		MongoCursor<Document> document = CollectionGroup().find(doc).iterator();
		try {
			while (document.hasNext()) {
				System.out.println(document.next().toJson());
			}
		} finally {
			document.close();
		}

		System.out.print("Successfull");
	}

	public void updateGroupName(String newName, String id) {

		Document query = new Document().append("id", id);

		Bson updates = Updates.combine(Updates.set("name", newName));

		UpdateOptions options = new UpdateOptions().upsert(true);
		try {
			UpdateResult result = CollectionGroup().updateOne(query, updates, options);
			System.out.println("Modified document count: " + result.getModifiedCount());
			System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is
																		// performed
		} catch (MongoException me) {
			System.err.println("Unable to update due to an error: " + me);
		}
	}

}
