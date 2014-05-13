package by.bsu.goals.dao.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.bsu.goals.dao.NotificationDAO;
import by.bsu.goals.data.Notification;
import by.bsu.goals.log.Logger;

/**
 * @author Sergey Datskevich May 12, 2014 5:45:12 PM
 * 
 * 
 */

public class NotificationDAOSqlLite implements NotificationDAO
{

	private static final String TABLE_NAME = "notification";
	private static final String NOTIFICATION_ID = "id";
	private static final String GOAL_ID = "goal_id";
	private static final String REPEAT_NUMBER = "repeat_number";
	private static final String REPEATE_TIME = "repeat_time";
	private static final String NOTIFY_AT = "notify_at";
	private static final String SOUND_URI = "sound_uri";

	private final SQLiteOpenHelper dbHelper;
	private final Logger logger = new Logger(this);
	private SQLiteDatabase writableDatabase;
	private SQLiteDatabase readableDatabase;

	public NotificationDAOSqlLite(SQLiteOpenHelper sqLiteOpenHelper) {
		this.dbHelper = sqLiteOpenHelper;
		writableDatabase = dbHelper.getWritableDatabase();
		readableDatabase = dbHelper.getReadableDatabase();

	}

	@Override
	public void releaseResources() {
		dbHelper.close();
	}

	@Override
	public Notification load(long notifId) {
		String selection = NOTIFICATION_ID + " = ?";
		String[] selectionArgs = new String[] { String.valueOf(notifId) };
		Cursor cursor = readableDatabase.query(TABLE_NAME, null, selection,
				selectionArgs, null, null, null);
		Notification notification = null;
		if (cursor.moveToFirst()) {
			notification = getNotification(cursor);
			logger.i("Loaded " + notification.getNotifiacationId());
		}
		cursor.close();
		return notification;
	}

	@Override
	public boolean update(Notification notification) {
		writableDatabase.beginTransaction();
		try {
			int countOfupdatedItems = writableDatabase.update(TABLE_NAME,
					notificationToValues(notification), NOTIFICATION_ID
							+ " = ?", new String[] { String
							.valueOf(notification.getNotifiacationId()) });
			if (countOfupdatedItems == 1) {
				writableDatabase.setTransactionSuccessful();
				logger.i("Successful update of notification");
			}
			return true;
		} catch (Exception e) {
			logger.e(e.getMessage(), e);
			return false;
		} finally {
			writableDatabase.endTransaction();
		}
	}

	@Override
	public long create(Notification notification) {
		writableDatabase.beginTransaction();
		try {
			long newNotifId = writableDatabase.insert(TABLE_NAME, null,
					notificationToValues(notification));
			writableDatabase.setTransactionSuccessful();
			return newNotifId;
		} catch (Exception e) {
			return -1;
		} finally {
			writableDatabase.endTransaction();
		}
	}

	@Override
	public List<Notification> getGoalsNotifications(Long goalId) {
		List<Notification> notifications = new ArrayList<Notification>();
		String selection = GOAL_ID + " = ?";
		String[] selectionArgs = new String[] { String.valueOf(goalId) };
		String orderBy = NOTIFY_AT + "asc";
		Cursor cursor = writableDatabase.query(TABLE_NAME, null, selection,
				selectionArgs, null, null, orderBy);

		if (cursor.moveToFirst()) {
			do {
				notifications.add(getNotification(cursor));
			} while (cursor.isLast());
			logger.i("Loaded " + notifications.size() + "notifications :)");
		} else {
			logger.i("There are no notifications for this goal:" + goalId);
		}
		cursor.close();
		return notifications;
	}
	
	@Override
	public Notification getGoalsLastNotification(long goalId) {
		String selection = GOAL_ID + " = ?";
		String[] selectionArgs = new String[] { String.valueOf(goalId) };
		String orderBy = NOTIFY_AT + " asc ";
		Cursor cursor = readableDatabase.query(TABLE_NAME, null, selection,
				selectionArgs, null, null, orderBy);
		Notification notification = null;
		if (cursor.moveToFirst()) {
			notification = getNotification(cursor);
			logger.i("Loaded " + notification.getNotifiacationId());
		}
		cursor.close();
		return notification;
	}
	

	@Override
	public boolean deleteNotification(long notifId) {
		writableDatabase.beginTransaction();
		try {
			String whereClause = NOTIFICATION_ID + " = ?";
			String[] whereArgs = new String[]{ String.valueOf(notifId) };
			writableDatabase.delete(TABLE_NAME, whereClause, whereArgs);
			writableDatabase.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			writableDatabase.endTransaction();
		}
	}

	@Override
	public boolean deletAllNotificationsOfGoal(long goalId) {
		writableDatabase.beginTransaction();
		try {
			String whereClause = GOAL_ID + " = ?";
			String[] whereArgs = new String[]{ String.valueOf(goalId) };
			writableDatabase.delete(TABLE_NAME, whereClause, whereArgs);
			writableDatabase.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			writableDatabase.endTransaction();
		}
	}

	private ContentValues notificationToValues(Notification notification) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(GOAL_ID, notification.getGoalId());
		contentValues.put(REPEAT_NUMBER, notification.getRepeatNumber());
		contentValues
				.put(REPEATE_TIME, notification.getRepeatTime().toString());
		contentValues.put(NOTIFY_AT, notification.getNotifyAt().toString());
		contentValues.put(SOUND_URI, notification.getUri());
		return contentValues;
	}

	private Notification getNotification(Cursor cursor) {
		Notification notification = new Notification();
		notification.setNotificationId(cursor.getLong(cursor
				.getColumnIndex(NOTIFICATION_ID)));
		notification.setGoalId(Long.valueOf(cursor.getString(cursor
				.getColumnIndex(GOAL_ID))));
		notification.setRepeatNumber(Integer.valueOf(cursor.getString(cursor
				.getColumnIndex(REPEAT_NUMBER))));
		notification.setRepeatTime(new Time(cursor.getLong(cursor
				.getColumnIndex(REPEATE_TIME))));
		notification.setNotifyAt(new Timestamp(cursor.getLong(cursor
				.getColumnIndex(NOTIFY_AT))));
		notification.setUri(cursor.getString(cursor.getColumnIndex(SOUND_URI)));
		return notification;
	}

}
