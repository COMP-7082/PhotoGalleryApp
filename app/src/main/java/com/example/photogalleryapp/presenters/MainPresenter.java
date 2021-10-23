package com.example.photogalleryapp.presenters;

import android.content.Intent;

import com.example.photogalleryapp.base.BasePresenter;
import com.example.photogalleryapp.views.MainView;

import java.util.Date;

public interface MainPresenter extends BasePresenter<MainView> {
    void takePhoto();
    void findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String locate);
    void handleNavigationInput(String navigationAction, String caption);
    void onReturn(int requestCode, int resultCode, Intent data);
    Intent sharePhoto();
    Intent search();
    void updatePhoto(String caption);
    void fusedLocationClient();
    String getLocation();
}
