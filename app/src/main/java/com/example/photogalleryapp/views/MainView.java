package com.example.photogalleryapp.views;

import com.example.photogalleryapp.base.BaseView;
import com.example.photogalleryapp.presenters.MainPresenter;

public interface MainView extends BaseView<MainPresenter> {
    void displayPhoto(String path);
}
