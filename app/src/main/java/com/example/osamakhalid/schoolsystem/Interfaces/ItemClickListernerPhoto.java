package com.example.osamakhalid.schoolsystem.Interfaces;

import android.view.View;

import com.example.osamakhalid.schoolsystem.Model.GalleryData_Model;

import java.util.List;

/**
 * Created by HAMI on 07/03/2018.
 */

public interface ItemClickListernerPhoto {
    void onClick(View view,String name, List<GalleryData_Model> galleryData_models);

}
