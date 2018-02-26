package com.senpro.vo;

public class EventDetail {
	
	private String eventTitle;
	private String eventDate;
	private String eventStartTime;
	private String eventEndTime;
	private String ticketPrice;
	private String nbrOfTicketsLeft;
	private Seating seating;
	private String description;
	private String imagePath;
	private String eventId;
	
	
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventStartTime() {
		return eventStartTime;
	}
	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}
	public String getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getNbrOfTicketsLeft() {
		return nbrOfTicketsLeft;
	}
	public void setNbrOfTicketsLeft(String nbrOfTicketsLeft) {
		this.nbrOfTicketsLeft = nbrOfTicketsLeft;
	}
	public Seating getSeating() {
		return seating;
	}
	public void setSeating(Seating seating) {
		this.seating = seating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	
}
