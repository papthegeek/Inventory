package com.senpro.controller;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.senpro.service.InventoryService;
import com.senpro.vo.InventoryRequest;
import com.senpro.vo.InventoryResponse;

@RestController
@RequestMapping(value = "inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@RequestMapping(value = "inventorylist", headers = "Accept=application/json", method = RequestMethod.GET)
	public InventoryResponse getInventoryList() {
		InventoryResponse response = inventoryService.getAllInventory();
		return response;
	}

	@RequestMapping(value = "eventDetails/{id}", headers = "Accept=application/json", method = RequestMethod.GET)
	public Response getEventDetails(
			@PathVariable(value = "id") String key) {
		return inventoryService.getEventRecord(Integer
				.parseInt(key));
	}

	@RequestMapping(value = "addEvents", headers = "Accept=application/json", consumes = "application/json", method = RequestMethod.POST)
	public Response UpdateInventory(@RequestBody List<InventoryRequest> request) {
		inventoryService.addRecordsToInventory(request);
		return Response.status(Status.OK).entity("Records successfully added").build();
	}
	
	@RequestMapping(value ="removeEvent/{id}" , headers = "Accept=application/json", method = RequestMethod.DELETE)
	public Response removeRecord(@PathVariable(value = "id") String  key){
		int result = inventoryService.removeRecord(Integer.parseInt(key));
		if(result > 0){
		
			return Response.status(Status.OK).entity("Record successfully deleted! id: " + key).build();
		}
		return Response.status(Status.NOT_FOUND).entity("Record Was NOT successfully deleted! id: " + key).build();	
	}

}
