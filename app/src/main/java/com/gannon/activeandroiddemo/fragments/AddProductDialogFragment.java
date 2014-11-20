package com.gannon.activeandroiddemo.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gannon.activeandroiddemo.R;
import com.gannon.activeandroiddemo.activities.Main;
import com.gannon.activeandroiddemo.models.Category;
import com.gannon.activeandroiddemo.models.Product;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Add Product Dialog Fragment
 */
public class AddProductDialogFragment extends DialogFragment
{
    @InjectView(R.id.title_edit)
    protected EditText titleEdit;
    @InjectView(R.id.description_edit)
    protected EditText descriptionEdit;
    @InjectView(R.id.price_edit)
    protected EditText priceEdit;
    @InjectView(R.id.category_spinner)
    protected Spinner categorySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        ButterKnife.inject(this, view);

        getDialog().setTitle("Add Product");

        categorySpinner.setAdapter(
                new ArrayAdapter<Category>(
                        getActivity(), android.R.layout.simple_spinner_item, Category.getAll()
                )
        );

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);

        ((Main)getActivity()).reloadCurrent();
    }

    @OnClick(R.id.create_button)
    protected void createdButtonClick()
    {
        saveProduct();

        getDialog().dismiss();
    }

    /**
     * Persist new product to database
     */
    private void saveProduct()
    {
        Product product = new Product((Category)categorySpinner.getSelectedItem());

        product.title       = titleEdit.getText().toString();
        product.description = descriptionEdit.getText().toString();
        product.price       = Double.parseDouble(priceEdit.getText().toString());

        product.save();

        Toast.makeText(
                getActivity(), "Product has been added to database", Toast.LENGTH_SHORT
        ).show();
    }
}
