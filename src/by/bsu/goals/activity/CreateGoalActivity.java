package by.bsu.goals.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Artem Shaban
 * Since 2014 Май 14.
 */
public class CreateGoalActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        text.setText("CreateGoalActivity");
        addContentView(text, new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
