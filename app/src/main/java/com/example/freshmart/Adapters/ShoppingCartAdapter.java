package com.example.freshmart.Adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshmart.Activities.MainActivity;
import com.example.freshmart.Classes.CategoryItemsList;
import com.example.freshmart.Classes.Item;
import com.example.freshmart.Classes.ShoppingCart;
import com.example.freshmart.Interfaces.OnShoppingCartItemListener;
import com.example.freshmart.R;

import java.util.HashMap;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<Item> m_ShoppingCartAsList;
    private MainActivity m_HostedActivity;
    private OnShoppingCartItemListener m_Listener;

    public ShoppingCartAdapter(List<Item> i_UserShoppingCartAsList, MainActivity i_HostedActivity, OnShoppingCartItemListener i_Listener)
    {
        m_HostedActivity = i_HostedActivity;
        m_ShoppingCartAsList = i_UserShoppingCartAsList;
        m_Listener = i_Listener;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_item, parent, false);
        return new ShoppingCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.ViewHolder holder, int position) {
        int itemPosition = position;
        Item shoppingCartItem = m_ShoppingCartAsList.get(position);
        String itemImage = shoppingCartItem.getImage();

        holder.m_ItemName.setText(shoppingCartItem.getName());
        holder.m_ItemImage.setImageResource(m_HostedActivity.getResources().getIdentifier(itemImage, "drawable", m_HostedActivity.getPackageName()));

        double price = shoppingCartItem.getPrice();
        holder.m_ItemPrice.setText(String.format("%.2f", price));

        holder.m_ItemQuantity.setText("x" + " " + shoppingCartItem.getQuantity());

        holder.m_RemoveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_ShoppingCartAsList.remove(itemPosition);
                m_Listener.onRemoveItem(shoppingCartItem);
            }
        });

        holder.m_ItemQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Listener.onQuantitySelected(shoppingCartItem);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Listener.onItemClicked(shoppingCartItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_ShoppingCartAsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView m_ItemName;
        private ImageView m_ItemImage;
        private TextView m_ItemPrice;
        private TextView m_ItemQuantity;
        private ImageButton m_RemoveItemButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m_ItemName = itemView.findViewById(R.id.itemName);
            m_ItemImage = itemView.findViewById(R.id.itemImage);
            m_ItemPrice = itemView.findViewById(R.id.itemPrice);
            m_ItemQuantity = itemView.findViewById(R.id.itemQuantity);
            m_RemoveItemButton = itemView.findViewById(R.id.removeItem);
        }
    }

}
