package com.example.osamakhalid.schoolsystem.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osamakhalid.schoolsystem.Adapters.Books_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Book_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;


public class TotalBooksFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Book_Model> listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_total_books, container, false);
        listItems = new ArrayList<>();
        listItems.add(new Book_Model("Introduction to Physics", "Course: Physics"));
        listItems.add(new Book_Model("Advanced Physics", "Course: Physics"));
        listItems.add(new Book_Model("Introduction to Chemistry", "Course: Chemistry"));
        listItems.add(new Book_Model("Advanced Chemistry", "Course: Chemistry"));
        listItems.add(new Book_Model("Introduction to Algebra", "Course: Maths"));
        listItems.add(new Book_Model("Islamiat", "Course: Islamiat"));
        listItems.add(new Book_Model("Urdu", "Course:Urdu"));
        listItems.add(new Book_Model("Introduction to Calculus", "Course: Maths"));
        listItems.add(new Book_Model("Advanced Calculus", "Course: Maths"));
        listItems.add(new Book_Model("Introduction to Geometry", "Course: Maths"));
        recyclerView = view.findViewById(R.id.total_books_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Books_Adapter(listItems);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
