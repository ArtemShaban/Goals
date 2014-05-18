package by.bsu.goals.sidemenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.data.Goal;

import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 18.
 */

public class SideMenuStepsView extends LinearLayout
{
    private final LayoutInflater inflater;
    private final LayoutParams layoutParams;
    private final int width;
    private List<Goal> steps;
    private TextView title;
    private TextView startAt;
    private TextView finishAt;
    private SideMenuAdapter.OnGoalCLickedListener listener;

    public SideMenuStepsView(Context context, List<Goal> steps, int parentWidthPx, SideMenuAdapter.OnGoalCLickedListener listener)
    {
        super(context);
        this.steps = steps;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setOrientation(VERTICAL);
        width = (int) (parentWidthPx * 0.9);
        layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        this.listener = listener;
        init();
    }

    public void init()
    {
        removeAllViews();
        for (final Goal step : steps)
        {
            LinearLayout view = (LinearLayout) inflater.inflate(R.layout.side_menu_step_item, null);
            title = (TextView) view.findViewById(R.id.side_menu_step_title);
            title.setText(step.getTitle());
            startAt = (TextView) view.findViewById(R.id.side_menu_step_started_at);
            startAt.setText(step.getStartedAt().toString());
            finishAt = (TextView) view.findViewById(R.id.side_menu_step_finish_at);
            finishAt.setText(step.getFinishedAt().toString());
            view.setTag(step.getSteps());
            LinearLayout container = (LinearLayout) view.findViewById(R.id.side_menu_step_container);
            container.setVisibility(GONE);

            view.setOnClickListener(new OnClickListener()
            {
                private final Goal currentStep = step;

                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.onGoalClicked(currentStep);
                    }

                    LinearLayout container = (LinearLayout) v.findViewById(R.id.side_menu_step_container);
                    if (container.getVisibility() == GONE)
                    {
                        List<Goal> subSteps = (List<Goal>) v.getTag();
                        if (subSteps != null)
                        {
                            container.addView(new SideMenuStepsView(getContext(), subSteps, width, listener));
                            container.setVisibility(VISIBLE);
                        }
                    }
                    else
                    {
                        container.setVisibility(GONE);
                    }
                }
            });
            addView(view);
        }
    }
}
