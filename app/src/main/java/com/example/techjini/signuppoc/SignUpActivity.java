package com.example.techjini.signuppoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.techjini.signuppoc.fragment.StoreInformationFragment;
import com.example.techjini.signuppoc.util.Constant;
import com.example.techjini.signuppoc.util.LocationTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class SignUpActivity extends AppCompatActivity {
    public static final int LOCATION_REQUEST = 400;
    private String mAddress;
    private double mLongitude;
    private double mLatitude;
    private EditText mEmailEdit;
    private boolean isPrimarySelected;
    private String primaryEmail;
    private StoreInformationFragment mfragment;
    private GPSTracker mGpsTracker;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationTracker mLocationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mGpsTracker = new GPSTracker(this);
        // startActivityForResult(new Intent(this, FindLocation.class), LOCATION_REQUEST);


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();

      /*  if (mfragment == null && mGpsTracker.canGetLocation) {
            addFragment(true);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCATION_REQUEST:

                mLatitude = data.getDoubleExtra("LATITUDE", 0);
                mLongitude = data.getDoubleExtra("LONGITUDE", 0);
                mAddress = data.getStringExtra("Address");
                break;
            case Constant.IntentFlag.REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    mLocationTracker = new LocationTracker(this);
                    addFragment(false);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            switch (requestCode) {
                case Constant.Permission.COARSE_LOCATION_PERMISSION_REQUEST_CODE:
                case Constant.Permission.FINE_LOCATION_PERMISSION_REQUEST_CODE:

                    break;
            }

        }
    }

    /**
     * Method to verify google play services on the device
     */
    private void checkPlayServices() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(this);
        if (code == ConnectionResult.SUCCESS) {
            onActivityResult(Constant.IntentFlag.REQUEST_GOOGLE_PLAY_SERVICES, Activity.RESULT_OK, null);
        } else if (api.isUserResolvableError(code) && api.showErrorDialogFragment(this, code, Constant.IntentFlag.REQUEST_GOOGLE_PLAY_SERVICES)) {
            // wait for onActivityResult call (see below)
        } else {
            Toast.makeText(this, api.getErrorString(code), Toast.LENGTH_LONG).show();
        }
    }

    private void showDialogToSettings() {
        // Build the alert dialog
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Services Not Active");
        builder.setMessage("Please enable Location Services and GPS");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });

        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();*/
        // Check the location settings of the user and create the callback to react to the different possibilities
       /* mLocationRequest = new LocationRequest();
        LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequestBuilder.build());
        result.setResultCallback(mResultCallbackFromSettings);*/


    }


    private void addFragment(boolean isDelayed) {

        mfragment = StoreInformationFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.layout, mfragment).commit();
    }

}
