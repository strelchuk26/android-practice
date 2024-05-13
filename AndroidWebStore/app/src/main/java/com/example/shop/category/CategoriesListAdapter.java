package com.example.shop.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop.R;
import com.example.shop.dto.category.CategoryItemDTO;

import java.util.List;

// Adapter for ListView
public class CategoriesListAdapter extends ArrayAdapter<CategoryItemDTO> {
    private List<CategoryItemDTO> items;
    private LayoutInflater inflater;

    public CategoriesListAdapter(Context context, List<CategoryItemDTO> items) {
        super(context, R.layout.category_view, items);
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CategoryListViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_view, parent, false);
            holder = new CategoryListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryListViewHolder) convertView.getTag();
        }

        CategoryItemDTO item = items.get(position);
        holder.categoryName.setText(item.getName());
        holder.categoryDescription.setText(item.getDescription());

        return convertView;
    }
}

