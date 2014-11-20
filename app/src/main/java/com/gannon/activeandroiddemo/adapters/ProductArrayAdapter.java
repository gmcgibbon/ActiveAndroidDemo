package com.gannon.activeandroiddemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gannon.activeandroiddemo.R;
import com.gannon.activeandroiddemo.models.Product;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Product Adapter
 */
public class ProductArrayAdapter extends ArrayAdapter<Product>
{
    /**
     * Constructor
     * @param context Context parent activity
     */
    public ProductArrayAdapter(Context context)
    {
        super(context, R.layout.template_product_item);
    }

    /**
     * Constructor
     * @param context Context parent activity
     * @param items   List of Product products to list
     */
    public ProductArrayAdapter(Context context, List<Product> items)
    {
        super(context, R.layout.template_product_item, items);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        ItemHolder holder;

        if (view != null)
        {
            holder = (ItemHolder) view.getTag();
        }
        else
        {
            view   = inflater.inflate(R.layout.template_product_item, parent, false);
            holder = new ItemHolder(view);
            view.setTag(holder);
        }

        initItem(holder, getItem(position));

        return view;
    }

    private void initItem(ItemHolder holder, Product data)
    {
        holder.titleText.setText(data.title);
        holder.descriptionText.setText(data.description);
        holder.priceText.setText(data.getPriceAsCurrency());

        if (data.category != null)
        {
            holder.categoryText.setText(data.category.name);
        }
        else
        {
            holder.categoryText.setText("None");
        }
    }

    static class ItemHolder
    {
        @InjectView(R.id.title_text)
        TextView titleText;
        @InjectView(R.id.description_text)
        TextView descriptionText;
        @InjectView(R.id.price_text)
        TextView priceText;
        @InjectView(R.id.category_text)
        TextView categoryText;

        public ItemHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }
}
