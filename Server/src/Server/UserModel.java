package Server;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
public class UserModel {
	public MongoCollection<Document> CollectionAccount() {
		String uri = "mongodb+srv://longit7557:19667557@chatappjava.wntnalz.mongodb.net/?retryWrites=true&w=majority";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("Database");

		MongoCollection<Document> collection = mongoDatabase.getCollection("Account");
		return collection;
	}
}
