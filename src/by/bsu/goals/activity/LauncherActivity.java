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
}
