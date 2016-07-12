package com.example.azvk.simplesqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class InputFragment extends Fragment {

    private static EditText itemInput;
    private static Button addItem;
    private static Button deleteItem;
    private static Button deleteAllItem;

    private DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHandler = new DBHandler(getContext(), null, null, 1);


        itemInput = (EditText) getActivity().findViewById(R.id.inputText);
        addItem = (Button) getActivity().findViewById(R.id.addButton);
        deleteItem = (Button) getActivity().findViewById(R.id.deleteButton);
        deleteAllItem = (Button) getActivity().findViewById(R.id.deleteAllButton);

        dbHandler = new DBHandler(getContext(), null, null, 1);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonClicked();
            }
        });
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonClicked();
            }
        });
        deleteAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllItems();
            }
        });
    }

    //add item to the database
    private void addButtonClicked() {

        if(itemInput.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            Items items = new Items(itemInput.getText().toString());
            dbHandler.addNote(items);
            EventList eventList = new EventList();
            eventList.setResultCode(100);
            eventList.setItem(items.get_itemName());
            EventBus.getDefault().post(eventList);
            itemInput.setText("");

            Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();}

    }

    //delete item from the database
    private void deleteButtonClicked() {
        String item = itemInput.getText().toString();
        if(dbHandler.deleteNote(item)){
            EventList eventList = new EventList();
            eventList.setResultCode(200);
            eventList.setItem(item);
            EventBus.getDefault().post(eventList);
            itemInput.setText("");

            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "No notes to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAllItems(){
        new AlertDialog.Builder(getContext())
                .setTitle("")
                .setMessage("Are you sure")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteAllItems();
                        EventList eventList = new EventList();
                        eventList.setResultCode(300);
                        EventBus.getDefault().post(eventList);

                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
