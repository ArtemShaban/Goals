package by.bsu.goals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.controller.GoalController;
import by.bsu.goals.controller.GoalView;
import by.bsu.goals.data.Goal;
import by.bsu.goals.log.Logger;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 14.
 */
public class GoalActivity extends Activity implements GoalView
{
    private final Logger logger = new Logger(this);
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView startAtTextView;
    private TextView finishAtTextView;
    private ListView stepsListView;
    private long goalId;
    private Callback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_info);
        goalId = getIntent().getExtras().getLong(LauncherActivity.GOAL_ID_EXTRA);
        callback = new GoalController(this);

        titleTextView = (TextView) findViewById(R.id.goal_info_title);
        descriptionTextView = (TextView) findViewById(R.id.goal_info_description);
        startAtTextView = (TextView) findViewById(R.id.goal_info_started_at);
        finishAtTextView = (TextView) findViewById(R.id.goal_info_finish_at);
        stepsListView = (ListView) findViewById(R.id.goal_info_steps);
        stepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Goal step = (Goal) parent.getAdapter().getItem(position);
                logger.i("Step clicked: " + step.toString());
                callback.setGoal(step.getId());
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        callback.setGoal(goalId);
    }

    @Override
    public void updateInfo(String title, String description, Timestamp startAt, Timestamp finishAt)
    {
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        startAtTextView.setText(startAt.toString());
        finishAtTextView.setText(finishAt.toString());
    }

    @Override
    public void showSteps(@Nullable List<Goal> steps)
    {
        if (steps != null)
        {
            stepsListView.setAdapter(new ArrayAdapter<Goal>(this, android.R.layout.simple_list_item_1, steps));
            stepsListView.setVisibility(View.VISIBLE);
        }
        else
        {
            stepsListView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed()
    {
        callback.backClicked();
    }
}
