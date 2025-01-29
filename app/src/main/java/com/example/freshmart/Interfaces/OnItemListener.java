package com.example.freshmart.Interfaces;

import com.example.freshmart.Classes.Item;

public interface OnItemListener
{
    public void onItemClick(Item item);
    public void onLikeClick(Item item);
    public void onAddToCartClick(Item item);

}
