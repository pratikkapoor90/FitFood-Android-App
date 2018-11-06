package pizzavengers.ca.dal.com.fitfood;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//import static pizzavengers.ca.dal.com.fitfood.CalculateStatsActivity.REQUEST_LOCATION;

public class RestaurantsData extends AppCompatActivity {

    private ListView fooditems;
    private ArrayList<FoodItem> foodList;
    private FoodAdapter foodAdapter;
    public double longitude;
    public double latitude;
    private ArrayList<RestaurantItem> restaurantsList;
    ListView restaurants;
    PlacesAdapter adapter;
    private static final String TAG = "Restaurant List";
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String rest_name;
    Runnable runnable;
    public static final String Rest = "RestaurantName";

    //////////////////////////////////
    private DataExchange dataExchange;
    //////////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentML = getIntent();
        setContentView(R.layout.restaurant_list);

        //////////////////////////////////////////////
        dataExchange = DataExchange.getDataInstance();
        //////////////////////////////////////////////

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.restaurant_list);
        getRestaurants();

    }
    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            else{
                Toast.makeText(getApplicationContext(), "Unable to get location !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void getRestaurants(){
        setContentView(R.layout.restaurant_list);
        restaurantsList = new ArrayList<>();
        adapter = new PlacesAdapter(this, R.layout.list_item, restaurantsList);
        restaurants = findViewById(R.id.restaurants);
        restaurants.setAdapter(adapter);

       /* lati.setText(String.valueOf(latitude));
        longit.setText(String.valueOf(longitude));*/

        final String url ="https://maps.googleapis.com/maps/api/place/search/json?location="+latitude+","+longitude+"&radius=2000&sensor=true&keyword=burgers&type=fastfood&price_level=1&key=AIzaSyBzfzsqxMoL9EhpTlQ45c4wjkoIvF2mb1Y";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();

                try {
                    restaurantsList.clear();
                    JSONArray Array = response.getJSONArray("results");


                    for (int i = 0; i < Array.length(); i++) {
                        String name = Array.getJSONObject(i).getString("name").toString();
                        String opennow = Array.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").toString();
                        String rating = Array.getJSONObject(i).getString("rating").toString();
                        String vicinity = Array.getJSONObject(i).getString("vicinity").toString();

                        restaurantsList.add(new RestaurantItem(name, opennow, rating, vicinity));
                    }
                    adapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(getApplicationContext(), "Error retrieving data", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        restaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d(TAG, "onCreate: name: " + restaurantsList.get(i));
                rest_name = restaurantsList.get(i).getName();

                ///////////////////////////////////////////////
                dataExchange.setRestaurantName(rest_name);
                ///////////////////////////////////////////////

                Toast.makeText(getApplicationContext(), "You Clicked on :" + restaurantsList.get(i).getName(), Toast.LENGTH_SHORT).show();
                Intent intentML = new Intent(RestaurantsData.this, MenuData.class);
                intentML.putExtra(Rest, rest_name);
                startActivity(intentML);
            }
        });
        bottomNavigation();
    }

    public void bottomNavigation() {
        BottomNavigationView btmNav = (BottomNavigationView) findViewById(R.id.btm_nav);
        /* btmNav.setOnNavigationItemSelectedListener(navListener);*/
        //BottomNavigationViewHelper.disableShiftMode(btmNav);

        Menu menu = btmNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.view1:
                        Intent intent1 = new Intent(RestaurantsData.this, CalculateStatsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.view2:

                        break;
                    case R.id.view3:
                        Intent intent3 = new Intent(RestaurantsData.this, UserProfile.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }

}
