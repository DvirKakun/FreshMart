package com.example.freshmart.Interfaces;

import com.example.freshmart.Classes.Category;

import java.util.List;

public interface OnCategoriesFetchedListener {
    void onCategoriesFetched(List<Category> categories);
}
