package by.bsu.goals.controller;

import by.bsu.goals.data.Goal;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 Май 15.
 */
public interface GoalView extends View
{
    public void updateInfo(String title, String description, Timestamp startAt, Timestamp finishAt);
    public void showSteps(List<Goal> steps);

    interface Callback
    {
        public void setGoal(Long goalId);
        public void backClicked();
        void onViewAttach();
        void onStepChosen(long stepId);
    }
}
