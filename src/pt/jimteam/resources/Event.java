package pt.jimteam.resources;

public class Event {

	String id;
	String owner;
	String name;
	String description;
	String start_time;
	String end_time;
	String location;
	String venue;
	String updated_time;
	String privacy;
	String picture;

	public Event() {
		super();
	}

	public Event(String id, String owner, String name, String description,
			String start_time, String end_time, String location, String venue,
			String updated_time, String privacy, String picture) {
		super();
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.start_time = start_time;
		this.end_time = end_time;
		this.location = location;
		this.venue = venue;
		this.updated_time = updated_time;
		this.privacy = privacy;
		this.picture = picture;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
