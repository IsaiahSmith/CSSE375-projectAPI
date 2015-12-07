package lets.helloworld;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import static java.util.Arrays.asList;

public class mongoDbAPI {
	private MongoDatabase db;
	private SHA1 sha;
	
	public mongoDbAPI(MongoDatabase db){
		this.db=db;
		this.sha = new SHA1();
	}
	
	public int insertUser(User u) {
		try{
		db.getCollection("user").insertOne(u.toDoc());
			return 1;
		}catch(MongoWriteException e){
			return e.getCode();
		}
	}
        
        public Document getUser(String user_id) {
		FindIterable<Document> iterable = db.getCollection("user").find(
				new BasicDBObject("_id", user_id));
		Document doc = iterable.first();
		if (doc == null) {
			return new Document("false", 1);
		}
		return doc;
	}
		
	public ArrayList<Event> getAllEvents(){
		ArrayList<Event> allE = new ArrayList<Event>();
		FindIterable<Document> iterable = db.getCollection("event").find();
		iterable.forEach(new Block<Document>(){
			@Override
			public void apply(Document arg0) {
				allE.add(new Event(arg0));
//				System.out.println(arg0);
			}
		});
		return allE;
	}
	
	public Event getEvent(String eventId){
		FindIterable<Document> iterable = db.getCollection("event").find(new BasicDBObject("_id", eventId));
		return  new Event(iterable.first());
	}
	
	public void updateOne(String collection, String id, String operation, String key, String value){
		db.getCollection(collection).updateOne(new Document("_id", id),
		        new Document(operation, new Document(key, value)));
	}	
	
	public boolean resetPassword(String currentPassword,String password, String owner_id){
		FindIterable<Document> iterable = db.getCollection("test").find(new BasicDBObject("_id", owner_id));
        Document doc = iterable.first();
        String test = doc.getString("password");
        if(test.equals(password)){
    		this.updateOne("user", owner_id, "$set", "password",password);
        	return true;
        }
        return false;
	}
	
	public Document login(String owner_id, String password){
		FindIterable<Document> iterable = db.getCollection("user").find(new BasicDBObject("_id", owner_id));
        Document doc = iterable.first();
        String test = doc.getString("password");
        password = sha.encrypt(password);
        if(test.equals(password)){
        	return doc;
        }
		return new Document("false",1);
	}
	
	public static Point2D.Double getLoc() {
		try {
			URL geoLocation = new URL("http://www.telize.com/geoip");
			URLConnection l = geoLocation.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					l.getInputStream()));
			String inputLine = in.readLine();
			in.close();
			JSONObject obj = new JSONObject(inputLine);
			double x = (double) obj.get("latitude");
			double y = (double) obj.get("longitude");
			return new Point2D.Double(x, y);
		} catch (Exception e) {
			System.out.println("url exception");
		}
		return null;
	}

	public void findNearByEvent(Point2D.Double loc, double maxD) {
//		BasicDBObject filter = new BasicDBObject("$near", new double[]{loc.x,loc.y});
//		filter.put("$maxDistance", maxD);
//		BasicDBObject query = new BasicDBObject("geoLocation", filter);
//		FindIterable<Document> ev = db.getCollection("event").find(query);
		
		Document query = new Document("geoLocation",new Document("$near",new Document("geoLocation", asList(loc.x, loc.y))));
		FindIterable<Document> ev = db.getCollection("event").find(query);
		ev.forEach(new Block<Document>(){
			@Override
			public void apply(final Document document){
				System.out.println(document);
			}
		});
	}

	
	

}
