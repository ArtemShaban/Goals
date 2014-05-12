package by.bsu.goals.dao.impl;

import java.util.List;

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
	
    public NotificationDAOSqlLite(SQLiteOpenHelper sqLiteOpenHelper)
    {
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
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean update(Notification notification) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public long create(Notification notification) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public List getGoalsNotifications(Long goalId) {
		// TODO Auto-generated method stub
		return null;
	}

}
