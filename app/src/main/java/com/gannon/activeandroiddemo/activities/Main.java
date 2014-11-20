package com.gannon.activeandroiddemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gannon.activeandroiddemo.R;
import com.gannon.activeandroiddemo.adapters.ProductArrayAdapter;
import com.gannon.activeandroiddemo.fragments.AddProductDialogFragment;
import com.gannon.activeandroiddemo.helpers.ModelHelper;
import com.gannon.activeandroiddemo.models.Category;
import com.gannon.activeandroiddemo.models.Product;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

/**
 * Main Activity
 */
public class Main extends Activity
{
    @InjectView(R.id.filter_spinner)
    protected Spinner filterSpinner;
    @InjectView(R.id.product_list)
    protected ListView productList;

    private ArrayAdapter<Category> categoryAdapter;
    private ProductArrayAdapter    productsAdapter;

    public void reloadCurrent()
    {
        filterSpinnerItemSelect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        ButterKnife.inject(this);

        categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item);
        productsAdapter = new ProductArrayAdapter(this);

        filterSpinner.setAdapter(categoryAdapter);
        productList.setAdapter(productsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() // reload adapter contents on resume
    {
        super.onResume();

        setCategoryAdapterSource(Category.getAll());
        setProductAdapterSource(Product.getAll());
    }

    @OnClick(R.id.add_button)
    protected void addButtonClick()
    {
        String tag = getResources().getString(R.string.add_product_dialog_tag);

        AddProductDialogFragment dialog = new AddProductDialogFragment();

        dialog.show(getFragmentManager(), tag);
    }

    @OnClick(R.id.reset_button)
    protected void resetButtonClick()
    {
        ModelHelper.seedProducts();

        filterSpinner.setSelection(0);

        setProductAdapterSource(Product.getAll());
    }

    @OnClick(R.id.clear_button)
    protected void clearButtonClick()
    {
        Product.deleteAll();

        filterSpinner.setSelection(0);

        setProductAdapterSource(Product.getAll());
        setCategoryAdapterSource(Category.getAll());
    }

    @OnItemSelected(R.id.filter_spinner)
    protected void filterSpinnerItemSelect()
    {
        Category category = (Category) filterSpinner.getSelectedItem();

        if (category.name.equals("(None)"))
        {
            setProductAdapterSource(Product.getAll());
        }
        else
        {
            setProductAdapterSource(category.getProducts());
        }
    }

    @OnItemClick(R.id.product_list)
    protected void productListItemSelect(int position)
    {
        Product product = (Product)productList.getItemAtPosition(position);

        Toast.makeText(this, "Product ID " + product.getId(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Set category adapter data source and update
     * @param categories List of Category categories to set
     */
    private void setCategoryAdapterSource(List<Category> categories)
    {
        categoryAdapter.clear();
        categoryAdapter.add(ModelHelper.getPlaceholderCategory());
        categoryAdapter.addAll(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    /**
     * Set product adapter data source and update
     * @param products List of Product categories to set
     */
    private void setProductAdapterSource(List<Product> products)
    {
        productsAdapter.clear();
        productsAdapter.addAll(products);
        productsAdapter.notifyDataSetChanged();
    }
}
