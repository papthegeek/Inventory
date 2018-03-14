package com.senpro.controller;

import java.util.List;


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

	@RequestMapping(value = "inventorylist/{id}", headers = "Accept=application/json", method = RequestMethod.GET)
	public InventoryResponse getInventoryList(
			@PathVariable(value = "id") String key) {
		InventoryResponse response = inventoryService.getAllInventory(Integer
				.parseInt(key));
		return response;
	}

	@RequestMapping(value = "eventDetails/{id}", headers = "Accept=application/json", method = RequestMethod.GET)
	public InventoryResponse getEventDetails(
			@PathVariable(value = "id") String key) {
		return inventoryService.getEventRecord(Integer
				.parseInt(key));
	}

	@RequestMapping(value = "addEvents", headers = "Accept=application/json", consumes = "application/json", method = RequestMethod.POST)
	public InventoryResponse  UpdateInventory(@RequestBody List<InventoryRequest> request) {
		return inventoryService.addRecordsToInventory(request);
	}
	
	@RequestMapping(value ="removeEvent/{id}" , headers = "Accept=application/json", method = RequestMethod.DELETE)
	public InventoryResponse removeRecord(@PathVariable(value = "id") String  key, @RequestBody InventoryRequest request){
		return inventoryService.removeRecord(Integer.parseInt(key), request);
	}

}
