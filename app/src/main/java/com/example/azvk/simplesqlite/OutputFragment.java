package com.example.azvk.simplesqlite;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class OutputFragment extends Fragment {

    DBHandler dbHandler;
    Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_output, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) getActivity().findViewById(R.id.listView);
        adapter = new Adapter(getContext());
        listView.setAdapter(adapter);

        printDatabase();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler(getContext(), null, null, 1);
    }

    private void printDatabase() {
        List<String> dbString = dbHandler.databaseToString();
        adapter.updateAdapter(dbString);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Subscribe
    public void onEvent(EventList eventList){
        if(eventList.getResultCode()==100){
            adapter.addItem(eventList.getItem());
        }
        else if (eventList.getResultCode()==200) {
            int index = adapter.items.indexOf(eventList.getItem());
            if(index > -1){
                adapter.deleteItem(index);
            }
        }
        else if(eventList.getResultCode()==300){
            adapter.clearItems();
        }
    }
}
