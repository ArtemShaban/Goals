package by.bsu.goals.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import by.bsu.goals.R;
import by.bsu.goals.dao.DBHelper;

public class MainActivity extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        deleteDatabase("goals");

        DBHelper dbHelper = new DBHelper(this);

        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Artem");
        writableDatabase.insert("user", null, contentValues);
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
}
