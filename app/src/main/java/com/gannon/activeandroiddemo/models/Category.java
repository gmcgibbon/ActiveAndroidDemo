package com.gannon.activeandroiddemo.models;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Categories Table Model
 */
@Table(name = "Categories")
public class Category extends Model
{
    @Column(index = true)
    public String name; // column annotations without names will use the field name by default

    /**
     * Get associated products
     * @return
     */
    public List<Product> getProducts() // associated products getter
    {
        return getMany(Product.class, "category_id");
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * Get all categories
     * @return List of Category categories
     */
    public static List<Category> getAll()
    {

        return new Select()
                .all()
                .from(Category.class)
                .execute();
    }

    /**
     * Deletes all categories
     */
    public static void deleteAll()
    {
        new Delete()
                .from(Category.class)
                .execute();

        ActiveAndroid.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'Categories'");
    }
}
