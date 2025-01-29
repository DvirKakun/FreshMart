package com.example.freshmart.Controllers;

import com.example.freshmart.Classes.Item;
import com.example.freshmart.Classes.ShoppingCart;
import com.example.freshmart.Classes.User;
import com.example.freshmart.Models.UserModel;

import java.util.HashMap;
import java.util.List;

public class UserController {
    private UserModel m_UserModel;

    public UserController()
    {
        m_UserModel = new UserModel();
    }
    public void Login(String i_Email, String i_Password, UserModel.AuthCallback callback) {
        m_UserModel.Login(i_Email, i_Password, callback);
    }

    public void Register(String i_FirstName, String i_LastName, String i_Email, String i_Password, String i_PhoneNumber, UserModel.AuthCallback callback)
    {
        User newUser = new User(i_FirstName, i_LastName, i_Password, i_Email, i_PhoneNumber);
        m_UserModel.Register(newUser, callback);
    }

    public void GetUser(UserModel.UserCallback callback)
    {
        m_UserModel.GetUser(callback);
    }

    public void UpdateShoppingCart(ShoppingCart i_UserShoppingCart, UserModel.UpdateShoppingCartCallback callback)
    {
        m_UserModel.UpdateShoppingCart(i_UserShoppingCart, callback);
    }

    public void UpdateLikedItemsList(HashMap<String, Item> i_UserLikedItemList, UserModel.UpdateLikedItemsListCallback callback)
    {
        m_UserModel.UpdateLikedItemsList(i_UserLikedItemList, callback);
    }

    public void GetUserShoppingCart(UserModel.ShoppingCartCallback callback)
    {
        m_UserModel.GetUserShoppingCart(callback);
    }

    public void GetUserLikedItemsList(UserModel.LikedItemsCallback callback)
    {
        m_UserModel.GetUserLikedItemsList(callback);
    }


}