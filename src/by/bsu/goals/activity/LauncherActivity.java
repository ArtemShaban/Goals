package by.bsu.goals.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import by.bsu.goals.R;
import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.GoalDAO;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;
import by.bsu.goals.logic.GoalLogic;

import java.util.List;

public class LauncherActivity extends Activity
{
    private DBHelper dbHelper;
    private GoalDAO goalDAO;

    private GoalLogic goalLogic;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        databaseTest();

        dbHelper = new DBHelper(this);
        goalDAO = new GoalDAOSqlLite(dbHelper);
        goalLogic = new GoalLogic(goalDAO);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        List<Goal> activeGoals = goalLogic.getActiveGoals();
        Intent intent;
        if (activeGoals != null && activeGoals.size() > 0)
        {
            intent = new Intent(this, GoalActivity.class);
        }
        else
        {
            intent = new Intent(this, CreateGoalActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        goalDAO.releaseResources();
    }

    private void databaseTest()
    {
        deleteDatabase("goals");

        DBHelper dbHelper = new DBHelper(this);

        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "Artem");
        contentValues.put("started_at", System.currentTimeMillis());
        contentValues.put("finish_at", 112142);
        contentValues.put("user_id", "13");
        writableDatabase.insert("goal", null, contentValues);
        Cursor c = writableDatabase.query("goal", null, null, null, null, null, null);

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
