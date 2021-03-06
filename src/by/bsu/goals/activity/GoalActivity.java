package by.bsu.goals.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
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
import by.bsu.goals.logic.GoalLogic;
import by.bsu.goals.sidemenu.SideMenuAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 14.
 */
public class GoalActivity extends ActionBarActivity implements GoalView
{
    private final Logger logger = new Logger(this);
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView startAtTextView;
    private TextView finishAtTextView;
    private ListView stepsListView;
    private long goalId;
    private Callback callback;

    private ListView sideMenuListView;
    private SideMenuAdapter.OnGoalCLickedListener goalCLickedListener = new SideMenuAdapter.OnGoalCLickedListener()
    {
        @Override
        public void onGoalClicked(Goal goal)
        {
            callback.goalUpdated(goal.getId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int behindOffset = (int) (metrics.widthPixels * 0.4);
        menu.setBehindOffset(behindOffset);
        menu.setBehindScrollScale(1f);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        sideMenuListView = new ListView(this);
        List<Goal> activeGoalsWithChildren = new GoalLogic().getActiveGoalsWithChildren();
        SideMenuAdapter sideMenuAdapter = new SideMenuAdapter(this, activeGoalsWithChildren, metrics.widthPixels - behindOffset);
        sideMenuAdapter.setGoalCLickedListener(goalCLickedListener);
        sideMenuListView.setAdapter(sideMenuAdapter);
        menu.setMenu(sideMenuListView);

        goalId = getIntent().getExtras().getLong(LauncherActivity.GOAL_ID_EXTRA);
        callback = new GoalController(this);
        callback.setGoal(goalId);

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
                callback.onStepChosen(step.getId());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        callback.onViewAttach();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void updateInfo(String title, String description, Timestamp startAt, Timestamp finishAt)
    {
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        startAtTextView.setText(startAt.toString());
        finishAtTextView.setText(finishAt.toString());
    }

    public void showSteps(List<Goal> steps)
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

    @Override
    public void finishView()
    {
        finish();
    }
}
