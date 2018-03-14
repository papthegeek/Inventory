package com.senpro.vo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class InventoryResponse {



	private ArrayList<EventDetail> events = new ArrayList<EventDetail>();
	private CodeDesc responseCodeDesc;
	private ResponseError error;
	
	public ArrayList<EventDetail> getEvents() {
		return events;
	}

	public void setEvents(EventDetail event) {
		this.events.add(event);
	}

	public CodeDesc getResponseCodeDesc() {
		return responseCodeDesc;
	}

	public void setResponseCodeDesc(CodeDesc responseCodeDesc) {
		this.responseCodeDesc = responseCodeDesc;
	}

	public ResponseError getError() {
		return error;
	}

	public void setError(ResponseError error) {
		this.error = error;
	}
}
