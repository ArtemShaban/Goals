package by.bsu.goals.controller;

import by.bsu.goals.activity.LauncherActivity;
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
        logic = new GoalLogic();
        goalsStack = new Stack<Goal>();
    }

    @Override
    public void setGoal(Long goalId)
    {
        Goal goal = goalDAO.loadGoalWithChildren(goalId);
        goalsStack.add(goal);
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
            LauncherActivity.setState(LauncherActivity.State.EXIT);
            view.finishView();
        }
    }

    @Override
    public void onViewAttach()
    {
        updateGoalView(goalsStack.peek());
    }

    @Override
    public void onStepChosen(long stepId)
    {
        addAndUpdateGoal(stepId);
    }

    @Override
    public void goalUpdated(long goalId)
    {
        while (true && goalsStack.size() > 0)
        {
            Goal currentGoal = goalsStack.pop();
            if (currentGoal.getId() == goalId)
            {
                addAndUpdateGoal(goalId);
                break;
            }
        }
        if (goalsStack.size() == 0)
        {
            addAndUpdateGoal(goalId);
        }
    }

    private void addAndUpdateGoal(long goalId)
    {
        setGoal(goalId);
        updateGoalView(goalsStack.peek());
    }

    private void updateGoalView(Goal goal)
    {
        view.updateInfo(goal.getTitle(), goal.getDescription(), goal.getStartedAt(), goal.getFinishedAt());
        view.showSteps(goal.getSteps());
    }
}
