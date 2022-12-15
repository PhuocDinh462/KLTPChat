package Server.Models;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class UserModel {
	public MongoCollection<Document> CollectionUser() {
		String uri = "mongodb+srv://admin:admin123@cluster0.wbqiils.mongodb.net/?retryWrites=true&w=majority";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		try (MongoClient mongoClient = new MongoClient(mongoClientURI)) {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("Database");

			MongoCollection<Document> collection = mongoDatabase.getCollection("User");
			System.out.println(collection);
			System.out.println("Connection successful!");
			return collection;
		}
	}
}

//package Server.Models;

//import org.bson.Document;
//
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//public class UserModel {
//	public DBCollection CollectionUser() {
//		String uri = "mongodb+srv://admin:admin123@cluster0.wbqiils.mongodb.net/?retryWrites=true&w=majority";
//		MongoClientURI mongoClientURI = new MongoClientURI(uri);
//		MongoClient mongoClient = new MongoClient(mongoClientURI);
//		System.out.println("Connection complete");
//		MongoDatabase mongoDatabase = mongoClient.getDatabase("Database");
//
//		DBCollection collection = (DBCollection) mongoDatabase.getCollection("User");
//		
//		return collection;
//	}
//}