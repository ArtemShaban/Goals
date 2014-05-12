package by.bsu.goals.dao;

import by.bsu.goals.data.Goal;

import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 11.
 */
public interface GoalDAO extends DAO
{
    public long saveGoal(Goal goal);
    public Goal loadGoal(long goalId);
    public List<Goal> loadChildren(long parentId);
    public Goal loadGoalWithChildren(long goalId);
    public List<Goal> loadAllGoals(Long userId);
    public void updateGoal(Goal goal);
}
