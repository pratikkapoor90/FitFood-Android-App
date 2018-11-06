package pizzavengers.ca.dal.com.fitfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lakshmimaligireddy on 2018-04-04.
 */

public class Logout extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.getInstance().signOut();
        Intent intentlogout = new Intent(Logout.this, MainActivity.class);
        startActivity(intentlogout);
    }
}
