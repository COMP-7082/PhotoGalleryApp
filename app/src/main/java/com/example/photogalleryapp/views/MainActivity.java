package com.example.photogalleryapp.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photogalleryapp.R;
import com.example.photogalleryapp.presenters.MainPresenter;
import com.example.photogalleryapp.presenters.MainPresenterImpl;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements MainView {

    static final int SEARCH_ACTIVITY_REQUEST_CODE = 2;
    private TextView tvCity;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.text_city);
        presenter = new MainPresenterImpl(this); //Adding presenter
        presenter.bindView(this);
        presenter.fusedLocationClient();
        presenter.findPhotos(new Date(Long.MIN_VALUE), new Date(), "", "");
    }

    public void getLocation(View v){ tvCity.setText(presenter.getLocation()); }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onReturn(requestCode, resultCode, data);
    }

    public void takePhoto(View v){
        presenter.takePhoto();
    }

    @Override
    public void displayPhoto(String path) {
        ImageView iv = findViewById(R.id.ivGallery);
        TextView tv = findViewById(R.id.tvTimestamp);
        EditText et = findViewById(R.id.etCaption);

        if(path == null || path.equals("")){
            iv.setImageResource(R.mipmap.ic_launcher);
            et.setText("");
            tv.setText("");
        } else {
            iv.setImageBitmap(BitmapFactory.decodeFile(path));
            String[] attr = path.split("_");
            et.setText(attr[1]);
            tv.setText("Date: " + attr[2] + "  Time: " + attr[3] + "\nCity: " + attr[4]);
        }
    }

    public void scrollPhotos(View v) {
        presenter.updatePhoto(((EditText) findViewById(R.id.etCaption)).getText().toString());
        switch (v.getId()) {
            case R.id.btnPrev:
                presenter.handleNavigationInput("ScrollLeft", "caption");
                break;
            case R.id.btnNext:
                presenter.handleNavigationInput("ScrollRight", "caption");
                break;
            default:
                break;
        }
    }

    public void searchButton(View v){
        startActivityForResult(presenter.search(), SEARCH_ACTIVITY_REQUEST_CODE);
    }

    public void shareButton(View v) {
        startActivity(Intent.createChooser(presenter.sharePhoto(), "Share to"));
    }

}