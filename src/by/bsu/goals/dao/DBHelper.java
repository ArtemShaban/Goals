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
    private static DBHelper instance;
    private Context context;

    private DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static DBHelper instance()
    {
        if (instance != null)
        {
            return instance;
        }
        else
        {
            throw new RuntimeException("Can't get instance before initialisation!");
        }
    }

    public static void initDBHelper(Context context)
    {
        if (instance == null)
        {
            instance = new DBHelper(context);
        }
        else
        {
            logger.i("Try init helper which already init.");
        }

    }

    public static void release()
    {
        instance.close();
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
            db.execSQL("CREATE TABLE \"user\" (\n" +
                    "  \"id\"   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "  \"name\" VARCHAR(45)                       NOT NULL\n" +
                    ");");
            db.execSQL("CREATE TABLE \"goal\" (\n" +
                    "  \"id\"          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "  \"title\"       VARCHAR(45),\n" +
                    "  \"description\" TEXT,\n" +
                    "  \"started_at\"  INTEGER                           NOT NULL,\n" +
                    "  \"finish_at\"   INTEGER                           NOT NULL,\n" +
                    "  \"category_id\" INTEGER,\n" +
                    "  \"user_id\"     INTEGER                           NOT NULL,\n" +
                    "  \"parent_id\"   INTEGER,\n" +
                    "  \"list_index\"  INTEGER,\n" +
                    "  CONSTRAINT \"fk_goal_user\"\n" +
                    "  FOREIGN KEY (\"user_id\")\n" +
                    "  REFERENCES \"user\" (\"id\"),\n" +
                    "  CONSTRAINT \"fk_goal_goal1\"\n" +
                    "  FOREIGN KEY (\"id\", \"parent_id\")\n" +
                    "  REFERENCES \"goal\" (\"parent_id\", \"parent_id\")\n" +
                    ");");
            db.execSQL("CREATE INDEX \"goal.fk_goal_user\" ON \"goal\" (\"user_id\");\n");
            db.execSQL("CREATE INDEX \"goal.fk_goal_goal1\" ON \"goal\" (\"id\", \"parent_id\");\n");
            db.execSQL("CREATE TABLE \"notification\" (\n" +
                    "  \"id\"            INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "  \"goal_id\"       INTEGER                           NOT NULL,\n" +
                    "  \"repeat_number\" INTEGER,\n" +
                    "  \"repeat_time\"   INTEGER,\n" +
                    "  \"notify_at\"     INTEGER                           NOT NULL,\n" +
                    "  \"sound_uri\"     VARCHAR(100),\n" +
                    "  CONSTRAINT \"fk_notification_goal1\"\n" +
                    "  FOREIGN KEY (\"goal_id\")\n" +
                    "  REFERENCES \"goal\" (\"id\")\n" +
                    "  ON DELETE CASCADE\n" +
                    ");\n");
            db.execSQL("CREATE INDEX \"notification.fk_notification_goal1\" ON \"notification\" (\"goal_id\");");
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
