package com.example.photogalleryapp.base;

public interface BasePresenter<V> {
    void bindView(V view);
    void dropView();
}
