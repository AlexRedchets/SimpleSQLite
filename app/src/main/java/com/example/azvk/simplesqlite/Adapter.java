package com.example.azvk.simplesqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    //create array of strings to keep Notes
    public List<String> items = new ArrayList<>();
    private Context context;

    public Adapter(Context context) {
        this.context = context;
    }

    public void updateAdapter(List<String> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items!=null ? items.size() : 0;
    }

    @Override
    public String getItem(int i) {
        return items!=null ? items.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(String item){
        items.add(item);
        notifyDataSetChanged();

    }

    public void clearItems(){
        items.clear();
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.rowlayout, parent, false);
            viewHolder.checkedItems = (TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.checkedItems.setText(items.get(position));
        return convertView;
    }

    class ViewHolder{
        public TextView checkedItems;
    }

}
