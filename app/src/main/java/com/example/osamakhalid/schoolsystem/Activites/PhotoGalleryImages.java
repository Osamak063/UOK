package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.GalleryImages_Adapter;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.GalleryData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class PhotoGalleryImages extends AppCompatActivity {

    List<GalleryData_Model> galleryData_models;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_images);

        galleryData_models = this.getIntent().getParcelableArrayListExtra("Data");

        if(galleryData_models != null ){
            recyclerView = findViewById(R.id.gallery_recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(PhotoGalleryImages.this));
            adapter = new GalleryImages_Adapter(galleryData_models,PhotoGalleryImages.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(PhotoGalleryImages.this, Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

        }



    }





}
