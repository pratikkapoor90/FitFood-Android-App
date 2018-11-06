package pizzavengers.ca.dal.com.fitfood;

//Code Reference: https://github.com/dhivya-jayaraman/Firebase_Workshop

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button  signOutButton;
    private TextView helloUserText;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String UserId;
    private DataExchange dataExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        signOutButton = (Button) findViewById(R.id.sign_out);
        helloUserText = (TextView) findViewById(R.id.text_user);
        ///////////////////////////////////////////////
        dataExchange = DataExchange.getDataInstance();

        ///////////////////////////////////////////////


//        Firebase AuthListener instance
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Hi " + user.getEmail() + "." , Toast.LENGTH_SHORT ).show();
                    UserId = user.getUid();
                    //////////////////////////////
                    dataExchange.setUID(UserId);
                    //////////////////////////////
                    Intent intent1 = new Intent(MainActivity.this, CalculateStatsActivity.class);
                    //intent1.putExtra("TESTID",UserId);
                    startActivity(intent1);
                    finish();
                }
            }
        };

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutButton();
            }
        });
    }
    //sign out method
    public void signOutButton() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}