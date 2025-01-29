package com.example.freshmart.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshmart.Activities.MainActivity;
import com.example.freshmart.Classes.Category;
import com.example.freshmart.Interfaces.OnCategoryClickListener;
import com.example.freshmart.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> m_CategoriesList;
    private OnCategoryClickListener m_Listener;
    private MainActivity m_HostedActivity;

    public CategoriesAdapter(MainActivity i_HostedActivity, List<Category> i_CategoriesList, OnCategoryClickListener i_Listener)
    {
        this.m_HostedActivity = i_HostedActivity;
        this.m_CategoriesList = i_CategoriesList;
        this.m_Listener = i_Listener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view, m_Listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        Category category = m_CategoriesList.get(position);
        int categoryImage = m_HostedActivity.getResources().getIdentifier(category.getImage(), "drawable", m_HostedActivity.getPackageName());

        holder.m_CategoryImage.setImageResource(categoryImage);
        holder.m_CategoryName.setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_Listener != null)
                {
                    m_Listener.onCategoryClick(category.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_CategoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView m_CategoryImage;
        private TextView m_CategoryName;
        public ViewHolder(@NonNull View itemView, OnCategoryClickListener i_Listener) {
            super(itemView);
            m_CategoryImage = itemView.findViewById(R.id.categoryImage);
            m_CategoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
