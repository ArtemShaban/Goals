package by.bsu.goals.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.controller.GoalController;
import by.bsu.goals.controller.GoalView;
import by.bsu.goals.data.Goal;
import by.bsu.goals.log.Logger;
import by.bsu.goals.logic.GoalLogic;
import by.bsu.goals.sidemenu.SideMenuAdapter;
import by.bsu.goals.util.DateUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 14.
 */
public class GoalInfoActivity extends ActionBarActivity implements GoalView
{
    private final Logger logger = new Logger(this);
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView startAtTextView;
    private TextView finishAtTextView;
    private ListView stepsListView;
    private long currentGoalId;
    private Callback callback;

    private SlidingMenu sideMenu;
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

        sideMenu = new SlidingMenu(this);
        sideMenu.setMode(SlidingMenu.LEFT);
        sideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int behindOffset = (int) (metrics.widthPixels * 0.5);
        sideMenu.setBehindOffset(behindOffset);
        sideMenu.setBehindScrollScale(1f);
        sideMenu.setFadeDegree(0.35f);
        sideMenu.setShadowDrawable(R.drawable.shadow);
        sideMenu.setShadowWidth(behindOffset);

        sideMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        sideMenuListView = new ListView(this);
        List<Goal> activeGoalsWithChildren = new GoalLogic().getActiveGoalsWithChildren();
        SideMenuAdapter sideMenuAdapter = new SideMenuAdapter(this, activeGoalsWithChildren, metrics.widthPixels - behindOffset);
        sideMenuAdapter.setGoalCLickedListener(goalCLickedListener);
        sideMenuListView.setAdapter(sideMenuAdapter);
        sideMenu.setMenu(sideMenuListView);

        currentGoalId = getIntent().getExtras().getLong(LauncherActivity.GOAL_ID_EXTRA);
        callback = new GoalController(this);
        callback.setGoal(currentGoalId);

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
        switch (item.getItemId())
        {
            case android.R.id.home:
                if (sideMenu.isMenuShowing())
                {
                    sideMenu.showContent();
                }
                else
                {
                    sideMenu.showMenu();
                }
                return true;
            case R.id.action_bar_menu_add:
                callback.onAddClicked();
                return true;
            case R.id.action_bar_menu_edit:
                callback.onEditClicked();
                return true;
//            case R.id.action_bar_menu_fake:
//                LauncherActivity.fillDatabase("Fake title");
//                onResume();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        startAtTextView.setText(DateUtil.dateToDMmYyy(startAt.getTime()));
        finishAtTextView.setText(DateUtil.dateToDMmYyy(finishAt.getTime()));
    }

    public void showSteps(List<Goal> steps)
    {
        if (steps != null)
        {
            stepsListView.setAdapter(new StepsListAdapter(steps));
            stepsListView.setVisibility(View.VISIBLE);
        }
        else
        {
            stepsListView.setVisibility(View.GONE);
        }
    }

    @Override
    public void startEditActivity(Long goalId)
    {
        Intent intent = new Intent(this, EditGoalActivity.class);
        intent.putExtra("goalId", goalId);
        startActivity(intent);
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

    private class StepsListAdapter extends BaseAdapter
    {

        private List<Goal> goals;

        private StepsListAdapter(List<Goal> goals)
        {
            this.goals = goals;
        }

        @Override
        public int getCount()
        {
            return goals.size();
        }

        @Override
        public Object getItem(int position)
        {
            return goals.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return goals.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            final ViewHolder holder;

            if (convertView == null)
            {
                view = getLayoutInflater().inflate(R.layout.side_menu_goal_item, null);

                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.side_menu_item_title);
                holder.finishAt = (TextView) view.findViewById(R.id.side_menu_item_finish_at);
                holder.startAt = (TextView) view.findViewById(R.id.side_menu_item_started_at);
                holder.description = (TextView) view.findViewById(R.id.side_menu_item_description);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            Goal goal = goals.get(position);
            holder.title.setText(goal.getTitle());
            holder.finishAt.setText(DateUtil.dateToDMmYyy(goal.getFinishedAt().getTime()));
            holder.startAt.setText(DateUtil.dateToDMmYyy(goal.getStartedAt().getTime()));
            holder.description.setText(goal.getDescription());
            holder.position = position;
            return view;
        }

        private class ViewHolder
        {
            TextView title;
            TextView startAt;
            TextView finishAt;
            TextView description;
            int position;
        }
    }
}
