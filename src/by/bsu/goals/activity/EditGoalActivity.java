package by.bsu.goals.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.controller.EditGoalController;
import by.bsu.goals.data.Goal;

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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_goal_layout);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.get("goal") != null)
				controller.setGoal((Goal) extras.get("goal"));
			if (extras.get("parentGoal") != null)
				controller.setParentGoal((Goal) extras.get("parentGoal"));
		}
		controller.setActivity(this);
		changeStartDate = (TextView) findViewById(R.id.textViewStartedAtValue);
		changeFinishDate = (TextView) findViewById(R.id.textViewFinishedAtValue);
		parentGoal = (TextView) findViewById(R.id.textViewParentTitleValue);
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextDescription = (EditText) findViewById(R.id.editTextDescription);
		changeFinishDate.setOnClickListener(controller);
		changeStartDate.setOnClickListener(controller);

		TextView text = new TextView(this);
		text.setText("CreateGoalActivity");
		addContentView(text, new ActionBar.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		controller.fillUIInfo();
	}

	protected void onResume(Bundle savedInstanceState) {
		super.onResume();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.get("goal") != null)
				controller.setGoal((Goal) extras.get("goal"));
			if (extras.get("parentGoal") != null)
				controller.setParentGoal((Goal) extras.get("parentGoal"));
		}
		controller.fillUIInfo();
	}
}
