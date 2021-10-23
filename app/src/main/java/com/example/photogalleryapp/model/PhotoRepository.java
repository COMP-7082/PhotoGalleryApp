package com.example.photogalleryapp.model;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PhotoRepository implements IPhotoRepository{
    private Context context;
    private ArrayList<Photo> photos = null;

    public PhotoRepository(Context context){
        this.context = context;
        photos = new ArrayList<>();
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

    public ArrayList<Photo> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String locate) {
        photos = new ArrayList<Photo>();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.photogalleryapp/files/Pictures");
        File[] fList = file.listFiles();
        if(fList != null){
            for (File f : fList) {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime()
                        && f.lastModified() <= endTimestamp.getTime()))
                        && (keywords.equals("") || f.getPath().contains(keywords))
                        && (locate.equals("") || f.getPath().contains(locate))) {
                    PhotoCreate(f.getPath());
                }
            }
            return photos;
        } else {
            return null;
        }
    }

}
