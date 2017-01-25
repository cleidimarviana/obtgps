package me.acklay;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cleidimar on 1/25/2017.
 */

public class LocationManagerActivity extends AppCompatActivity {

    ObtainGPS gps;
    Button btLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);

        btLocalizacao = (Button) findViewById(R.id.btn);

        btLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalization();
            }
        });
    }

    public void getLocalization() {
        gps = new ObtainGPS(this);


        if (Utils.getLocalization(this)) {
            // check if GPS enabled
            if (gps.canGetLocation()) {

                AlertDialog erroLocation = new AlertDialog.Builder(this).create();
                erroLocation.setTitle("Localização");
                erroLocation.setMessage("Lat:" + gps.getLatitude() + " Lng:" + gps.getLongitude());
                erroLocation.show();

            } else {

                AlertDialog erroLocation = new AlertDialog.Builder(this).create();
                erroLocation.setTitle("Localização não encontrada");
                erroLocation.setMessage("Sua Localização não foi encontrada!! Tente novamente!");
                erroLocation.show();
                Utils.showSettingsAlert(this);
            }

        }
    }

}