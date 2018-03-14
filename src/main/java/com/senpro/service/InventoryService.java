package com.senpro.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.senpro.dao.InventoryDao;
import com.senpro.entity.EventDetailTable;
import com.senpro.utils.InventoryConstants;
import com.senpro.vo.CodeDesc;
import com.senpro.vo.EventDetail;
import com.senpro.vo.InventoryRequest;
import com.senpro.vo.InventoryResponse;
import com.senpro.vo.ResponseError;

@Service("inventoryService")
public class InventoryService {

	@Autowired
	InventoryDao inventoryDao;

	Log LOG = LogFactory.getLog(InventoryService.class);

	@Transactional
	public InventoryResponse getAllInventory(int key) {

		List<EventDetailTable> events = null;
		InventoryResponse response = new InventoryResponse();
		try {
			events = inventoryDao.getAllInventory(key);
			if (CollectionUtils.isNotEmpty(events)) {
				response = getInventoryList(events);
			}else {
				ResponseError error = new ResponseError();
				error.setCode("500001");
				error.setDescription("Could not retrieve record");
				response.setError(error);
			}
		} catch (Exception e) {
			LOG.error("Exception occured in getAllInventory method!!!");
		}

		return response;
	}

	@Transactional
	public InventoryResponse getEventRecord(int key) {
		InventoryResponse inventoryResponse = new InventoryResponse();
		List<EventDetailTable> events = null;
		EventDetailTable event = null;
		try {
			event = inventoryDao.getEvent(key);
			if (null != event && event.getEventId() != null) {
				events = new ArrayList<EventDetailTable>();
				events.add(event);inventoryResponse = getInventoryList(events);				
			} else {
				ResponseError error = new ResponseError();
				error.setCode("500001");
				error.setDescription("Could not retrieve record");
				inventoryResponse.setError(error);
			}
		} catch (Exception ex) {
			LOG.error("Exception occured in getEventRecord method!!!");
		}
		return inventoryResponse;
	}

	@Transactional
	public InventoryResponse addRecordsToInventory(List<InventoryRequest> requestList) {
		
		InventoryResponse inventoryResponse = new InventoryResponse();
		try {
			if (!CollectionUtils.isEmpty(requestList)) {
				for (InventoryRequest request : requestList) {

					if (!StringUtils.isEmpty(request.getEventTitle())) {
						EventDetailTable record = new EventDetailTable();
						record.setDescription(request.getDescription());
						record.setEventDate(request.getEventDate());
						record.setEventTitle(request.getEventTitle());
						record.setNbrOfTicketsLeft(request.getNbrOfTicketsLeft());
						record.setTicketPrice(request.getTicketPrice());
						String imagePath = addCloudinaryImage(request.getImageString());
						if(!StringUtils.isEmpty(imagePath)) {
							record.setImagePath(imagePath);
						}
						
						if (null != request.getEventStartTime()) {
							if (isValidTimeFormat(request.getEventStartTime())) {
								record.setEventStartTime(convertHMmSsToSeconds(request.getEventStartTime()));
							} else {
								LOG.error("Given Start Time is not in correct format !!");
							}
						}
						if (null != request.getEventEndTime()) {
							if (isValidTimeFormat(request.getEventEndTime())) {
								record.setEventEndTime(convertHMmSsToSeconds(request.getEventEndTime()));
							} else {
								LOG.error("Given End Time is not in correct format !!");
							}
						}
						inventoryDao.addRecord(record);
						CodeDesc responseCodeDesc = new CodeDesc();
						responseCodeDesc.setCode("200002");
						responseCodeDesc.setMessage("Record Successfully added");
						inventoryResponse.setResponseCodeDesc(responseCodeDesc);
					}
				}
			}
		} catch (Exception ex) {
			LOG.error("Exception occured in addRecordsToInventory method! cause:" + ex.getMessage());
			ResponseError error = new ResponseError();
			error.setCode("500002");
			error.setDescription("Underlaying data layer error");
			inventoryResponse.setError(error);
		}
		return inventoryResponse;
	}

	@Transactional
	public InventoryResponse removeRecord(Integer key, InventoryRequest request) {

		deleteCloudinaryImage(request.getImagePath());
		InventoryResponse inventoryResponse = new InventoryResponse();
		int result = inventoryDao.deleteRecord(key);
		if(result > 0){
			
			CodeDesc responseCodeDesc = new CodeDesc();
			responseCodeDesc.setCode("200003");
			responseCodeDesc.setMessage("Record Successfully Deleted");
			inventoryResponse.setResponseCodeDesc(responseCodeDesc);
		}else {
			ResponseError error = new ResponseError();
			error.setCode("500003");
			error.setDescription("Underlaying data layer error");
			inventoryResponse.setError(error);
		}
	
		return inventoryResponse;
	}

