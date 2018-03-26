package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.GalleryData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 07/03/2018.
 */

public class GalleryImages_Adapter extends RecyclerView.Adapter<GalleryImages_Adapter.ViewHolder> {
    Context context;
    List<GalleryData_Model> galleryData_models;

    public GalleryImages_Adapter( List<GalleryData_Model> galleryData_models,Context context) {
        this.context = context;
        this.galleryData_models = galleryData_models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_folder_images_layout,parent,false);
        return new GalleryImages_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GalleryData_Model galleryData = galleryData_models.get(position);

        if(galleryData != null){
            holder.image_name.setText(galleryData.getFileNameDisplay());
            Glide.with(context).load(Values.image_path + galleryData.getFileName()).into(holder.imageView);
        }else{

        }

    }

    @Override
    public int getItemCount() {
        if(galleryData_models!= null){
            return galleryData_models.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView image_name;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.data_images);
            image_name = itemView.findViewById(R.id.user_image_name);

        }
    }
}
