package pizzavengers.ca.dal.com.fitfood;

// Reference Link: https://www.youtube.com/watch?v=XijS62iP1Xo


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Pratik Kapoor on 2018-03-18.
 */



public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "REG_TOKEN";

    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_token);
    }
}


