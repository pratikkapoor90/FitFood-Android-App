package pizzavengers.ca.dal.com.fitfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuData extends AppCompatActivity {
    String rest_name;
    private ListView fooditems;
    private ArrayList<FoodItem> foodList;
    private FoodAdapter foodAdapter;

    ///////////////////////////////////////
    private DataExchange dataExchange;
    int iprotein,ifats,icalories;
    ///////////////////////////////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentmenu = getIntent();
        rest_name = intentmenu.getStringExtra(RestaurantsData.Rest);

        ///////////////////////////////////////////////

        dataExchange =  DataExchange.getDataInstance();
        rest_name    =  dataExchange.getRestaurantName();
        iprotein      =  dataExchange.getProtein();
        icalories     =  dataExchange.getCalories();
        ifats         =  dataExchange.getFat();

        ///////////////////////////////////////////////


    }
    protected void onStart(){
        super.onStart();
        getFood();
    }
    public void getFood() {
        setContentView(R.layout.activity_menu_list);
        fooditems = findViewById(R.id.fooditems);
        foodList= new ArrayList<>();
        foodAdapter = new FoodAdapter(this, R.layout.activity_menu_item, foodList);
        final String url = "https://api.nutritionix.com/v1_1/search/" + rest_name + "?results=0:60&fields=brand_name,item_name,item_id,nf_calories,nf_protein,nf_total_fat&appId=0640813b&appKey=5fb79a54b24385139a50383edb13c9b2";


        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Toast.makeText(getApplicationContext(), "Success!",
                                Toast.LENGTH_SHORT).show();

                        try {
                            foodList.clear();
                            JSONArray hits = response.getJSONArray("hits");

                            for (int i=0;i<hits.length();i++){

                                JSONObject hit = hits.getJSONObject(i);
                                JSONObject fields = hit.getJSONObject("fields");
                                String proteins = fields.getString("nf_protein").toString();
                                String calories = fields.getString("nf_calories").toString();
                                String fats = fields.getString("nf_total_fat").toString();
                                String name = fields.getString("item_name").toString();

                                if(checkAdd(calories,fats,proteins)==1){
                                foodList.add(new FoodItem(name, calories, fats, proteins));
                                }
                                else{

                                }
                            }
                            fooditems.setAdapter(foodAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private int checkAdd(String calories, String fats, String proteins) {

        //////////////////////////////////////
        String GoalM = dataExchange.getGoal();
        //////////////////////////////////////

        //////////////////////////// Gain ///////////////////////////
        if(GoalM.equals("Gain Weight")){
            if(Integer.parseInt(proteins)>(iprotein/3)-10){

                if(Integer.parseInt(fats)>(ifats/3)-3){
                    return 1;
                }


            }


        }
        //////////////////////////// Maintain ///////////////////////////
        else if(GoalM.equals("Maintain")){
            if(Integer.parseInt(proteins)>(iprotein/3)-10 && Integer.parseInt(proteins)<(iprotein/3)+10){

                if(Integer.parseInt(fats)>(ifats/3)-3 && Integer.parseInt(fats)<(ifats/3)+3){
                    return 1;
                }


            }
        }

        //////////////////////////// Lose //////////////////////////////
        else if(GoalM.equals("Lose Weight")){
            if(Integer.parseInt(proteins)>(iprotein/3)-8 ){

                if(Integer.parseInt(fats)<(ifats/3)+3){
                    return 1;
                }


            }
        }

        return 0;
    }
}



