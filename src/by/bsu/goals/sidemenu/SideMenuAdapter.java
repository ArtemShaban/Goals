package by.bsu.goals.sidemenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
    private Activity activity;
    private List<Goal> goals;

    public SideMenuAdapter(Activity activity, List<Goal> goals)
    {
        this.activity = activity;
        this.goals = goals;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.side_menu_list_item, null);

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
                ListView listView = (ListView) v.findViewById(R.id.side_menu_item_list_child);
                if (listView.getVisibility() == View.GONE)
                {
                    ViewHolder viewHolder = (ViewHolder) v.getTag();
                    ArrayList<Goal> steps = ((Goal) getItem(viewHolder.position)).getSteps();
                    if (steps != null)
                    {
                        listView.setAdapter(new SideMenuAdapter(activity, steps));
                        listView.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    listView.setVisibility(View.GONE);
                }
            }
        });
        return view;
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
