package com.example.photogalleryapp.model;

import java.io.File;

public class Photo {

    private String photoPath;

    public Photo(String photoPath) { this.photoPath = photoPath; }

    public String getPhotoPath(){ return photoPath; }

    public void setPhotoPath(String photoPath){ this.photoPath = photoPath; }

    public void updateCaption(String photoPath, String caption){
        String[] attr = photoPath.split("_");
        if (attr.length >= 3) {
            File to = new File(attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + "_" + attr[4] + "_" + ".jpeg");
            File from = new File(photoPath);
            from.renameTo(to);
        }
    }
}
