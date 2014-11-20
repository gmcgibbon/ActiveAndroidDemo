package com.gannon.activeandroiddemo.models;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.text.NumberFormat;
import java.util.List;

/**
 * Product Table Model
 */
@Table(name = "Products")
public class Product extends Model
{
    @Column(index = true) // index this column for faster searching by title
    public String title;
    @Column
    public String description;
    @Column
    public double price;

    @Column(name = "category_id", onDelete = Column.ForeignKeyAction.SET_NULL) // explicitly named column
                                                                               // when reference is deleted set to null
    public Category category;

    /**
     * Constructor
     */
    public Product() // super call required
    {
        super();
    }

    /**
     * Constructor
     * @param category Category to associate with
     */
    public Product(Category category) // multiple constructors may be used to add associations on creation
    {
        this();
        this.category = category;
    }

    /**
     * Gets price value as currency
     * @return String currency price
     */
    public String getPriceAsCurrency() // formatting shortcuts are often implemented in models
    {
        return NumberFormat
                .getCurrencyInstance()
                .format(price);
    }

    /**
     * Get all products
     * @return List of Product products
     */
    public static List<Product> getAll()
    {

        return new Select()
                .all()
                .from(Product.class)
                .execute();
    }

    /**
     * Deletes all products
     */
    public static void deleteAll()
    {
        new Delete()
                .from(Product.class)
                .execute();

        ActiveAndroid.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'Products'");
    }
}
