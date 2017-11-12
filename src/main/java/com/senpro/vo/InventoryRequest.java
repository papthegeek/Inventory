package com.senpro.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class InventoryRequest {

	
	private String eventTitle;
	private String eventDate;
	private String eventStartTime;
	private String eventEndTime;
	private BigDecimal ticketPrice;
	private int nbrOfTicketsLeft;
	private String description;
	private List<InventoryRequest> requestList = new ArrayList<InventoryRequest>();
	
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
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getNbrOfTicketsLeft() {
		return nbrOfTicketsLeft;
	}
	public void setNbrOfTicketsLeft(int nbrOfTicketsLeft) {
		this.nbrOfTicketsLeft = nbrOfTicketsLeft;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<InventoryRequest> getRequestList() {
		return requestList;
	}
	public void setRequestList(List<InventoryRequest> requestList) {
		this.requestList = requestList;
	}
	
	
}
