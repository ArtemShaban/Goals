package by.bsu.goals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import by.bsu.goals.R;
import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;
import by.bsu.goals.logic.GoalLogic;

/**
 * Created by Artem Shaban
 * Since 2014 Май 14.
 */
public class CreateGoalActivity extends Activity
{
    private TextView title;
    private TextView description;
    private ListView steps;

    private GoalLogic goalLogic;
    private long goalId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_info);

        title = (TextView) findViewById(R.id.goal_info_title);
        description = (TextView) findViewById(R.id.goal_info_description);
        steps = (ListView) findViewById(R.id.goal_info_steps);

        goalLogic = new GoalLogic(new GoalDAOSqlLite(DBHelper.instance()));
        goalId = getIntent().getExtras().getLong(LauncherActivity.GOAL_ID_EXTRA);

        ArrayAdapter<Goal> adapter = new ArrayAdapter<Goal>(this, android.R.layout.simple_expandable_list_item_1, goalLogic.getSortedChildren(goalId));
        steps.setAdapter(adapter);
        steps.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(CreateGoalActivity.this, ((Goal) parent.getAdapter().getItem(position)).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
