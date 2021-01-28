package com.example.shopapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;
    private GroceryItemAdapter newItemsAdapter, popularItemAdapter, suggestedItemsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        initDutNavView();
        initRecViews();
        return view;
    }

    private void initRecViews() {
        newItemsAdapter = new GroceryItemAdapter(getActivity());
        newItemsRecView.setAdapter(newItemsAdapter);
        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemAdapter = new GroceryItemAdapter(getActivity());
        popularItemsRecView.setAdapter(popularItemAdapter);
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        List<GroceryItem> allItems = Utils.getAllItems(getActivity());
        if(null!= allItems){
            newItemsAdapter.setItems(allItems);
        }
    }

    private void initDutNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getActivity(), "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        Toast.makeText(getActivity(), "search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cart:
                        Toast.makeText(getActivity(), "cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void init(View view) {
        bottomNavigationView = view.findViewById(R.id.bottomNavView);
        newItemsRecView = view.findViewById(R.id.newItemsRecView);
        popularItemsRecView = view.findViewById(R.id.popularItemsRecView);
        suggestedItemsRecView = view.findViewById(R.id.suggestedItemsRecView);

    }
}
