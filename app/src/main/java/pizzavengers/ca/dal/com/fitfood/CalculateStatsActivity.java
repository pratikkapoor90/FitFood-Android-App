package pizzavengers.ca.dal.com.fitfood;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.os.SystemClock.sleep;


public class CalculateStatsActivity extends AppCompatActivity{

    ///////////////////////////////////////
    private DataExchange dataExchange;
    ///////////////////////////////////////
    private TextView calVal;
    private TextView proteinVal;
    private TextView fatVal;
    private TextView BmiVal;
    private TextView status;
    /*Integer uHeight=170;
    Integer uWeight=54;
    Integer uAge=23;*/

    public Integer uAge, Age;
    public Double Height, Weight, uHeight, uWeight;

    Integer icalVal,iproteinVal,ifatVal,ibmiVal;
    String istatus,uGender;
    FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private DataSnapshot dataSnapshot;
    UserInfo userInfo;
    public String FirstName, LastName;
    static final int REQUEST_LOCATION = 1;
    private static final String TAG = "Stats";
    String UID, UserIdUserIp, UserIdLogin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentCS = getIntent();

        ///////////////////////////////////////////////
        dataExchange = DataExchange.getDataInstance();
        UID = dataExchange.getUID();
        ///////////////////////////////////////////////
        databaseReference = database.getInstance().getReference("UserInformation").child(UID);

        getData();

        /*UID = intentCS.getStringExtra("TESTID");
        if(UID == null){
            UID = intentCS.getStringExtra("TESTIDInput");
        }
        if(UID != null){
            databaseReference = database.getInstance().getReference("UserInformation").child(UID);
            getData();
        }
        else{
            UID = intentCS.getStringExtra("TESTIDLogin");
            databaseReference = database.getInstance().getReference("UserInformation").child(UID);

        }*/

    }
    public void getData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                FirstName = userInfo.getFirstName();
                LastName = userInfo.getLastName();
                Age = userInfo.getuAge();
                Weight = userInfo.getuWeight();
                Height = userInfo.getuHeight();
                Toast.makeText(CalculateStatsActivity.this, "Data Retrieved successfully!", Toast.LENGTH_SHORT).show();

                //Calling Calculations function for calculating Health Stats of user
                calculations();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void calculations(){

        icalVal = checkCalIntake(Height, Weight, Age);
        iproteinVal = checkProteinIntake(Weight);
        ifatVal = checkFatIntake(icalVal);
        ibmiVal = bmiCalc(Weight, Height);
        istatus = checkBmi(ibmiVal);


        //////////////////////////////////////////////////////////////
        dataExchange.setUserdata(iproteinVal,ifatVal,icalVal,istatus);
        //////////////////////////////////////////////////////////////

        //Calling Display function for displaying health Stats of user
        Display();

    }

    public void Display() {

        setContentView(R.layout.activity_display_stats);
        calVal = findViewById(R.id.calVal);
        proteinVal = findViewById(R.id.proteinVal);
        fatVal = findViewById(R.id.fatVal);
        BmiVal = findViewById(R.id.bmiVal);
        status = findViewById(R.id.status);

        calVal.setText(icalVal.toString());
        proteinVal.setText(iproteinVal.toString());
        fatVal.setText(ifatVal.toString());
        BmiVal.setText(ibmiVal.toString());
        status.setText(istatus);

        Button button = findViewById(R.id.search_button);

        bottomNavigation();

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMenuList = new Intent(CalculateStatsActivity.this, RestaurantsData.class);
                *//*Bundle CalcStats = new Bundle();
                CalcStats.putString("calVal",icalVal.toString());
                CalcStats.putString("proteinVal",iproteinVal.toString());
                CalcStats.putString("fatVal", ifatVal.toString());
                intentMenuList.putExtras(CalcStats);*//*

                startActivity(intentMenuList);
                //setContentView(R.layout.restaurant_list);

            }
        });*/
    }

    public void bottomNavigation() {
        BottomNavigationView btmNav = (BottomNavigationView) findViewById(R.id.btm_nav);
        /* btmNav.setOnNavigationItemSelectedListener(navListener);*/
        //BottomNavigationViewHelper.disableShiftMode(btmNav);
        Menu menu = btmNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.view1:

                        break;
                    case R.id.view2:
                        Intent intent2 = new Intent(CalculateStatsActivity.this, RestaurantsData.class);
                        startActivity(intent2);
                        break;
                    case R.id.view3:
                        Intent intent3 = new Intent(CalculateStatsActivity.this, UserProfile.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }


    // Calculating daily calorie intake required based on daily activity level
    public int checkCalIntake(Double height,Double weight, int age) {
        int calVal;
        calVal = Integer.parseInt(String.valueOf((Math.round(10*(weight) + 6.25*(height)- 5*age + 5))));
        /*if(gender=="Male"){
            calVal = Integer.parseInt(String.valueOf(Math.round(10*(weight) + 6.25*(height)- 5*age + 5)));
        }
        else{
            calVal = Integer.parseInt(String.valueOf(Math.round(10* (weight) + 6.25*(height) - 5* age - 161)));
        }*/
        return calVal;

    }

    // Calculating daily protein intake required based on daily activity level
    public int checkProteinIntake(Double uWeight) {
        int proteinVal;
        proteinVal =  Integer.parseInt(String.valueOf(Math.round(1.7* uWeight)));
        return proteinVal;

        /* Pratik:
        if(Activitylvl.equals("More")){
            proteinVal =  String.valueOf(1.7* uWeight);
        }
        else if(Activitylvl.equals("Less")){
            proteinVal =  String.valueOf(0.8* uWeight);
        }
        */

    }

    // Calculating daily fat intake required based on daily activity level
    public int checkFatIntake(int calVal) {
        String fatVal = null;
        fatVal = String.valueOf(0.35* calVal);
        fatVal = String.valueOf(Math.round(Float.parseFloat(fatVal)/9));
        return Integer.parseInt(fatVal);


        /* Pratik:
        if(Activitylvl.equals("More")){
            fatVal = String.valueOf(0.35* (calVal));
            fatVal = String.valueOf(Float.parseFloat(fatVal)/9);
        }
        else if(Activitylvl.equals("Less")){
            fatVal = String.valueOf(0.25* (calVal));
            fatVal = String.valueOf(Float.parseFloat(fatVal)/9);
        }
        */
    }

    //code referred from https://stackoverflow.com/questions/18475971/bmi-calculator-in-java
    public String checkBmi(Integer res) {

        String bmiVal = null;
        if (res <= 18 ) {
            bmiVal = "Underweight";
        } else if (res > 18 && res <= 25) {
            bmiVal = "Normal";
        } else if (res > 25 && res < 30) {
            bmiVal = "Overweight";
        } else if (res == 30 || res > 30) {
            bmiVal = "Obese";
        }
        return bmiVal;
    }

    //Calculating BMI
    public int bmiCalc(double iuWeight, double iuHeight) {
        iuHeight = iuHeight/100;
        iuHeight = iuHeight*iuHeight;
        int res;
        res = Integer.parseInt(String.valueOf(Math.round(iuWeight/iuHeight)));
        return res;
    }

    /*startActivity(intent);*/



}