package by.bsu.goals.logic;

import by.bsu.goals.dao.DAO;
import by.bsu.goals.dao.GoalDAO;
import by.bsu.goals.data.Goal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 14.
 */
public class GoalLogic
{
    private final GoalDAO goalDAO;

    public GoalLogic(GoalDAO goalDAO)
    {
        this.goalDAO = goalDAO;
    }

    public List<Goal> getActiveGoals()
    {
        ArrayList<Goal> result = new ArrayList<Goal>();

        List<Goal> goals = goalDAO.loadAllGoals(DAO.FAKE_USER_ID);
        for (Goal goal : goals)
        {
            if (isGoalActive(goal))
            {
                result.add(goal);
            }
        }
        return result.size() > 0 ? result : null;
    }

    private boolean isGoalActive(Goal goal)
    {
        return System.currentTimeMillis() < goal.getFinishedAt().getTime();
    }
}
