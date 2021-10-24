package com.example.photogalleryapp.presenters;

import android.app.Activity;

import com.example.photogalleryapp.views.SearchView;

public class SearchPresenterImpl implements SearchPresenter{

    private SearchView view;
    private final Activity context;

    public SearchPresenterImpl(Activity context) {
        this.context = context;
    }

    @Override
    public void bindView(SearchView view) { this.view = view; }

    @Override
    public void dropView() { this.view = null; }

}
