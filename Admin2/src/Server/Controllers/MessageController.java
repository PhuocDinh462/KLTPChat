package Server.Controllers;

import static com.mongodb.client.model.Filters.eq;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;

import com.mongodb.client.MongoCursor;
import Server.Classes.Message;
import Server.Models.MessageModel;

public class MessageController extends MessageModel {
	public void create(Message msg) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Document document = new Document("createdBy", msg.getUserId()).append("desId", msg.getDestinationId())
				.append("content", msg.getContent()).append("createTime", formatter.format(date));

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

	public void delete(String id) {
		CollectionMessage().deleteMany(eq("_id", id));
	}

	public void findMessageByContent(String content) {
		Document doc = new Document();
		doc.append("content", content);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();

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
		doc.append("_id", id);
		MongoCursor<Document> document = CollectionMessage().find(doc).iterator();
		try {
			while (document.hasNext()) {
				System.out.println(document.next().toJson());
			}
		} finally {
			document.close();
		}

		System.out.print("Successfull");
	}
}
