package by.bsu.goals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.controller.EditGoalController;
import by.bsu.goals.data.Goal;

/**
 * Created by Artem Shaban Since 2014 Май 14.
 */
public class EditGoalActivity extends Activity {
	public EditGoalController controller = new EditGoalController();
	public TextView parentGoal;
	public TextView changeStartDate;
	public TextView changeFinishDate;
	public EditText editTextTitle;
	public EditText editTextDescription;
	public Button createOrEditButton;
	public Button createSubTaskButton;
	public ListView subGoals;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_goal_layout);

		controller.setActivity(this);
		changeStartDate = (EditText) findViewById(R.id.textViewStartedAtValue);
		changeFinishDate = (EditText) findViewById(R.id.textViewFinishedAtValue);
		parentGoal = (TextView) findViewById(R.id.textViewParentTitleValue);
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextDescription = (EditText) findViewById(R.id.editTextDescription);
		createOrEditButton = (Button) findViewById(R.id.editOrCreateGoalButton);
		createSubTaskButton = (Button) findViewById(R.id.createSubTaskButton);
		subGoals = (ListView) findViewById(R.id.edit_goal_steps);
		subGoals.setOnItemLongClickListener(controller);
		createOrEditButton.setOnClickListener(controller);
		changeFinishDate.setOnFocusChangeListener(controller);
		changeStartDate.setOnFocusChangeListener(controller);
		createSubTaskButton.setOnClickListener(controller);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.get("goalId") != null) {
				// retrieve goal from db by goalId idedentifier
				controller.setGoalById((Long) extras.get("goalId"));
				controller.setParentGoalById(controller.getGoal().getParentId());
				subGoals.setAdapter(new ArrayAdapter<Goal>(this, android.R.layout.simple_list_item_1, controller.getGoalChilds(controller.getGoal().getId())));
				createOrEditButton.setText(R.string.Edit_goal_button);
			} else {
				controller.createAndFillNewGoal((Long) extras
						.get("parentGoalId"));
				controller.setParentGoalById(controller.getGoal().getParentId());
				subGoals.setAdapter(new ArrayAdapter<Goal>(this, android.R.layout.simple_list_item_1, new Goal[0]));
			}
		}
		controller.fillUIInfo();
	}

	protected void onResume(Bundle savedInstanceState) {
		super.onResume();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.get("goalId") != null) {
				controller.setGoalById((Long) extras.get("goalId"));
				createOrEditButton.setText(R.string.Edit_goal_button);
			}
			if (extras.get("parentGoalId") != null)
				controller.setParentGoalById((Long) extras.get("parentGoalId"));
		}
		controller.fillUIInfo();
	}
}
