package by.bsu.goals.controller;

import by.bsu.goals.dao.GoalDAO;
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

    public GoalController(GoalView view, GoalLogic logic, GoalDAO goalDAO)
    {
        this.view = view;
        this.logic = logic;
        this.goalDAO = goalDAO;
    }

    @Override
    public void setGoal(Long goalId)
    {
        Goal goal = goalDAO.loadGoal(goalId);
//        if(goal.getParentId())
    }

    @Override
    public void backClicked()
    {

    }
}
