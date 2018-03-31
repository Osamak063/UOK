package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListernerPhoto;
import com.example.osamakhalid.schoolsystem.Model.GalleryImage_Data;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 07/03/2018.
 */

public class GalleryFolder_Adapter extends RecyclerView.Adapter<GalleryFolder_Adapter.ViewHolder>{

    Context context;
    public List<GalleryImage_Data> galleryResponse_models;
    public ItemClickListernerPhoto itemClickListener;

    public GalleryFolder_Adapter( List<GalleryImage_Data> galleryResponse_models,Context context) {
        this.context = context;
        this.galleryResponse_models = galleryResponse_models;
    }

    @Override
    public GalleryFolder_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_folder_view_layout, parent, false);
        return new GalleryFolder_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryFolder_Adapter.ViewHolder holder, int position) {

        GalleryImage_Data galleryResponseModela = galleryResponse_models.get(position);
        holder.folder_name.setText(galleryResponseModela.getFolderName());

    }
    public void setItemClickListener(ItemClickListernerPhoto itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        if(galleryResponse_models != null){
            return galleryResponse_models.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView folder_name;
        public ViewHolder(View itemView) {
            super(itemView);

            folder_name = itemView.findViewById(R.id.folder_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                String name = galleryResponse_models.get(getAdapterPosition()).getFolderName();
                GalleryImage_Data galleryImage_data = galleryResponse_models.get(getAdapterPosition());
                itemClickListener.onClick(view,name,galleryImage_data.getImages());
            }
        }
    }


}
