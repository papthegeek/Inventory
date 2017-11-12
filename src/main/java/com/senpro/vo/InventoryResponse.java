package com.senpro.vo;

import java.util.ArrayList;

public class InventoryResponse {



	private ArrayList<EventDetail> events = new ArrayList<EventDetail>();
	
	public ArrayList<EventDetail> getEvents() {
		return events;
	}

	public void setEvents(EventDetail event) {
		this.events.add(event);
	}
}
