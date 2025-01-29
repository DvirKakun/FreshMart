package com.example.freshmart.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.freshmart.Classes.Item;
import com.example.freshmart.Classes.ShoppingCart;
import com.example.freshmart.Classes.User;
import com.example.freshmart.Controllers.UserController;
import com.example.freshmart.Interfaces.OnLikedItemsListUpdatedListener;
import com.example.freshmart.Interfaces.OnShoppingCartUpdatedListener;
import com.example.freshmart.Models.UserModel;
import com.example.freshmart.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private UserController m_UserController;
    private User m_User;
    private ShoppingCart m_UserShoppingCart;
    private HashMap<String, Item> m_UserLikedItemsList ;
    private OnLikedItemsListUpdatedListener m_LikedItemsListUpdatedListener;
    private OnShoppingCartUpdatedListener m_ShoppingCartUpdatedListener;
    private BottomNavigationView m_BottomNavigationView;
    private BadgeDrawable m_BadgeDrawable;
    private NavController m_NavContorller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        m_NavContorller = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView)).getNavController();
        m_BottomNavigationView = findViewById(R.id.navigationView);
        m_UserController = new UserController();

        m_BottomNavigationView.getMenu().findItem(R.id.shoppingCartBarButton).setEnabled(false);
        m_BottomNavigationView.getMenu().findItem(R.id.likedItemsBarButton).setEnabled(false);

        m_BottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                boolean isHandled = false;

                if (itemId == R.id.homeButton) {
                    m_NavContorller.navigate(R.id.categories_page);
                    isHandled = true;
                } else if (itemId == R.id.shoppingCartBarButton) {
                    m_NavContorller.navigate(R.id.shoppingCartPage);
                    isHandled = true;
                } else if (itemId == R.id.likedItemsBarButton) {
                    m_NavContorller.navigate(R.id.likedItemsPage);
                    isHandled = true;
                }
                else if(itemId == R.id.searchBarButton)
                {
                    m_NavContorller.navigate(R.id.searchPage);
                    isHandled = true;
                }

                return isHandled;
            }
        });

        m_NavContorller.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
                if (destinationId == R.id.categories_page) {
                    m_BottomNavigationView.getMenu().findItem(R.id.homeButton).setChecked(true);
                }
                else if (destinationId == R.id.shoppingCartPage) {
                    m_BottomNavigationView.getMenu().findItem(R.id.shoppingCartBarButton).setChecked(true);
                }
                else if (destinationId == R.id.likedItemsPage) {
                    m_BottomNavigationView.getMenu().findItem(R.id.likedItemsBarButton).setChecked(true);
                }
                else if(destinationId == R.id.searchPage)
                {
                    m_BottomNavigationView.getMenu().findItem(R.id.searchBarButton).setChecked(true);
                }
                else {
                    m_BottomNavigationView.getMenu().findItem(R.id.homeButton).setChecked(false);
                    m_BottomNavigationView.getMenu().findItem(R.id.shoppingCartBarButton).setChecked(false);
                    m_BottomNavigationView.getMenu().findItem(R.id.likedItemsBarButton).setChecked(false);
                    m_BottomNavigationView.getMenu().findItem(R.id.searchBarButton).setChecked(false);
                }
        });
    }
    public void SetLikedItemsListUpdateListener(OnLikedItemsListUpdatedListener listener) {
        this.m_LikedItemsListUpdatedListener = listener;
    }

    public void SetShoppingCartUpdatedListener(OnShoppingCartUpdatedListener listener)
    {
        this.m_ShoppingCartUpdatedListener = listener;
    }

    public void Login(String email, String password, View view) {
        m_UserController.Login(email, password, new UserModel.AuthCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Login Succeeded", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_login_page_to_categories_page);

                FetchUserShoppingCartFromFB(new UserModel.ShoppingCartCallback() {
                    @Override
                    public void onSuccess(ShoppingCart userShoppingCart) {
                        m_UserShoppingCart = userShoppingCart;
                        UpdateShoppingCartNavigationView();
                    }

                    @Override
                    public void onFailure(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "The email or password are invalid", Toast.LENGTH_SHORT).show();
                    }
                });

                FetchUserLikedItemsListFromFB(new UserModel.LikedItemsCallback() {
                    @Override
                    public void onSuccess(HashMap<String, Item> userLikedItemsList) {
                        m_UserLikedItemsList = userLikedItemsList;
                        UpdateLikedItemsNavigationView();
                    }

                    @Override
                    public void onFailure(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "The email or password are invalid", Toast.LENGTH_SHORT).show();
                    }
                });

                EnableNavigationView();
            }

            @Override
            public void onFailure(Exception i_Exception) {
                Toast.makeText(MainActivity.this, "The email or password are invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Register(String firstName, String lastName, String email, String password, String phoneNumber, View view) {
        m_UserController.Register(firstName, lastName, email, password, phoneNumber, new UserModel.AuthCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Registration Succeeded", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_signup_page_to_login_page);
            }

            @Override
            public void onFailure(Exception i_Exception) {
                Toast.makeText(MainActivity.this, "Authentication failed, please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GetUser()
    {
        m_UserController.GetUser(new UserModel.UserCallback() {
            @Override
            public void onSuccess(User user) {
                m_User = user;
                ActionBar actionBar = getSupportActionBar();

                if (actionBar != null) {
                    actionBar.setTitle("Welcome, " + m_User.getFirstName() + " " + m_User.getLastName());
                }
            }

            @Override
            public void onFailure(DatabaseError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void FetchUserShoppingCartFromFB(UserModel.ShoppingCartCallback callback)
    {
        m_UserController.GetUserShoppingCart(callback);
    }

    public void FetchUserLikedItemsListFromFB(UserModel.LikedItemsCallback callback)
    {
        m_UserController.GetUserLikedItemsList(callback);
    }

    public void RemoveItem(Item i_Item)
    {
        m_UserShoppingCart.getShoppingCart().remove(i_Item.getName());
        m_UserShoppingCart.setTotalPrice(m_UserShoppingCart.getTotalPrice() - (i_Item.getPrice() * i_Item.getQuantity()));

        if(m_UserLikedItemsList.containsKey(i_Item.getName())) {
            m_UserLikedItemsList.get(i_Item.getName()).setQuantity(0);
        }

        UpdateShoppingCart(m_UserShoppingCart);
        UpdateLikedItemsList(m_UserLikedItemsList);
    }

    public void UpdateLikedItemsList(HashMap<String, Item> i_UserLikedItemsList) //, UserModel.UpdateLikedItemsListCallback callback
    {
        m_UserLikedItemsList = i_UserLikedItemsList;

        m_UserController.UpdateLikedItemsList(i_UserLikedItemsList, new UserModel.UpdateLikedItemsListCallback() {
            @Override
            public void onSuccess() {
                if(m_LikedItemsListUpdatedListener != null) {
                    m_LikedItemsListUpdatedListener.onLikedItemsListUpdated();
                }

                UpdateLikedItemsNavigationView();
            }

            @Override
            public void onFailure(Exception error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdateShoppingCart(ShoppingCart i_UserShoppingCart)
    {
        m_UserShoppingCart = i_UserShoppingCart;

        m_UserController.UpdateShoppingCart(i_UserShoppingCart, new UserModel.UpdateShoppingCartCallback() {
            @Override
            public void onSuccess() {
                if(m_ShoppingCartUpdatedListener != null) {
                    m_ShoppingCartUpdatedListener.OnShoppingCartUpdated();
                }

                UpdateShoppingCartNavigationView();
            }

            @Override
            public void onFailure(Exception error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void SetData(ShoppingCart i_UserShoppingCart, HashMap<String, Item> i_UserLikedItemsList)
    {
        m_UserShoppingCart = i_UserShoppingCart;
        m_UserLikedItemsList = i_UserLikedItemsList;
    }

    public ShoppingCart GetUserShoppingCart()
    {
        return m_UserShoppingCart;
    }

    public HashMap<String, Item> GetUserLikedItemsList()
    {
        return m_UserLikedItemsList;
    }

    public void DisableNavigationView()
    {
        m_BottomNavigationView.setVisibility(View.GONE);
    }
    public void EnableNavigationView()
    {
        m_BottomNavigationView.setVisibility(View.VISIBLE);
        m_BottomNavigationView.setSelectedItemId(R.id.homeButton);
    }
    public void UpdateShoppingCartNavigationView()
    {
        int shoppingCartQuantity = m_UserShoppingCart.getShoppingCart().size();

        m_BadgeDrawable = m_BottomNavigationView.getOrCreateBadge(R.id.shoppingCartBarButton);

        if(shoppingCartQuantity > 0) {
            m_BadgeDrawable.setNumber(shoppingCartQuantity);
        }else
        {
            m_BottomNavigationView.removeBadge(R.id.shoppingCartBarButton);
        }

        m_BottomNavigationView.getMenu().findItem(R.id.shoppingCartBarButton).setEnabled(true);
    }

    public void UpdateLikedItemsNavigationView()
    {
        int likedItemsListQuantity = m_UserLikedItemsList.size();

        m_BadgeDrawable = m_BottomNavigationView.getOrCreateBadge(R.id.likedItemsBarButton);

        if(likedItemsListQuantity > 0) {
            m_BadgeDrawable.setNumber(likedItemsListQuantity);
        }else
        {
            m_BottomNavigationView.removeBadge(R.id.likedItemsBarButton);
        }

        m_BottomNavigationView.getMenu().findItem(R.id.likedItemsBarButton).setEnabled(true);
    }
}