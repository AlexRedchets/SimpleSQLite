package com.example.azvk.simplesqlite;

public class Items {

    private int _id;
    private String _itemName;

    public Items(){}

    public Items(String itemName) {
        this._itemName = itemName;
    }

    public int get_id() {
        return _id;
    }

    public String get_itemName() {
        return _itemName;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public void set_itemName(String itemName) {
        this._itemName = itemName;
    }
}
