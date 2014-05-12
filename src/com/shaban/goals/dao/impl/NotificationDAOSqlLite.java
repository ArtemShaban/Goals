package com.shaban.goals.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shaban.goals.dao.NotificationDAO;
import com.shaban.goals.log.Logger;

/**
 * @author Sergey Datskevich May 12, 2014 5:45:12 PM
 *
 *
 */

public class NotificationDAOSqlLite implements NotificationDAO {

	
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

        Cursor c = writableDatabase.query("user", null, null, null, null, null, null);

        if (c != null)
        {
            if (c.moveToFirst())
            {
                String str;
                do
                {
                    str = "";
                    for (String cn : c.getColumnNames())
                    {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.i("Goal", str);

                } while (c.moveToNext());
            }
        }
        c.close();
        dbHelper.close();

    }

    
	@Override
	public void releaseResources() {
		
	}

}
