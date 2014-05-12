package com.shaban.goals.dao;

import java.util.List;

import com.shaban.goals.data.Notification;

public interface NotificationDAO extends DAO {
	
	public Notification load(long notifId);
	public boolean update(Notification notification);
	public long create(Notification notification);
	public List<Notification> getGoalsNotifications(Long goalId);
	
}