	private InventoryResponse getInventoryList(List<EventDetailTable> events) {
		InventoryResponse response = null;
		try {
			if (null != events && !CollectionUtils.isEmpty(events)) {
				response = new InventoryResponse();
				for (EventDetailTable detail : events) {
					if (null != detail) {
						EventDetail eventdetails = new EventDetail();
						if (!StringUtils.isEmpty(detail.getEventTitle())) {
							eventdetails.setEventTitle(detail.getEventTitle());
						}
						if (!StringUtils.isEmpty(detail.getEventDate())) {
							eventdetails.setEventDate(detail.getEventDate());
						}
						if (!StringUtils.isEmpty(detail.getEventStartTime())) {
							eventdetails
									.setEventStartTime(convertSecondsToHMmSs(detail.getEventStartTime().longValue()));
						}
						if (!StringUtils.isEmpty(detail.getEventEndTime())) {
							eventdetails.setEventEndTime(convertSecondsToHMmSs(detail.getEventEndTime().longValue()));
						}
						if (!StringUtils.isEmpty(detail.getTicketPrice())) {
							eventdetails.setTicketPrice(detail.getTicketPrice().toPlainString());
						}
						if (!StringUtils.isEmpty(detail.getNbrOfTicketsLeft())) {
							eventdetails.setNbrOfTicketsLeft(Integer.toString(detail.getNbrOfTicketsLeft()));
						}
						if (!StringUtils.isEmpty(detail.getDescription())) {
							eventdetails.setDescription(detail.getDescription());
						}
						if (!StringUtils.isEmpty(detail.getImagePath())) {
							eventdetails.setImagePath(detail.getImagePath());
						}
						if (!StringUtils.isEmpty(detail.getEventId())) {
							eventdetails.setEventId(Integer.toString(detail.getEventId()));
						}
						response.getEvents().add(eventdetails);
					}
				}
			} else {
				LOG.error("Requested Record not found in / returned from DB!!");
			}

		} catch (Exception e) {
			LOG.error("ERROR occured while mapping Db record to Inventory Response Object!!");
		}
		return response;
	}

	public static String convertSecondsToHMmSs(long seconds) {
		long s = seconds % 60;
		long m = (seconds / 60) % 60;
		long h = (seconds / (60 * 60)) % 24;
		return String.format("%02d:%02d:%02d", h, m, s);
	}

	public static Integer convertHMmSsToSeconds(String time) {
		String[] units = time.split(":");
		Integer seconds = (Integer.parseInt(units[0]) * 3600 + Integer.parseInt(units[1]) * 60
				+ Integer.parseInt(units[2]));
		return seconds;
	}

	public boolean isValidTimeFormat(String time) {
		SimpleDateFormat format = new SimpleDateFormat(InventoryConstants.TIME_FORMAT_HHMMSS);
		try {
			format.parse(time);
			return true;
		} catch (Exception e) {
			LOG.error("Given Time is in Invalid format !!");
			return false;
		}
	}

	private String addCloudinaryImage(String imageString) {
		String url = null;
		try {

			Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(InventoryConstants.CloudName,
					InventoryConstants.cdnCloudName, InventoryConstants.ApiKey, InventoryConstants.cdnApiKey,
					InventoryConstants.Secret, InventoryConstants.cdnSecret));

			url = (String) cloudinary.uploader().upload(imageString, ObjectUtils.emptyMap()).get(InventoryConstants.cdnUrl);

		} catch (IOException e) {

		}
		return url;
	}
	
	
	private void deleteCloudinaryImage(String imagePath) {
		try {
			Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(InventoryConstants.CloudName,
					InventoryConstants.cdnCloudName, InventoryConstants.ApiKey, InventoryConstants.cdnApiKey,
					InventoryConstants.Secret, InventoryConstants.cdnSecret));

			
			
			cloudinary.uploader().destroy(getImageCloudProductId(imagePath), ObjectUtils.emptyMap());		

		} catch (Exception e) {
			LOG.info("Exception occured while deleteing Cloudinary image!");
		}
	}
	
	private String getImageCloudProductId(String imagePath) {
		String productId = org.apache.commons.lang3.StringUtils.substring(imagePath,org.apache.commons.lang3.StringUtils.lastIndexOf(imagePath, "/") + 1);
		return productId.substring(0,productId.indexOf("."));
		
	}



}
