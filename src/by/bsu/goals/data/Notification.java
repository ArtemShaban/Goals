package by.bsu.goals.data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Sergey Datskevich May 12, 2014 5:29:06 PM
 *
 *
 */

public class Notification {
	
	private long notifiacationId;
	private Long goalId;
	private int repeatNumber;
	private Time repeatTime;
	private Timestamp notifyAt;
	private String uri;
	
	public Notification() {
		super();	
	}
	
	public long getNotifiacationId() {
		return notifiacationId;
	}

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public int getRepeatNumber() {
		return repeatNumber;
	}

	public void setRepeatNumber(int repeatNumber) {
		this.repeatNumber = repeatNumber;
	}

	public Time getRepeatTime() {
		return repeatTime;
	}

	public void setRepeatTime(Time repeatTime) {
		this.repeatTime = repeatTime;
	}

	public Timestamp getNotifyAt() {
		return notifyAt;
	}

	public void setNotifyAt(Timestamp notifyAt) {
		this.notifyAt = notifyAt;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
