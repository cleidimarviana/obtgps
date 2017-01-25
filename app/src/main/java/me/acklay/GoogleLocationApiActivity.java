package me.acklay;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleLocationApiActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mapGoogleApiClient;
    Button btLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_location_api);

        btLocalizacao = (Button) findViewById(R.id.btn);

        if (mapGoogleApiClient == null) {
            mapGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        btLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getLocalization(GoogleLocationApiActivity.this)) {
                    if (ActivityCompat.checkSelfPermission(GoogleLocationApiActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(GoogleLocationApiActivity.this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mapGoogleApiClient);
                    if (location != null) {
                        Toast.makeText(GoogleLocationApiActivity.this, "lat: " + location.getLatitude() + " log: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    } else {
                        Utils.showSettingsAlert(GoogleLocationApiActivity.this);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapGoogleApiClient.isConnected()) {
            mapGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}