package com.example.osamakhalid.schoolsystem.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osamakhalid.schoolsystem.Adapters.Books_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Book_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;


public class AvailableBooksFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Book_Model> listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_available_books, container, false);
        listItems = new ArrayList<>();
        listItems.add(new Book_Model("Introduction to Physics", "Course: Physics"));
        listItems.add(new Book_Model("Advanced Chemistry", "Course: Chemistry"));
        listItems.add(new Book_Model("Introduction to Algebra", "Course: Maths"));
        listItems.add(new Book_Model("Islamiat", "Course: Islamiat"));
        listItems.add(new Book_Model("Introduction to Calculus", "Course: Maths"));
        listItems.add(new Book_Model("Advanced Geometry", "Course: Maths"));
        recyclerView = view.findViewById(R.id.available_books_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Books_Adapter(listItems);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
