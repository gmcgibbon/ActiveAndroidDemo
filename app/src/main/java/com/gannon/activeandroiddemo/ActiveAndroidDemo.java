package com.gannon.activeandroiddemo;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.gannon.activeandroiddemo.helpers.ModelHelper;
import com.gannon.activeandroiddemo.models.Category;
import com.gannon.activeandroiddemo.models.Product;

/**
 * ActiveAndroid Demo Application
 */
public class ActiveAndroidDemo extends Application
{
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();

        ActiveAndroid.initialize(this); // init active android on application create

        context = this;

        initializeDatabase();
    }

    /**
     * Initialize database, seed values if empty
     */
    private void initializeDatabase()
    {
        if (Category.getAll().size() == 0)
        {
            ModelHelper.seedCategories();
        }

        if (Product.getAll().size() == 0)
        {
            ModelHelper.seedProducts();
        }
    }

    /**
     * Get Application-wide context
     * @return Context app context
     */
    public static Context getContext()
    {
        return context;
    }
}
