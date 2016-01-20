package lets.helloworld;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class User {

	private String _id; // email
	private String password;
	private ArrayList<String> allEvents_id;
	private ArrayList<String> currEvent_id;
	private ArrayList<String> interests;
	private ArrayList<String> following;

	private String name;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String gender;
	private String dob;
	private Document doc;
        //private SHA1 sha = new SHA1();

	public User(String _id, String password, String name, String street,
			String city, String state, String zipCode, String gender, String dob) {
		this._id = _id;
		this.name = name;
		this.password = password;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.gender = gender;
		this.dob = dob;
		this.allEvents_id = new ArrayList<String>();
		this.currEvent_id = new ArrayList<String>();
		this.interests = new ArrayList<String>();
		this.following = new ArrayList<String>();
	}

	@SuppressWarnings("unchecked")
	public User(Document userInfo) {
		this._id = (String) userInfo.get("_id");
		Document events = (Document) userInfo.get("events");
		// pull the string push into the string
		this.allEvents_id = (ArrayList<String>) events.get("all_ids");
		this.currEvent_id = (ArrayList<String>) events.get("currEvent_id");
		Document profile = (Document) userInfo.get("profile");
		this.name = (String) profile.get("name");
		Document address = (Document) profile.get("address");
		this.street = (String) address.getString("street");
		this.city = (String) address.get("city");
		this.state = (String) address.get("state");
		this.zipCode = (String) address.get("zipCode");
		this.gender = (String) profile.get("gender");
		this.dob = (String) profile.get("dob").toString();
		this.interests = (ArrayList<String>) userInfo.get("interests");
		this.following = (ArrayList<String>) userInfo.get("following");
	}

	/**
	 * Creating an unlinked user, the user does not follow anyone or participate
	 * in anyEvent yet
	 * 
	 * @return Document
	 * 
	 */
	public Document toDoc() {
		this.doc = new Document("_id", _id)
				.append("password", password)
				.append("profile",
						new Document("name", name)
								.append("address",
										new Document("street", street)
												.append("city", city)
												.append("state", state)
												.append("zipCode", zipCode))
								.append("gender", gender).append("dob", dob))
				.append("events",
						new Document("all_ids", new ArrayList<String>())
								.append("currEvent_ids",
										new ArrayList<String>()))
				.append("interests", new ArrayList<String>())
                                .append("following", getFollowing());
		return doc;
	}

	public String getDOB() {
		return this.dob;
	}

	public String getId() {
		return this._id;
	}

	public ArrayList<String> getAllEvents_id() {
		return allEvents_id;
	}

	public ArrayList<String> getCurrEvent_id() {
		return currEvent_id;
	}

	public ArrayList<String> getInterests() {
		return this.interests;
	}

	public ArrayList<String> getFollowing() {
		return following;
	}

	public void updateOne(String collection, String operation, String key,
			String value, MongoDatabase db) {
		db.getCollection(collection).updateOne(new Document("_id", this._id),
				new Document(operation, new Document(key, value)));

	}

	public void follow(String following_id, MongoDatabase db) {
		this.updateOne("user", "$push", "following", following_id, db);
	}

	public void unfollow(String following_id, MongoDatabase db) {
		this.updateOne("user", "$pop", "following", following_id, db);
	}

	public void addInterests(String tag, MongoDatabase db) {
		this.updateOne("user", "$push", "interests", tag, db);
	}

	public ArrayList<User> getFriends(MongoDatabase db) {
		ArrayList<User> allU = new ArrayList<User>();
		for (int i = 0; i < following.size(); i++) {
			FindIterable<Document> iterable = db.getCollection("user").find(new BasicDBObject("_id", following.get(i)));
			allU.add(new User(iterable.first()));
		}
		return allU;
	}

	public void attendEvent(String event_id, MongoDatabase db) {
		db.getCollection("user")
				.updateOne(
						new Document("_id", this._id),
						new Document("$push", new Document("events.all_ids",
								event_id)));
	}

	public void leaveEvent(String event_id, MongoDatabase db) {
		db.getCollection("event").updateOne(new Document("_id", this._id),
				new Document("$pop", new Document("events.all_ids", event_id)));
	}

	public void addEvent(Event e, MongoDatabase db) {
		db.getCollection("event").insertOne(e.toEventDoc());
	}

	public boolean removeEvent(String event_id, MongoDatabase db) {
		FindIterable<Document> iterable = db.getCollection("event").find(
				new BasicDBObject("_id", event_id));
		Document doc = iterable.first();
		if (this._id.equals((String) doc.get("host_id"))) {
			db.getCollection("event").deleteOne(new Document("_id", event_id));
			return true;
		}
		return false;
	}

	public String toString(){
		return this.toDoc().toJson();
	}
}
