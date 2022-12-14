package Server;
import java.net.UnknownHostException;
import com.mongodb.*;
public class ConnectDB {

	public static MongoClient mongoClient;
	public static DB database;
	public static DBCollection collection;
	
	public static void main(String[] args) throws UnknownHostException {
		mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))
		database = mongoClient.getDB("JavaProgramming"); // nếu không có db sẽ tự tạo
		collection = database.getCollection("ChatSystem"); // lấy collection
		User user = new User();
		user.setFriend(null);
		//... set and insert to database
		collection.insert(convert(user));
		
		// find object
		InforUser infor = new InforUser();
		DBObject query = new BasicObject("UserInfor", infor);
		DBCursor cursor = collection.find(query);
		//collection.findOne(DBObject obj)
		//collection.findAndRemove(DBObject query) if query is empty it will remove everything
		//collection.findAndModify(DBObject obj) hay (DBObject query)
		System.out.println(cursor.one); // limit to the first one
		while (cursor.hasNext()) {
			DBObject next = cursor.next();
			System.out.println(next);
		}
		
		User newUser = new User();
		newUser.setFriend(null);
		// set...
		
		collection.findAndModify(query,convert(newUser)); // tìm ra đúng user đó và thay thế bằng newUser
		
		
	}
	public static DBObject convert(User user) {
		return new BasicObject("UserInfor", user.getInfor()).append("timeLogIn",user.getTimeLogin()).append("friendList",user.getFriend()).append("friendRequestList",user.getAddFriendRequest());
	}
	
}
