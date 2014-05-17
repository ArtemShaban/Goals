package by.bsu.goals.logic;

import by.bsu.goals.dao.DAO;
import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.GoalDAO;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 14.
 */
public class GoalLogic
{
    private final GoalDAO goalDAO;

    public GoalLogic()
    {
        this.goalDAO = new GoalDAOSqlLite(DBHelper.instance());
    }

    public List<Goal> getSortedChildren(long goalId)
    {
        List<Goal> children = goalDAO.loadChildren(goalId);
        if (children != null)
        {
            Collections.sort(children);
            return children;
        }
        return null;
    }

    public List<Goal> getActiveGoals()
    {
        ArrayList<Goal> result = new ArrayList<Goal>();

        List<Goal> goals = goalDAO.loadAllGoals(DAO.FAKE_USER_ID);
        if (goals != null)
        {
            for (Goal goal : goals)
            {
                if (isGoalActive(goal) && goal.getParentId() == null)
                {
                    result.add(goal);
                }
            }
            return result;
        }
        return null;
    }

    private boolean isGoalActive(Goal goal)
    {
        return System.currentTimeMillis() < goal.getFinishedAt().getTime();
    }
}
