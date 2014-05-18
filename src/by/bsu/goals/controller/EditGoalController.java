package by.bsu.goals.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TimePicker;
import by.bsu.goals.R;
import by.bsu.goals.activity.EditGoalActivity;
import by.bsu.goals.activity.GoalInfoActivity;
import by.bsu.goals.dao.DAO;
import by.bsu.goals.dao.DBHelper;
import by.bsu.goals.dao.GoalDAO;
import by.bsu.goals.dao.impl.GoalDAOSqlLite;
import by.bsu.goals.data.Goal;
import by.bsu.goals.log.Logger;
import by.bsu.goals.util.DateUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Sergey Datskevich May 13, 2014 1:23:53 PM
 */

@SuppressLint("NewApi")
public class EditGoalController implements OnClickListener, OnDateSetListener,
		OnTimeSetListener {

	public static final int FINISHED_AT = 1;
	public static final int STARTED_AT = 2;
	public static final boolean HOURS_TIME_FROMAT_24 = true;
	private static Logger logger = new Logger(EditGoalController.class);
	private static GoalDAO goalDao = new GoalDAOSqlLite(DBHelper.instance());
	private long DEFAULT_DIFFERENCE_BETWEEN_STARTAND_FINISH = 1235;
	private Goal goal;
	private Goal parentGoal;
	private EditGoalActivity activity;
	// uses to define witch textEdit view is clicked
	private int currentTypeFlag = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textViewFinishedAtValue:
			changeGoalDateAndTime(FINISHED_AT);
			break;
		case R.id.textViewStartedAtValue:
			changeGoalDateAndTime(STARTED_AT);
			break;
		case R.id.editOrCreateGoalButton:
			CreateNewGoal();
			break;
		case R.id.createSubTaskButton:
			CreateNewSubGoal();
		}
	}

	private void CreateNewSubGoal() {
		readUIInfo();
		long goalId;
		if (goal.getId() == 0) {
			goalId = goalDao.saveGoal(goal);
		} else {
			goalId = goal.getId();
			goalDao.updateGoal(goal);
		}
		Intent intent = new Intent(activity, EditGoalActivity.class);
		intent.putExtra("parentGoalId", goalId);
		this.activity.startActivity(intent);
		activity.finish();
	}

	private void CreateNewGoal() {
		readUIInfo();
		long goalId;
		if (goal.getId() == 0) {
			goalId = goalDao.saveGoal(goal);
		} else {
			goalId = goal.getId();
			goalDao.updateGoal(goal);
		}
		Intent intent = new Intent(activity, GoalInfoActivity.class);
		intent.putExtra("goalId", goalId);
		this.activity.startActivity(intent);
		activity.finish();
	}

	private void changeGoalDateAndTime(int type) {
		DialogFragment dialogFragment;
		currentTypeFlag = type;
		dialogFragment = new CustomTimePicker(type);
		dialogFragment.show(activity.getFragmentManager(), type + "");
		dialogFragment = new CustomDatePicker(type);
		dialogFragment.show(activity.getFragmentManager(), type + "");
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Date date = null;
		switch (currentTypeFlag) {
		case FINISHED_AT:
			date = DateUtil.parseStringToDate(
					DateUtil.DATE_TEMPLATE_dd_MMM_yyyy,
					activity.changeFinishDate.getText().toString());
			date.setHours(hourOfDay);
			date.setMinutes(minute);
			activity.changeFinishDate.setText(DateUtil.formatDateToString(
					DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm, date));
			break;
		case STARTED_AT:
			date = DateUtil.parseStringToDate(
					DateUtil.DATE_TEMPLATE_dd_MMM_yyyy,
					activity.changeStartDate.getText().toString());
			date.setHours(hourOfDay);
			date.setMinutes(minute);
			activity.changeStartDate.setText(DateUtil.formatDateToString(
					DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm, date));
			break;
		default:
			return;
		}
		if (!DateUtil.isValidDates(activity.changeStartDate.getText()
				.toString(), activity.changeFinishDate.getText().toString())) {
			revertDateChanges();
		}

	}

	private void revertDateChanges() {
		if (currentTypeFlag == STARTED_AT) {
			activity.changeStartDate.setText(activity.changeFinishDate
					.getText());
		} else {
			activity.changeFinishDate.setText(activity.changeStartDate
					.getText());
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// Date started at 1900 and DatePicker return 19../
		Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
		if (((Integer) view.getTag()).equals(FINISHED_AT)) {
			activity.changeFinishDate.setText(DateUtil.formatDateToString(
					DateUtil.DATE_TEMPLATE_dd_MMM_yyyy, date));
			logger.i(activity.changeFinishDate.getText().toString());
		} else {
			activity.changeStartDate.setText(DateUtil.formatDateToString(
					DateUtil.DATE_TEMPLATE_dd_MMM_yyyy, date));
			logger.i(activity.changeStartDate.getText().toString());
		}
	}

	public Goal getGoal() {
		if (goal == null) {
			this.goal = new Goal();
			this.goal.setTitle("");
			this.goal.setDescription("");
			this.goal.setStartedAt(new Timestamp(System.currentTimeMillis()));
			this.goal.setFinishedAt(new Timestamp(System.currentTimeMillis()
					+ DEFAULT_DIFFERENCE_BETWEEN_STARTAND_FINISH));
			this.goal.setUserId(DAO.FAKE_USER_ID);
			if (parentGoal == null) {
				this.goal.setParentId(null);
			} else {
				this.goal.setParentId(parentGoal.getId());
			}
		}
		return goal;
	}

	public void setGoal(Long goalId) {
		this.goal = goalDao.loadGoal(goalId);
	}

	public void createAndFillNewGoal(Long parentGoalId) {
		Goal goal = new Goal();
		goal.setTitle("");
		goal.setDescription("");
		goal.setStartedAt(new Timestamp(System.currentTimeMillis()));
		goal.setFinishedAt(new Timestamp(System.currentTimeMillis()
				+ DEFAULT_DIFFERENCE_BETWEEN_STARTAND_FINISH));
		goal.setUserId(DAO.FAKE_USER_ID);
		goal.setParentId(parentGoalId);
		this.goal = goal;
	}

	public void fillUIInfo() {
		getGoal();
		activity.editTextTitle.setText(this.goal.getTitle());
		activity.editTextDescription.setText(this.goal.getDescription());
		if (this.goal.getParentId() != null) {
			activity.parentGoal.setText(goalDao.loadGoal(
					this.goal.getParentId()).getTitle());
		}

		activity.changeStartDate.setText(DateUtil.formatDateToString(
				DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
				this.goal.getStartedAt()));
		activity.changeFinishDate.setText(DateUtil.formatDateToString(
				DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
				this.goal.getFinishedAt()));

	}

	public void readUIInfo() {
		goal.setTitle(activity.editTextTitle.getText().toString());
		goal.setDescription(activity.editTextDescription.getText().toString());
		goal.setStartedAt(new Timestamp(DateUtil.parseStringToDate(
				DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
				activity.changeStartDate.getText().toString()).getTime()));
		goal.setFinishedAt(new Timestamp(DateUtil.parseStringToDate(
				DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
				activity.changeFinishDate.getText().toString()).getTime()));
	}

	public Goal getParentGoal() {
		return parentGoal;
	}

	public void setParentGoal(Long parentGoalId) {
		if (parentGoalId != null)
			this.parentGoal = goalDao.loadGoal(parentGoalId);
	}

	public void setActivity(EditGoalActivity activity) {
		this.activity = activity;
	}

	@SuppressLint("ValidFragment")
	private class CustomDatePicker extends DialogFragment {
		private int type;

		public CustomDatePicker(int type) {
			this.type = type;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Date date;

			if (type == FINISHED_AT) {
				date = DateUtil.parseStringToDate(
						DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
						activity.changeFinishDate.getText().toString());
			} else {
				date = DateUtil.parseStringToDate(
						DateUtil.DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
						activity.changeStartDate.getText().toString());
			}
			DatePickerDialog dialogw = new DatePickerDialog(activity,
					EditGoalController.this, date.getYear() + 1900,
					date.getMonth(), date.getDay());
			dialogw.getDatePicker().setTag(type);
			return dialogw;
		}
	}

	@SuppressLint("ValidFragment")
	private class CustomTimePicker extends DialogFragment {
		protected int type;

		public CustomTimePicker(int type) {
			this.type = type;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Date date;
			if (type == FINISHED_AT) {
				date = getGoal().getFinishedAt();
			} else {
				date = getGoal().getStartedAt();
			}
			TimePickerDialog dialogw = new TimePickerDialog(activity,
					EditGoalController.this, date.getHours(),
					date.getMinutes(), HOURS_TIME_FROMAT_24);

			return dialogw;
		}

		// private void setTimePickerId(int id){
		// ((TimePicker)this.getView().findViewById(R.id.timePicker)).setId(id);
		// }
	}
}
