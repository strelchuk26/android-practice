package com.example.shop.category;

import android.view.View;
import android.widget.TextView;

import com.example.shop.R;

// ListView
public class CategoryListViewHolder {
    public TextView categoryName;
    public TextView categoryDescription;

    public CategoryListViewHolder(View itemView) {
        categoryName = itemView.findViewById(R.id.categoryName);
        categoryDescription = itemView.findViewById(R.id.categoryDescription);
    }
}

