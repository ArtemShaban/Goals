package by.bsu.goals.log;

import android.util.Log;

/**
 * Created by Artem Shaban
 * Since 2014 may 08.
 */
public class Logger
{
    private static final String TAG = "GOALS";
    private String className;

    @SuppressWarnings("rawtypes")
	public Logger(Class clazz)
    {
        className = clazz.getName();
    }

    public Logger(String className)
    {
        this.className = className;
    }

    public Logger(Object object)
    {
        className = object.getClass().getName();
    }

    public void i(String msg)
    {
        Log.i(TAG, className + msg);
    }

    public void e(String msg, Throwable throwable)
    {
        Log.e(TAG, className + msg, throwable);
    }
}
