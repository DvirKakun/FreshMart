package com.example.freshmart.Interfaces;

import com.example.freshmart.Classes.Item;

import java.util.List;

public interface OnCategoryItemsFetchedListener {
    void onCategoryItemsFetched(List<Item> categoryItems);
}
