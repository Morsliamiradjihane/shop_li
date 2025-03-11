package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Product;
import java.util.ArrayList;
import java.util.List;

public class FeaturedProductsAdapter extends RecyclerView.Adapter<FeaturedProductsAdapter.FeaturedProductViewHolder> {
    private List<Product> featuredProducts;
    private OnFeaturedProductClickListener listener;

    public interface OnFeaturedProductClickListener {
        void onFeaturedProductClick(Product product);
    }

    public FeaturedProductsAdapter(OnFeaturedProductClickListener listener) {
        this.featuredProducts = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeaturedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_product, parent, false);
        return new FeaturedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedProductViewHolder holder, int position) {
        Product product = featuredProducts.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return featuredProducts.size();
    }

    public void updateFeaturedProducts(List<Product> newProducts) {
        this.featuredProducts.clear();
        this.featuredProducts.addAll(newProducts);
        notifyDataSetChanged();
    }

    static class FeaturedProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productDescription;

        public FeaturedProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.featuredProductImage);
            productName = itemView.findViewById(R.id.featuredProductName);
            productPrice = itemView.findViewById(R.id.featuredProductPrice);
            productDescription = itemView.findViewById(R.id.featuredProductDescription);
        }

        public void bind(final Product product, final OnFeaturedProductClickListener listener) {
            productName.setText(product.getName());
            productPrice.setText(String.format("$%.2f", product.getPrice()));
            productDescription.setText(product.getDescription());
            
            // TODO: Load image using Glide or Picasso
            // Glide.with(itemView.getContext())
            //     .load(product.getImageUrl())
            //     .into(productImage);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFeaturedProductClick(product);
                }
            });
        }
    }
}