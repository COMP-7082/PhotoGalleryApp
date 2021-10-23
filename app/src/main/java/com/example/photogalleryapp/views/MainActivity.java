package com.example.photogalleryapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.photogalleryapp.GpsTracker;
import com.example.photogalleryapp.R;
import com.example.photogalleryapp.presenters.MainPresenter;
import com.example.photogalleryapp.presenters.MainPresenterImpl;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainView {
    static final int SEARCH_ACTIVITY_REQUEST_CODE = 2;
    String cityName = null;
    private ArrayList<String> photos = null;
    private int index = 0;
    private TextView tvCity;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this); //Adding presenter
        presenter.bindView(this);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

        tvCity = findViewById(R.id.text_city);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        presenter.findPhotos(new Date(Long.MIN_VALUE), new Date(), "", "");
    }

    public void getLocation(View view){
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;

            try {
                addresses = gcd.getFromLocation(gpsTracker.getLatitude(),
                        gpsTracker.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            tvCity.setText(cityName);
        }else{
            gpsTracker.showSettingsAlert();
        }
    } // Getting Data

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