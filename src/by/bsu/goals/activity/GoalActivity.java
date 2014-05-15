package by.bsu.goals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import by.bsu.goals.R;
import by.bsu.goals.controller.GoalView;
import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;
import by.bsu.goals.logic.GoalLogic;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 14.
 */
public class GoalActivity extends Activity implements GoalView
{
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ListView stepsListView;

    private GoalLogic goalLogic;
    private long goalId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_info);

        titleTextView = (TextView) findViewById(R.id.goal_info_title);
        descriptionTextView = (TextView) findViewById(R.id.goal_info_description);
        stepsListView = (ListView) findViewById(R.id.goal_info_steps);

        goalLogic = new GoalLogic(new GoalDAOSqlLite(DBHelper.instance()));
        goalId = getIntent().getExtras().getLong(LauncherActivity.GOAL_ID_EXTRA);

        ArrayAdapter<Goal> adapter = new ArrayAdapter<Goal>(this, android.R.layout.simple_list_item_1, goalLogic.getSortedChildren(goalId));
        stepsListView.setAdapter(adapter);
        stepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(GoalActivity.this, parent.getAdapter().getItem(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void updateInfo(String title, String description, Timestamp startAt, Timestamp finishAt)
    {

    }

    @Override
    public void showSteps(@Nullable List<Goal> steps)
    {

    }
}
