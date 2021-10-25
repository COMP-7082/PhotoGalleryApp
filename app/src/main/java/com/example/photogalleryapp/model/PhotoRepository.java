package com.example.photogalleryapp.model;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Photo> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String locate) {
        photos = new ArrayList<Photo>();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.photogalleryapp/files/Pictures");
        File[] fList = file.listFiles();

        if(fList != null){
            Stream<File> fileStream = Arrays.stream(fList);
            fileStream.filter(f -> {
                return (startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime());
            }).filter(f -> {
                return f.lastModified() <= endTimestamp.getTime();
            }).filter(f -> {
                return (keywords.equals("") || f.getPath().contains(keywords));
            }).filter(f -> {
                return (locate.equals("") || f.getPath().contains(locate));
            }).forEach(f -> {
                PhotoCreate(f.getPath());
            });
            return photos;
        } else {
            return null;
        }
    }

//    public ArrayList<Photo> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String locate) {
//        photos = new ArrayList<Photo>();
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.photogalleryapp/files/Pictures");
//        File[] fList = file.listFiles();
//        Stream<File> fileStream = file.listFiles().stream();
//
//        if(fList != null){
//            for (File f : fList) {
//                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime()
//                        && f.lastModified() <= endTimestamp.getTime()))
//                        && (keywords.equals("") || f.getPath().contains(keywords))
//                        && (locate.equals("") || f.getPath().contains(locate))) {
//                    PhotoCreate(f.getPath());
//                }
//            }
//            return photos;
//        } else {
//            return null;
//        }
//    }

}
