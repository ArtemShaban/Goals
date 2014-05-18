package by.bsu.goals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.controller.EditGoalController;

/**
 * Created by Artem Shaban Since 2014 Май 14.
 */
public class EditGoalActivity extends Activity {
	public static EditGoalController controller = new EditGoalController();
	public TextView parentGoal;
	public TextView changeStartDate;
	public TextView changeFinishDate;
	public EditText editTextTitle;
	public EditText editTextDescription;
	public Button createOrEditButton;
	public Button createSubTaskButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_goal_layout);

		controller.setActivity(this);
		changeStartDate = (TextView) findViewById(R.id.textViewStartedAtValue);
		changeFinishDate = (TextView) findViewById(R.id.textViewFinishedAtValue);
		parentGoal = (TextView) findViewById(R.id.textViewParentTitleValue);
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextDescription = (EditText) findViewById(R.id.editTextDescription);
		createOrEditButton = (Button) findViewById(R.id.editOrCreateGoalButton);
		createSubTaskButton = (Button) findViewById(R.id.createSubTaskButton);

		createOrEditButton.setOnClickListener(controller);
		changeFinishDate.setOnClickListener(controller);
		changeStartDate.setOnClickListener(controller);
		createSubTaskButton.setOnClickListener(controller);

		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.get("goalId") != null) {
			// retrieve goal from db by goalId idedentifier
			controller.setGoal((Long) extras.get("goalId"));
			controller.setParentGoal(controller.getGoal().getParentId());
			createOrEditButton.setText(R.string.Edit_goal_button);
		} else {
//			controller.createAndFillNewGoal((Long) extras.get("parentGoalId"));
//			controller.setParentGoal(controller.getGoal().getParentId());
//			createOrEditButton.setText(R.string.Edit_goal_button);
        }

		controller.fillUIInfo();
	}

	protected void onResume(Bundle savedInstanceState) {
		super.onResume();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.get("goalId") != null) {
				controller.setGoal((Long) extras.get("goalId"));
				createOrEditButton.setText(R.string.Edit_goal_button);
			}
			if (extras.get("parentGoalId") != null)
				controller.setParentGoal((Long) extras.get("parentGoalId"));
		}
		controller.fillUIInfo();
	}
}
