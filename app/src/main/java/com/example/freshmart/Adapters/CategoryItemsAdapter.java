package com.example.freshmart.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshmart.Activities.MainActivity;
import com.example.freshmart.Classes.Item;
import com.example.freshmart.Classes.ShoppingCart;
import com.example.freshmart.Interfaces.OnItemListener;
import com.example.freshmart.R;

import java.util.HashMap;
import java.util.List;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.ViewHolder> {

    private List<Item> m_CategoryItemsList;
    private ShoppingCart m_UserShoppingCart;
    private HashMap<String, Item> m_UserLikedItemsList;
    private MainActivity m_HostedActivity;
    private OnItemListener m_Listener;

    public CategoryItemsAdapter(MainActivity i_HostedActivity, List<Item> i_CategoryItemsList, ShoppingCart i_UserShoppingCart,
                                HashMap<String, Item> i_UserLikedItemsList, OnItemListener i_Listener)
    {
        this.m_HostedActivity = i_HostedActivity;
        this.m_CategoryItemsList = i_CategoryItemsList;
        this.m_UserShoppingCart = i_UserShoppingCart;
        this.m_UserLikedItemsList = i_UserLikedItemsList;
        this.m_Listener = i_Listener;
    }

    @NonNull
    @Override
    public CategoryItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new CategoryItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsAdapter.ViewHolder holder, int position) {
        Item categoryItem = m_CategoryItemsList.get(position);
        int itemImage = m_HostedActivity.getResources().getIdentifier(categoryItem.getImage(), "drawable", m_HostedActivity.getPackageName());

        holder.m_CategoryItemImage.setImageResource(itemImage);
        holder.m_CategoryItemName.setText(categoryItem.getName());

        double price = categoryItem.getPrice();
        holder.m_CategoryItemPrice.setText(String.format("%.2f", price));


        if(isItemLiked(categoryItem))
        {
            holder.m_IsLiked.setImageResource(R.drawable.filled_heart);
        }
        else
        {
            holder.m_IsLiked.setImageResource(R.drawable.blank_heart);
        }

        if(isItemAddedToCart(categoryItem))
        {
            holder.m_IsAddedToCart.setImageResource(R.drawable.filled_shopping_cart);
        }
        else
        {
            holder.m_IsAddedToCart.setImageResource(R.drawable.blank_shopping_cart);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Listener.onItemClick(categoryItem);
            }
        });

        holder.m_IsAddedToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Listener.onAddToCartClick(categoryItem);
            }
        });

        holder.m_IsLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Listener.onLikeClick(categoryItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_CategoryItemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView m_CategoryItemImage;
        private TextView m_CategoryItemName;
        private TextView m_CategoryItemPrice;
        private ImageButton m_IsLiked;
        private ImageButton m_IsAddedToCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m_CategoryItemImage = itemView.findViewById(R.id.itemImage);
            m_CategoryItemName = itemView.findViewById(R.id.itemName);
            m_CategoryItemPrice = itemView.findViewById(R.id.itemPrice);
            m_IsLiked = itemView.findViewById(R.id.likeButton);
            m_IsAddedToCart = itemView.findViewById(R.id.addToCartButton);
        }
    }

    private boolean isItemLiked(Item i_Item)
    {
        return m_UserLikedItemsList.containsKey(i_Item.getName());
    }

    private boolean isItemAddedToCart(Item i_Item)
    {
        return m_UserShoppingCart.getShoppingCart().containsKey(i_Item.getName());
    }
}
