package by.bsu.goals.sidemenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import by.bsu.goals.R;
import by.bsu.goals.data.Goal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 18.
 */
public class SideMenuAdapter extends BaseAdapter
{
    private static LayoutInflater inflater = null;
    private final Activity activity;
    private final int width;
    private List<Goal> goals;
    private OnGoalCLickedListener goalCLickedListener;

    public SideMenuAdapter(Activity activity, List<Goal> goals, int widthSideMenu)
    {
        this.activity = activity;
        this.goals = goals;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        width = widthSideMenu;
    }

    public void setGoalCLickedListener(OnGoalCLickedListener goalCLickedListener)
    {
        this.goalCLickedListener = goalCLickedListener;
    }

    public int getCount()
    {
        return goals.size();
    }

    public Object getItem(int position)
    {
        return goals.get(position);
    }

    public long getItemId(int position)
    {
        return goals.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        final ViewHolder holder;

        if (convertView == null)
        {
            view = inflater.inflate(R.layout.side_menu_goal_item, null);

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.side_menu_item_title);
            holder.finishAt = (TextView) view.findViewById(R.id.side_menu_item_finish_at);
            holder.startAt = (TextView) view.findViewById(R.id.side_menu_item_started_at);
            holder.countChildren = (TextView) view.findViewById(R.id.side_menu_item_count_children);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Goal goal = goals.get(position);
        holder.title.setText(goal.getTitle());
        holder.finishAt.setText(goal.getFinishedAt().toString());
        holder.startAt.setText(goal.getStartedAt().toString());
        holder.countChildren.setText(goal.getSteps() != null ? String.valueOf(goal.getSteps().size()) : "0");
        holder.position = position;
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ViewHolder viewHolder = (ViewHolder) v.getTag();
                Goal goal = (Goal) getItem(viewHolder.position);
                if (goal.getParentId() == null && goalCLickedListener != null)
                {
                    goalCLickedListener.onGoalClicked(goal);
                }

                LinearLayout childrenList = (LinearLayout) v.findViewById(R.id.side_menu_item_lower_container);
                if (childrenList.getVisibility() == View.GONE)
                {
                    ArrayList<Goal> steps = goal.getSteps();
                    if (steps != null)
                    {
                        SideMenuStepsView child = new SideMenuStepsView(activity, steps, width);
                        childrenList.addView(child);
                        childrenList.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    childrenList.setVisibility(View.GONE);
                    childrenList.removeAllViews();
                }
            }
        });
        return view;
    }

    public interface OnGoalCLickedListener
    {
        public void onGoalClicked(Goal goal);
    }

    private class ViewHolder
    {
        TextView title;
        TextView startAt;
        TextView finishAt;
        TextView countChildren;
        int position;
    }

}
