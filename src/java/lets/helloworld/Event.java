package lets.helloworld;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import static java.util.Arrays.asList;

public class Event {
	private String _id;
	private String host_id;
	private String title;
	private Point2D.Double loc;
	private Date startingTime;
	private Date endTime;
	private String descritpion;
	private ArrayList<String> tags;
	

	@SuppressWarnings("unchecked")
	public Event(Document eventInfo) {
		this._id = (String) eventInfo.get("_id");
		this.host_id = (String) eventInfo.getString("host_id");
		this.startingTime = (Date) eventInfo.getDate("starting_time");
		this.endTime = (Date) eventInfo.getDate("end_time");
		this.title = (String) eventInfo.get("title");
		this.descritpion = eventInfo.getString("description");
		this.tags= (ArrayList<String>) eventInfo.get("tags");
		//how to put points into mongo?
		Document location = (Document) eventInfo.get("location");
		ArrayList<java.lang.Double> l = (ArrayList<java.lang.Double>) location.get("coordinates");
		this.loc = new Point2D.Double(l.get(0),l.get(1));
	}

	public Event( String host_id, String title, Point2D.Double loc,
		Date startingTime, Date endtTime, String description, ArrayList<String> tags) {
		this._id = ObjectId.get().toString();
		this.host_id = host_id;
		this.title = title;
		this.loc = loc;
		this.startingTime = startingTime;
		this.endTime = endtTime;
		this.descritpion = description;
		this.tags = tags;
	}
	
	public Document toEventDoc(){
		Document event = new Document("_id", _id)
										.append("host_id", host_id)
										.append("title", title)
										.append("tags", tags)
										.append("location",new Document("type","Point").append("coordinates", asList(loc.x,loc.y)))
										.append("starting_time", startingTime)
										.append("end_time", endTime)
										.append("description", descritpion);			
		return event;
	}
	
	public Date getStartingTime(){
		return this.startingTime;
	}
	
	public Point2D.Double getLocation(){
		return this.loc;
	}
	
	public String toString(){
		return this.toEventDoc().toJson();
	}
	

}
