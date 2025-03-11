package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private OnCategoryClickListener listener;
    private int selectedPosition = 0;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category, int position);
    }

    public CategoriesAdapter(OnCategoryClickListener listener) {
        this.categories = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, position, selectedPosition == position, listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategories(List<Category> newCategories) {
        this.categories.clear();
        this.categories.addAll(newCategories);
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(position);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView categoryIcon;
        private View itemContainer;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            itemContainer = itemView.findViewById(R.id.categoryContainer);
        }

        public void bind(final Category category, final int position, boolean isSelected,
                        final OnCategoryClickListener listener) {
            categoryName.setText(category.getName());
            // TODO: Load category icon using Glide or Picasso
            // Glide.with(itemView.getContext()).load(category.getImageUrl()).into(categoryIcon);

            itemContainer.setSelected(isSelected);
            
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category, position);
                }
            });
        }
    }
}