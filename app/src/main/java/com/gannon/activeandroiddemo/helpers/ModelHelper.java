package com.gannon.activeandroiddemo.helpers;

import android.content.Context;

import com.gannon.activeandroiddemo.ActiveAndroidDemo;
import com.gannon.activeandroiddemo.R;
import com.gannon.activeandroiddemo.models.Category;
import com.gannon.activeandroiddemo.models.Product;

/**
 * Model helper methods
 */
public class ModelHelper
{
    protected static Context context;

    static
    {
        context = ActiveAndroidDemo.getContext();
    }

    /**
     * Seed default categories
     */
    public static void seedCategories()
    {
        String[] names = context.getResources().getStringArray(R.array.seed_categories);

        Category.deleteAll();

        for (String name : names)
        {
            Category category = new Category();

            category.name = name;
            category.save();
        }
    }

    /**
     * Seed default products
     */
    public static void seedProducts()
    {
        String[] valueSets = context.getResources().getStringArray(R.array.seed_products);

        Product.deleteAll();

        for (String set : valueSets)
        {
            Product  product = new Product();
            String[] values = set.split(",\\s*");

            product.title       = values[0];
            product.description = values[1];
            product.price       = Double.parseDouble(values[2]);
            product.category    = Category.load(Category.class, Long.parseLong(values[3]));
            product.save();
        }
    }

    public static Category getPlaceholderCategory()
    {
        Category placeholder = new Category();

        placeholder.name = "(None)";

        return placeholder;
    }
}
