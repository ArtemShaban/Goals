package com.shaban.goals.objects;

import java.util.ArrayList;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 11.
 */
public class Goal
{
    private long id;
    private String title;
    private String description;
    private long startedAt;
    private long finishedAt;
    private long notifyAt;
    private long categoryId;
    private long userId;
    private long parentId;
    private int position;

    private ArrayList<Goal> steps;

    public Goal()
    {
    }

    public Goal(long id, String title, long startedAt, long finishedAt, long userId)
    {
        this.id = id;
        this.title = title;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.userId = userId;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public long getStartedAt()
    {
        return startedAt;
    }

    public void setStartedAt(long startedAt)
    {
        this.startedAt = startedAt;
    }

    public long getFinishedAt()
    {
        return finishedAt;
    }

    public void setFinishedAt(long finishedAt)
    {
        this.finishedAt = finishedAt;
    }

    public long getNotifyAt()
    {
        return notifyAt;
    }

    public void setNotifyAt(long notifyAt)
    {
        this.notifyAt = notifyAt;
    }

    public long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(long categoryId)
    {
        this.categoryId = categoryId;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public long getParentId()
    {
        return parentId;
    }

    public void setParentId(long parentId)
    {
        this.parentId = parentId;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public void setSteps(ArrayList<Goal> steps)
    {
        this.steps = steps;
    }


    @Override
    public String toString()
    {
        return "Goal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                ", notifyAt=" + notifyAt +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", position=" + position +
                ", steps=" + steps +
                '}';
    }
}
