package com.example.freshmart.Interfaces;

import com.example.freshmart.Classes.Item;

public interface OnShoppingCartItemListener {
    public void onRemoveItem(Item item);
    public void onQuantitySelected(Item item);
    public void onItemClicked(Item item);
}
