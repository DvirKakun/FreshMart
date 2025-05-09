package com.example.freshmart.Classes;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCart {
    private HashMap<String, Item> m_ShoppingCart;
    private double m_TotalPrice;

    public ShoppingCart()
    {
        m_ShoppingCart = new HashMap<>();
        m_TotalPrice = 0.0;
    }

    public void setShoppingCart(HashMap<String, Item> i_ShoppingCart) {
        this.m_ShoppingCart = i_ShoppingCart;
    }

    public HashMap<String, Item> getShoppingCart() {
        return m_ShoppingCart;
    }

    public double getTotalPrice() {
        return m_TotalPrice;
    }

    public void setTotalPrice(double i_TotalPrice) {
        BigDecimal roundedPrice = BigDecimal.valueOf(i_TotalPrice).setScale(2, RoundingMode.HALF_UP);
        m_TotalPrice = roundedPrice.doubleValue();
        m_TotalPrice = BigDecimal.valueOf(m_TotalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        m_TotalPrice = Math.max(0.0, m_TotalPrice);
    }

    public void AddToCart(Item i_Item)
    {
        Item itemFromShoppingCart = m_ShoppingCart.get(i_Item.getName());
        if(itemFromShoppingCart != null)
        {
            i_Item.setQuantity(itemFromShoppingCart.getQuantity() + 1);
        }
        else
        {
            i_Item.setQuantity(1);
        }
        m_ShoppingCart.put(i_Item.getName(), i_Item);
        BigDecimal roundedPrice = BigDecimal.valueOf(i_Item.getPrice()).setScale(2, RoundingMode.HALF_UP);
//        m_TotalPrice += i_Item.getPrice();
        m_TotalPrice += roundedPrice.doubleValue();
        m_TotalPrice =  BigDecimal.valueOf(m_TotalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void AddToCart(Item i_Item, int i_Quantity)
    {
        Item itemFromShoppingCart = m_ShoppingCart.get(i_Item.getName());
        if(itemFromShoppingCart != null)
        {
            i_Item.setQuantity(itemFromShoppingCart.getQuantity() + i_Quantity);
        }
        else
        {
            i_Item.setQuantity(i_Quantity);
        }
        m_ShoppingCart.put(i_Item.getName(), i_Item);
        BigDecimal roundedPrice = BigDecimal.valueOf((i_Item.getPrice() * i_Quantity)).setScale(2, RoundingMode.HALF_UP);
//        m_TotalPrice += (i_Item.getPrice() * i_Quantity);
        m_TotalPrice += roundedPrice.doubleValue();
        m_TotalPrice = BigDecimal.valueOf(m_TotalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void UpdateItemQuantityOnCart(Item i_Item, int i_Quantity)
    {
        Item itemFromShoppingCart = m_ShoppingCart.get(i_Item.getName());
        double priceUpdate = (i_Quantity - itemFromShoppingCart.getQuantity()) * itemFromShoppingCart.getPrice();
        BigDecimal roundedPriceUpdate = BigDecimal.valueOf(priceUpdate).setScale(2, RoundingMode.HALF_UP);

        i_Item.setQuantity(i_Quantity);
        m_ShoppingCart.put(itemFromShoppingCart.getName(), i_Item);
//        m_TotalPrice += priceUpdate;
        m_TotalPrice += roundedPriceUpdate.doubleValue();
        m_TotalPrice = BigDecimal.valueOf(m_TotalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        m_TotalPrice = Math.max(0.0, m_TotalPrice);
    }

    public List<Item> ToList()
    {
        List<Item> itemList = new ArrayList<>(this.m_ShoppingCart.values());
        return itemList;
    }

    public void Clear(){
        this.m_ShoppingCart.clear();
        this.m_TotalPrice = 0;
    }
}
