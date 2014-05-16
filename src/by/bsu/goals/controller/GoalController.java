package by.bsu.goals.controller;

import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.GoalDAO;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;
import by.bsu.goals.logic.GoalLogic;

import java.util.Stack;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 15.
 */
public class GoalController implements GoalView.Callback
{
    private Stack<Goal> goalsStack;
    private GoalView view;
    private GoalLogic logic;
    private GoalDAO goalDAO;

    public GoalController(GoalView view)
    {
        this.view = view;
        goalDAO = new GoalDAOSqlLite(DBHelper.instance());
        logic = new GoalLogic(goalDAO);
        goalsStack = new Stack<Goal>();
    }

    @Override
    public void setGoal(Long goalId)
    {
        Goal goal = goalDAO.loadGoalWithChildren(goalId);
        goalsStack.add(goal);
        updateGoalView(goal);
    }

    @Override
    public void backClicked()
    {
        goalsStack.pop();
        if (goalsStack.size() > 0)
        {
            Goal currentGoal = goalsStack.peek();
            updateGoalView(currentGoal);
        }
        else
        {
            view.finishView();
        }
    }

    private void updateGoalView(Goal goal)
    {
        view.updateInfo(goal.getTitle(), goal.getDescription(), goal.getStartedAt(), goal.getFinishedAt());
        view.showSteps(goal.getSteps());
    }
}
