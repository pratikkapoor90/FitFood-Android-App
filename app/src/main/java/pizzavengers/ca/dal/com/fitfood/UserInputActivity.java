package pizzavengers.ca.dal.com.fitfood;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.os.SystemClock.sleep;


public class UserInputActivity extends AppCompatActivity {


    //Firebase
    public static final int RC_SIGN_IN = 1;

    Toolbar toolbar;
    String uHeight,uWeight,uAge;
    UserInfo userInfo;
    private Button btnCalc;
    private View view;
    private RadioGroup rdbtn;
    private ProgressBar progressBar;
    private Spinner spinnerActivityLevel;
    String calVal;
    String uName;
    String uLastName, Goal, Gender, uID;
    RadioButton male, female;
    Spinner mySpinner;
    ArrayAdapter<String> myAdapter;
    FirebaseDatabase database;
    private DatabaseReference databaseReference;
    //////////////////////////////////
    private DataExchange dataExchange;
    //////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mFirebaseAuth = FirebaseAuth.getInstance();//....
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_stats);
        //////////////////////////////////
        dataExchange =DataExchange.getDataInstance();
        uID = dataExchange.getUID();
        //////////////////////////////////
        male = (RadioButton)findViewById(R.id.uMale);
        female = (RadioButton) findViewById(R.id.uFMale);
        mySpinner = (Spinner) findViewById(R.id.spinner5);
        myAdapter = new ArrayAdapter<String>(UserInputActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        toolbar = (Toolbar) findViewById(R.id.tb1);
        toolbar.setTitle("FITFOOD");
        progressBar = findViewById(R.id.progress_bar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("SIGNUP");
        Intent intent = getIntent();
        //uID = intent.getStringExtra("userID");


        btnCalc = findViewById(R.id.saveButton);


        btnCalc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                uName = ((EditText)findViewById(R.id.uName_value)).getText().toString();
                uLastName = ((EditText)findViewById(R.id.uLName_value)).getText().toString();

                uHeight = ((EditText)findViewById(R.id.uHeight_value)).getText().toString();
                uWeight = ((EditText)findViewById(R.id.uWeight_value)).getText().toString();
                uAge = ((EditText)findViewById(R.id.uAge_value)).getText().toString();
                Goal = mySpinner.getSelectedItem().toString();
                dataExchange.setGoal(Goal);

                if(male.isChecked()){
                    Gender = "Male";
                }
                else if(female.isChecked()){
                    Gender = "Female";
                }
                dataExchange.setGender(Gender);

                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("UserInformation");
                userInfo = new UserInfo();
                setValue();


            }
        });
    }
    public void setValue(){

        userInfo.setFirstName(uName);
        userInfo.setLastName(uLastName);
        userInfo.setuAge(Integer.parseInt(uAge));
        userInfo.setuGender(Gender);
        userInfo.setuGoal(Goal);
        userInfo.setuHeight(Double.parseDouble(uHeight));
        userInfo.setuWeight(Double.parseDouble(uWeight));
        insertData();
        /*userInfo.setGoal(Goal);
        userInfo.setGender(Gender);*/

    }
    public void insertData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String UserId = uID;
                databaseReference.child(UserId).setValue(userInfo);
                Toast.makeText(UserInputActivity.this, "Data Inserted successfully!", Toast.LENGTH_LONG).show();
                Intent intentCS = new Intent(UserInputActivity.this, CalculateStatsActivity.class);
                intentCS.putExtra("TESTIDInput", uID);
                startActivity(intentCS);


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent  = new Intent (UserInputActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(UserInputActivity.this,0, intent,PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(UserInputActivity.this);
                notificationBuilder.setContentTitle("YOUR HEALTH STATS");
                notificationBuilder.setContentText("Check your health stats here!!");
                notificationBuilder.setAutoCancel(true);
                notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
                notificationBuilder.setContentIntent(pendingIntent);

                notificationManager.notify(0,notificationBuilder.build());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}