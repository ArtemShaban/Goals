package by.bsu.goals.data;

import java.sql.Timestamp;
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
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private long categoryId;
    private long userId;
    private long parentId;
    private int position;

    private ArrayList<Goal> steps;

    public Goal()
    {
    }

    public Goal(long id, String title, Timestamp startedAt, Timestamp finishedAt, long userId)
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

    public Timestamp getStartedAt()
    {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt)
    {
        this.startedAt = startedAt;
    }

    public Timestamp getFinishedAt()
    {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt)
    {
        this.finishedAt = finishedAt;
    }
    
    //TODO Realize categories 
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
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", position=" + position +
                ", steps=" + steps +
                '}';
    }
}
