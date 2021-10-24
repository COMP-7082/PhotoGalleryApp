package com.example.photogalleryapp.model;

import java.util.ArrayList;
import java.util.Date;

public interface IPhotoRepository {
    ArrayList<Photo> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String locate);
}
