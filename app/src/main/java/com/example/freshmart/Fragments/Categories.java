package com.example.freshmart.Fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.freshmart.Activities.MainActivity;
import com.example.freshmart.Adapters.CategoriesAdapter;
import com.example.freshmart.Classes.CategoriesList;
import com.example.freshmart.Classes.Category;
import com.example.freshmart.Interfaces.OnCategoriesFetchedListener;
import com.example.freshmart.Interfaces.OnCategoryClickListener;
import com.example.freshmart.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Categories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Categories extends Fragment implements OnCategoryClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Categories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Categories newInstance(String param1, String param2) {
        Categories fragment = new Categories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View m_View;
    private RecyclerView m_CategoriesRecyclerView;
    private CategoriesAdapter m_Adapter;
    private List<Category> m_CategoriesList;
    private MainActivity m_HostedActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        m_View = inflater.inflate(R.layout.categories_page, container, false);
        m_HostedActivity = (MainActivity) requireActivity();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(m_View).navigate(R.id.action_categories_page_to_login_page);
                m_HostedActivity.DisableNavigationView();
            }
        });

        m_HostedActivity.GetUser();

        m_CategoriesRecyclerView = m_View.findViewById(R.id.categoriesRecyclerView);
        m_CategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        CategoriesList.GetCategoriesList(m_HostedActivity, new OnCategoriesFetchedListener() {
            @Override
            public void onCategoriesFetched(List<Category> categories) {
                m_CategoriesList = categories;

                createAdapter();
            }
        });

        return m_View;
    }

    private void createAdapter(){
        m_Adapter = new CategoriesAdapter(m_HostedActivity, m_CategoriesList, this);
        m_CategoriesRecyclerView.setAdapter(m_Adapter);
    }

    @Override
    public void onCategoryClick(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("category_id", categoryId);

        Navigation.findNavController(m_View)
                .navigate(R.id.action_categories_page_to_category_items_page, bundle);
    }

}