package by.bsu.goals.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private static State state = State.NONE;
    private GoalLogic goalLogic;

    public static void setState(State newState)
    {
        state = newState;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        deleteDatabase("goals");                        //TODO delete

        DBHelper.initDBHelper(this);
        goalLogic = new GoalLogic();

        fillDatabase("qqq");
        fillDatabase("qqq1");
        fillDatabase("qqq2");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent intent = null;

        switch (state)
        {
            case EDIT_GOAL:
                intent = new Intent(this, EditGoalActivity.class);
                break;
            case GOAL_INFO:
                intent = new Intent(this, GoalInfoActivity.class);
                break;
            case EXIT:
                finish();
                break;
            case NONE:
                List<Goal> activeGoals = goalLogic.getActiveGoals();
                if (activeGoals != null && activeGoals.size() > 0)
                {
                    intent = new Intent(this, GoalInfoActivity.class);
                    intent.putExtra("goalId", activeGoals.get(0).getId());
                }
                else
                {
                    intent = new Intent(this, EditGoalActivity.class);
                }
        }
        if (intent != null)
        {
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        DBHelper.release();
        state = State.NONE;
    }

    private void fillDatabase(String title)
    {
        GoalDAO goalDAO = new GoalDAOSqlLite(DBHelper.instance());
        Goal goal = new Goal();
        goal.setTitle(title);
        goal.setStartedAt(new Timestamp(1391990400000L));
        goal.setFinishedAt(new Timestamp(1418169600000L));
        goal.setUserId(DAO.FAKE_USER_ID);
        goal.setDescription("Description Description Description Descriptionv Description))) a big big description");
        long goalId = goalDAO.saveGoal(goal);
        goal.setParentId(goalId);
        goalDAO.saveGoal(goal);
        for (int i = 0; i < 5; i++)
        {
            goal.setTitle("Child of goal(id =" + goalId + ") " + i);
            goalDAO.saveGoal(goal);
        }
        long childId = goalDAO.saveGoal(goal);
        goal.setParentId(childId);
        for (int i = 0; i < 5; i++)
        {
            goal.setTitle("Child of child(id =" + childId + ") " + i);
            goalDAO.saveGoal(goal);
            if (i == 2)
            {
                long id = goalDAO.saveGoal(goal);
                goal.setParentId(id);
                for (int j = 0; j < 2; j++)
                {
                    goal.setTitle("Child of child(id =" + id + ") " + i);
                    goalDAO.saveGoal(goal);
                }
            }
        }

        long childChildId = goalDAO.saveGoal(goal);
        goal.setParentId(childChildId);
        for (int i = 0; i < 5; i++)
        {
            goal.setTitle("Child of child of child(id =" + childChildId + ") " + i);
            goalDAO.saveGoal(goal);
        }
    }

    public static enum State
    {
        GOAL_INFO, EDIT_GOAL, EXIT, NONE
    }
}
