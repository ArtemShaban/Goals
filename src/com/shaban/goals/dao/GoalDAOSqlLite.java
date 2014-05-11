package com.shaban.goals.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.shaban.goals.log.Logger;
import com.shaban.goals.objects.Goal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 11.
 */
public class GoalDAOSqlLite implements GoalDAO
{
    private static final String TABLE_NAME = "goals";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED_AT = "started_at";
    private static final String FINISH_AT = "finish_at";
    private static final String CATEGORY_ID = "category_id";
    private static final String USER_ID = "user_id";
    private static final String PARENT_ID = "parent_id";
    private static final String POSITION = "list_index";

    private final SQLiteOpenHelper dbHelper;
    private final Logger logger = new Logger(this);
    private SQLiteDatabase writableDatabase;
    private SQLiteDatabase readableDatabase;

    public GoalDAOSqlLite(SQLiteOpenHelper sqLiteOpenHelper)
    {
        this.dbHelper = sqLiteOpenHelper;
        writableDatabase = dbHelper.getWritableDatabase();
        readableDatabase = dbHelper.getReadableDatabase();

        Cursor c = writableDatabase.query("user", null, null, null, null, null, null);

        if (c != null)
        {
            if (c.moveToFirst())
            {
                String str;
                do
                {
                    str = "";
                    for (String cn : c.getColumnNames())
                    {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.i("Goal", str);

                } while (c.moveToNext());
            }
        }
        c.close();
        dbHelper.close();

    }

    @Override
    public long saveGoal(Goal goal)
    {
        return writableDatabase.insert(TABLE_NAME, null, goalToValues(goal));
    }

    @Override
    public Goal loadGoal(long goalId)
    {
        String selection = ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(goalId)};
        Cursor c = readableDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        Goal goal = null;
        if (c != null && c.moveToFirst())
        {
            goal = getGoal(c);
            logger.i("Loaded " + goal.toString());
        }
        c.close();
        return goal;
    }

    @Override
    public List<Goal> loadChildren(long parentId)
    {
        Cursor c = readableDatabase.query(TABLE_NAME, null, PARENT_ID + "=", new String[]{String.valueOf(parentId)}, null, null, null);

        ArrayList<Goal> goals = null;
        if (c != null && c.moveToFirst())
        {
            goals = new ArrayList<Goal>();
            logger.i(c.getCount() + "goals loaded.");
            do
            {
                Goal goal = getGoal(c);
                goals.add(goal);

                logger.i("Loaded " + goal.toString());
            } while (c.moveToNext());
        }
        c.close();
        return goals;
    }

    @Override
    public Goal loadGoalWithChildren(long goalId)
    {
        Goal goal = loadGoal(goalId);
        goal.setSteps((ArrayList<Goal>) loadChildren(goalId));
        return goal;
    }

    @Override
    public List<Goal> loadAllGoals(Long userId)
    {
        Cursor c;
        if (userId != null)
        {
            c = readableDatabase.query(TABLE_NAME, null, USER_ID + "=", new String[]{String.valueOf(userId)}, null, null, null);
        }
        else
        {
            throw new RuntimeException("userId is null");
        }

        ArrayList<Goal> goals = null;
        if (c != null && c.moveToFirst())
        {
            goals = new ArrayList<Goal>();
            logger.i(c.getCount() + "goals loaded.");
            do
            {
                Goal goal = getGoal(c);
                goals.add(goal);

                logger.i("Loaded " + goal.toString());
            } while (c.moveToNext());
        }
        c.close();
        return goals;
    }

    @Override
    public void updateGoal(Goal goal)
    {
        int updatedItems = writableDatabase.update(TABLE_NAME, goalToValues(goal), ID + " = ?", new String[]{String.valueOf(goal.getId())});
        if (updatedItems > 1)
        {
            throw new RuntimeException("Updated more than one goal. ID");
        }
    }

    @Override
    public void releaseResources()
    {
        dbHelper.close();
    }

    private ContentValues goalToValues(Goal goal)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, goal.getTitle());
        contentValues.put(DESCRIPTION, goal.getDescription());
        contentValues.put(STARTED_AT, goal.getStartedAt());
        contentValues.put(FINISH_AT, goal.getFinishedAt());
        contentValues.put(CATEGORY_ID, goal.getCategoryId());
        contentValues.put(USER_ID, goal.getUserId());
        contentValues.put(PARENT_ID, goal.getParentId());
        contentValues.put(POSITION, goal.getPosition());
        return contentValues;
    }

    private Goal getGoal(Cursor cursor)
    {
        Goal goal = new Goal();
        goal.setId(cursor.getLong(cursor.getColumnIndex(ID)));
        goal.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
        goal.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
        goal.setStartedAt(cursor.getLong(cursor.getColumnIndex(STARTED_AT)));
        goal.setFinishedAt(cursor.getLong(cursor.getColumnIndex(FINISH_AT)));
        goal.setCategoryId(cursor.getLong(cursor.getColumnIndex(CATEGORY_ID)));
        goal.setUserId(cursor.getLong(cursor.getColumnIndex(USER_ID)));
        goal.setParentId(cursor.getLong(cursor.getColumnIndex(PARENT_ID)));
        goal.setPosition(cursor.getInt(cursor.getColumnIndex(POSITION)));
        return goal;
    }

}
