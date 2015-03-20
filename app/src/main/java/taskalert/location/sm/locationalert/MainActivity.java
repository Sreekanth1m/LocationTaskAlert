package taskalert.location.sm.locationalert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,
        LocationSource.OnLocationChangedListener,LocationListener, View.OnClickListener

   {

    GoogleMap googleMap;
    UiSettings uiSettings;
    Button bListAlarm, bAddAlarm, bExit, bHelp;// bSettings, bSearch,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        bListAlarm = (Button)findViewById(R.id.bListAlarm);
        bAddAlarm = (Button)findViewById(R.id.bAddAlarm);
        bExit = (Button) findViewById(R.id.bExit);
        bHelp = (Button)findViewById(R.id.bHelp);
        bListAlarm.setOnClickListener(this);
        bAddAlarm.setOnClickListener(this);
        bExit.setOnClickListener(this);
       /* bSearch.setOnClickListener(this);
        bSettings.setOnClickListener(this);*/
        bHelp.setOnClickListener(this);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("A"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Current Latitude:" + latitude + ", Longitude:" + longitude);

    }
       @Override
       public void onClick(View v) {

           switch(v.getId())
           {
               case R.id.bListAlarm:
                   Intent listAlarm = new Intent(this,ListAlarm.class);
                   startActivity(listAlarm);
                   break;
               case R.id.bAddAlarm:
                   Intent addAlarm = new Intent(this,AddAlarm.class);
                   addAlarm.putExtra("CLASS_NAME","MainActivity");
                   startActivity(addAlarm);
                   break;
               case R.id.bHelp:
                   Intent helpAlarm = new Intent(this,Help.class);
                   startActivity(helpAlarm);
                   break;
               case R.id.bExit:
                /*  final Dialog exitDialog = new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog);
                    exitDialog.setContentView(R.layout.exit_dialog);
                   //exitDialog.setTitle("Exit Dialog");
                   ImageView imageView = (ImageView)exitDialog.findViewById(R.id.ivExitDialog);
                   imageView.setImageResource(R.drawable.ic_launcher);
                   TextView bedTitle = (TextView)exitDialog.findViewById(R.id.bedtitle);
                   bedTitle.setText("Exit Dialog");
                   TextView bedTextView = (TextView)exitDialog.findViewById(R.id.bedTextView);
                   bedTextView.setText("Do You Really Want to Exit? \n If Exit, All Alarms will be Disabled! \n Instead You can Hide the App \n ");
                   Button bedCancel, bedHide, bedExit;
                   bedCancel = (Button)exitDialog.findViewById(R.id.bedCancel);
                   bedHide = (Button)exitDialog.findViewById(R.id.bedHide);
                   bedExit = (Button)exitDialog.findViewById(R.id.bedExit);
                   bedCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            exitDialog.cancel();
                        }
                    });
                   bedHide.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           exitDialog.hide();
                       }
                    });
                   bedExit.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           exitDialog.dismiss();
                       }
                   });*/
                   final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
                   exitDialog.setTitle("Exit Dialog \n_______________________________");
                   exitDialog.setMessage("Do You Really Want to Exit? \nIf Exit, All Alarms will be Disabled! \nInstead You can Hide the App");
                   exitDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"Exit", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
             //              System.exit(0);
                       }
                   });
                   exitDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Hide             ",new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   moveTaskToBack(true);
                                   exitDialog.hide();
                               }
                           }
                   );

                   exitDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Cancel  ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exitDialog.cancel();
                                                    }
                       });

           //         });

                   exitDialog.show();
                  // exitDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                  // exitDialog.getWindow().setGravity(Gravity.CENTER);
                   break;
               /*case R.id.bListAlarm:
                   Intent listAlarm = new Intent(this,ListAlarm.class);
                   startActivity(listAlarm);
                   break;
               case R.id.bListAlarm:
                   Intent listAlarm = new Intent(this,ListAlarm.class);
                   startActivity(listAlarm);
                   break;*/
           }
       }
    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


   }
