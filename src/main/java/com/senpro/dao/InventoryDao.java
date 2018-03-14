package com.senpro.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senpro.entity.EventDetailTable;
import com.senpro.exception.BaseServiceException;
import com.senpro.utils.InventoryConstants;

@Repository("inventoryDao")
public class InventoryDao {

	Log LOG = LogFactory.getLog(InventoryDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	public List<EventDetailTable> getAllInventory(int key) {
		List<EventDetailTable> eventDetailsRecords = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			eventDetailsRecords = session.createQuery(
					"from EventDetailTable WHERE eventID > :key").setInteger("key", key).setMaxResults(6).list();

			if (null != eventDetailsRecords && eventDetailsRecords.size() > 0) {
				LOG.info("Records Retrieved from DB");
			} else {
				LOG.error("nothing was fetched from DB");
			}
		} catch (HibernateException e) {
			LOG.error(new StringBuilder("EXCEPTION: ").append(HibernateException.class.getName()).append(" CAUSE: ").append(e.getCause().toString()).append(" MESSAGE: ").append(e.getMessage()));
		}
		return eventDetailsRecords;
	}

	public EventDetailTable getEvent(int key) {
		
		EventDetailTable record = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			record = (EventDetailTable) session
					.get(EventDetailTable.class, key);
			
			if(null != record){
				LOG.info("Record successfully retrieved!! id: " + key);
			}
			else 
				LOG.error("Record was not found in DB!!! ");
		} catch (Exception e) {
			LOG.error(new StringBuilder("EXCEPTION: ").append(Exception.class.getName()).append(" CAUSE: ").append(e.getCause().toString()).append(" MESSAGE: ").append(e.getMessage()));
		}
		return record;
	}

	public void addRecord(EventDetailTable record) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(record);
			LOG.info(" Record inserted into DB successfully !!");
		} catch (Exception ex) {
			LOG.error("Error updating records into DB! Rolling back changes! Cause: "
					+ ex.getMessage());
			throw ex;
		} 
	}

	public int deleteRecord(Integer key) {
		int isdeleted = 0;
		try {

			Session session = this.sessionFactory.getCurrentSession();
			isdeleted = session
					.getNamedQuery(
							InventoryConstants.NAMED_QUERY_deleteRecordById)
					.setParameter("id", key).executeUpdate();
			if (isdeleted > 0)
				LOG.info(" Record successfully Deleted from DB  !!");
			else
				LOG.error(" Record NOT found in DB!!");
		} catch (Exception ex) {
			LOG.error("Error Deleting records from DB! Cause: "
					+ ex.getMessage());
		}
		return isdeleted;
	}

}
