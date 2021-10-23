package com.example.photogalleryapp.model;

import android.content.Context;

import java.util.ArrayList;

public class PhotoRepository implements IPhotoRepository{
    private Context context;
    private ArrayList<Photo> photos = null;

    public PhotoRepository(Context context){
        this.context = context;
        photos = new ArrayList<Photo>();
    }

    public void PhotoCreate(String photoPath){
        Photo newPhoto = new Photo(photoPath);
        photos.add(newPhoto);
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    public String lastPhoto(){
        return photos.get(photos.size() - 1).getPhotoPath();
    }
}
