package by.bsu.goals.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import by.bsu.goals.R;
import by.bsu.goals.dao.DAO;
import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.GoalDAO;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;
import by.bsu.goals.logic.GoalLogic;

import java.sql.Timestamp;
import java.util.List;

public class LauncherActivity extends Activity
{
    public static String GOAL_ID_EXTRA = "goalId";

    private DBHelper dbHelper;
    private GoalDAO goalDAO;

    private GoalLogic goalLogic;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        deleteDatabase("goals");
        DBHelper.initDBHelper(this);

        dbHelper = DBHelper.instance();
        goalDAO = new GoalDAOSqlLite(dbHelper);
        goalLogic = new GoalLogic(goalDAO);

        fillDatabase();

        List<Goal> activeGoals = goalLogic.getActiveGoals();
        Intent intent;
        if (activeGoals != null && activeGoals.size() > 0)
        {
            intent = new Intent(this, GoalActivity.class);
            intent.putExtra("goalId", activeGoals.get(0).getId());
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
        dbHelper.close();
    }

    private void fillDatabase()
    {
        Goal goal = new Goal();
        goal.setTitle("Title");
        goal.setStartedAt(new Timestamp(1391990400000L));
        goal.setFinishedAt(new Timestamp(1418169600000L));
        goal.setUserId(DAO.FAKE_USER_ID);
        long goalId = goalDAO.saveGoal(goal);
        goal.setParentId(goalId);
        goalDAO.saveGoal(goal);
        goalDAO.saveGoal(goal);
        goalDAO.saveGoal(goal);
        goalDAO.saveGoal(goal);
        goalDAO.saveGoal(goal);

        goalDAO.loadAllGoals(DAO.FAKE_USER_ID);
    }
}
