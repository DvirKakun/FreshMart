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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LikedItemsAdapter  extends RecyclerView.Adapter<LikedItemsAdapter.ViewHolder>{
    private List <Item> m_UserLikedItemsList;
    private ShoppingCart m_UserShoppingCart;
    private MainActivity m_HostedActivity;
    private OnItemListener m_Listener;

    public LikedItemsAdapter(List<Item> i_UserLikedItemsList, MainActivity i_HostedActivity, OnItemListener i_Listener)
    {
        m_HostedActivity = i_HostedActivity;
        m_UserShoppingCart = m_HostedActivity.GetUserShoppingCart();
        m_UserLikedItemsList = i_UserLikedItemsList;
        m_Listener = i_Listener;
    }

    @NonNull
    @Override
    public LikedItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liked_list_item, parent, false);
        return new LikedItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedItemsAdapter.ViewHolder holder, int position) {
        int itemPosition = position;
        Item likedItem = m_UserLikedItemsList.get(position);
        String itemImage = likedItem.getImage();

        holder.m_ItemName.setText(likedItem.getName());
        holder.m_ItemImage.setImageResource(m_HostedActivity.getResources().getIdentifier(itemImage, "drawable", m_HostedActivity.getPackageName()));

        double price = likedItem.getPrice();

        holder.m_ItemPrice.setText(String.format("%.2f", price));
        holder.m_LikeButton.setImageResource(R.drawable.filled_heart);

        if(isItemAddedToCart(likedItem))
        {
            holder.m_AddToCartButton.setImageResource(R.drawable.filled_shopping_cart);
        }
        else
        {
            holder.m_AddToCartButton.setImageResource(R.drawable.blank_shopping_cart);
        }

        holder.m_AddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            m_Listener.onAddToCartClick(likedItem);
            }
        });

        holder.m_LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_UserLikedItemsList.remove(itemPosition);
                m_Listener.onLikeClick(likedItem);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Listener.onItemClick(likedItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_UserLikedItemsList.size();
    }

    public boolean isItemAddedToCart(Item item)
    {
        return m_UserShoppingCart.getShoppingCart().containsKey(item.getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView m_ItemName;
        private ImageView m_ItemImage;
        private TextView m_ItemPrice;
        private ImageButton m_LikeButton;
        private ImageButton m_AddToCartButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m_ItemName = itemView.findViewById(R.id.itemName);
            m_ItemImage = itemView.findViewById(R.id.itemImage);
            m_ItemPrice = itemView.findViewById(R.id.itemPrice);
            m_LikeButton = itemView.findViewById(R.id.likeButton);
            m_AddToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
