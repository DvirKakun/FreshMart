package com.example.freshmart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshmart.Activities.MainActivity;
import com.example.freshmart.Classes.Item;
import com.example.freshmart.Classes.ShoppingCart;
import com.example.freshmart.Interfaces.OnShoppingCartUpdatedListener;
import com.example.freshmart.R;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItem extends Fragment implements OnShoppingCartUpdatedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditItem.
     */
    // TODO: Rename and change types and number of parameters
    public static EditItem newInstance(String param1, String param2) {
        EditItem fragment = new EditItem();
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
    private Item m_CurrentItem;
    private ShoppingCart m_UserShoppingCart;
    private HashMap<String, Item> m_UserLikedItemsList;
    private MainActivity m_HostedActivity;
    private TextView m_ItemName;
    private ImageView m_ItemImage;
    private TextView m_ItemPrice;
    private ImageButton m_CloseButton;
    private MaterialButton m_AddToCart;
    private ImageButton m_LikedButton;
    private ImageButton m_MinusButton;
    private ImageButton m_AddButton;
    private TextView m_Quantity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        m_View = inflater.inflate(R.layout.edit_item_page, container, false);
        m_CurrentItem = (Item) getArguments().getSerializable("item");
        m_HostedActivity = (MainActivity) requireActivity();
        m_ItemName = m_View.findViewById(R.id.itemName);
        m_ItemImage = m_View.findViewById(R.id.itemImage);
        m_ItemPrice = m_View.findViewById(R.id.itemPrice);
        m_CloseButton = m_View.findViewById(R.id.closeButton);
        m_AddToCart = m_View.findViewById(R.id.addToCartButton);
        m_LikedButton = m_View.findViewById(R.id.likeButton);
        m_MinusButton = m_View.findViewById(R.id.minusButton);
        m_AddButton = m_View.findViewById(R.id.addButton);
        m_Quantity = m_View.findViewById(R.id.itemQuantity);

        m_HostedActivity.SetShoppingCartUpdatedListener(this);
        m_HostedActivity.SetLikedItemsListUpdateListener(null);

        getUserDetails();
        showItem();

        m_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(m_Quantity.getText().toString());

                m_UserShoppingCart.AddToCart(m_CurrentItem, quantity);

                if(m_UserLikedItemsList.containsKey(m_CurrentItem.getName()))
                {
                    m_UserLikedItemsList.put(m_CurrentItem.getName(), m_CurrentItem);
                }

                m_HostedActivity.UpdateShoppingCart(m_UserShoppingCart);
                m_HostedActivity.UpdateLikedItemsList(m_UserLikedItemsList);
            }
        });

        m_LikedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_UserLikedItemsList.containsKey(m_CurrentItem.getName()))
                {
                    m_UserLikedItemsList.remove(m_CurrentItem.getName());
                    m_LikedButton.setImageResource(R.drawable.blank_heart);
                }
                else
                {
                    HashMap<String, Item> userShoppingCart = m_UserShoppingCart.getShoppingCart();
                    if(userShoppingCart.containsKey(m_CurrentItem.getName()))
                    {
                        m_CurrentItem.setQuantity(userShoppingCart.get(m_CurrentItem.getName()).getQuantity());
                    }

                    m_UserLikedItemsList.put(m_CurrentItem.getName(), m_CurrentItem);
                    m_LikedButton.setImageResource(R.drawable.filled_heart);

                }

                m_HostedActivity.UpdateLikedItemsList(m_UserLikedItemsList);
            }
        });

        m_AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qunatity = Integer.parseInt(m_Quantity.getText().toString());
                qunatity += 1;
                m_Quantity.setText(qunatity + "");

                updateMinusButtonState(qunatity);
            }
        });

        m_MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qunatity = Integer.parseInt(m_Quantity.getText().toString());
                qunatity -= 1;
                m_Quantity.setText(qunatity + "");

                updateMinusButtonState(qunatity);
            }
        });

        m_CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return m_View;
    }

    private void updateMinusButtonState(int quantity) {
        if (quantity <= 1) {
            m_MinusButton.setEnabled(false);
            m_MinusButton.setAlpha(0.5f);
            m_MinusButton.setBackgroundColor(0xB0B0B0);
        } else {
            m_MinusButton.setEnabled(true);
            m_MinusButton.setAlpha(1.0f);
            m_MinusButton.setBackgroundColor(0x3D3C3C);
        }
    }

    private void getUserDetails() {
        m_UserShoppingCart = m_HostedActivity.GetUserShoppingCart();
        m_UserLikedItemsList = m_HostedActivity.GetUserLikedItemsList();
    }

    private void showItem()
    {
        int itemImage = m_HostedActivity.getResources().getIdentifier(m_CurrentItem.getImage(), "drawable", m_HostedActivity.getPackageName());

        m_ItemName.setText(m_CurrentItem.getName());
        m_ItemImage.setImageResource(itemImage);
        m_ItemPrice.setText(String.format("%.2f", m_CurrentItem.getPrice()));


        if(isItemLiked())
        {
           m_LikedButton.setImageResource(R.drawable.filled_heart);
        }
        else
        {
            m_LikedButton.setImageResource(R.drawable.blank_heart);
        }

        updateMinusButtonState(1);
    }

    private boolean isItemLiked()
    {
        return m_UserLikedItemsList.containsKey(m_CurrentItem.getName());
    }

    @Override
    public void OnShoppingCartUpdated() {
        Toast.makeText(getContext(), "Item has been added to cart", Toast.LENGTH_SHORT).show();
    }
}