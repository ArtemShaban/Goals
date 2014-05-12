package by.bsu.goals.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.bsu.goals.R;
import by.bsu.goals.log.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 08.
 */
public class DBHelper extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "goals";
    private static final Logger logger = new Logger("DBHelper");
    private Context context;

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            Resources res = context.getResources();
            InputStream inputStream = res.openRawResource(R.raw.qoals_sqlite);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String createScript = new String(bytes);
            logger.i(createScript);

            db.execSQL(createScript);
        } catch (IOException e)
        {
            logger.e("", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
