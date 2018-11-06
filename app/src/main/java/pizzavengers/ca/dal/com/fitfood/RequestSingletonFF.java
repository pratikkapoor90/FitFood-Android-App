package pizzavengers.ca.dal.com.fitfood;

/**
 * Created by Pratik Kapoor on 2018-03-18.
 */

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestSingletonFF {


    private static RequestSingletonFF mInstance;

    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestSingletonFF(Context context) {
        mCtx = context.getApplicationContext();
        mRequestQueue = getRequestQueue();

    }

    public static synchronized RequestSingletonFF getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestSingletonFF(context.getApplicationContext());

        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public  <T> void addToRequestQueue(Request<T> req){ getRequestQueue().add(req);}
}