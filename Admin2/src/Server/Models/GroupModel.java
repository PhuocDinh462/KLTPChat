package Server.Models;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class GroupModel {
	public MongoCollection<Document> CollectionGroup() {
		String uri = "mongodb+srv://admin:admin123@cluster0.wbqiils.mongodb.net/?retryWrites=true&w=majority";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("Database");

		MongoCollection<Document> collection = mongoDatabase.getCollection("Group");
		return collection;
	}
}
