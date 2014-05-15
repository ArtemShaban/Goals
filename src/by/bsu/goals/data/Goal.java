package by.bsu.goals.data;

import by.bsu.goals.log.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Artem Shaban
 * Since 2014 MAY 11.
 */
public class Goal implements Comparable
{
    private final Logger logger = new Logger(this);
    private long id;
    private String title;
    private String description;
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private Long categoryId;
    private Long userId;
    private Long parentId;
    private Integer position;
    private ArrayList<Goal> steps;

    public Goal()
    {
    }

    public Goal(long id, String title, Timestamp startedAt, Timestamp finishedAt, Long userId)
    {
        this.id = id;
        this.title = title;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.userId = userId;
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
                ", parentId=" + getParentId() +
                ", position=" + position +
                ", steps=" + steps +
                '}';
    }

    @Override
    public int compareTo(Object another)
    {
        if (another instanceof Goal)
        {
            Goal anotherGoal = (Goal) another;
            if (anotherGoal.getPosition() != null && getPosition() != null)
            {
                if (anotherGoal.getPosition() > getPosition())
                {
                    return 1;
                }
                else if (anotherGoal.getPosition() == getPosition())
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                logger.e("Position is null", new Throwable());
                return 0;
            }
        }
        else
        {
            throw new RuntimeException("Impossible compare with another type.");
        }
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

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Integer getPosition()
    {
        return position;
    }

    public void setPosition(Integer position)
    {
        this.position = position;
    }

    public ArrayList<Goal> getSteps()
    {
        return steps;
    }

    public void setSteps(ArrayList<Goal> steps)
    {
        this.steps = steps;
    }
}
