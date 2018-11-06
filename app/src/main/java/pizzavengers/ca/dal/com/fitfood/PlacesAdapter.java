package pizzavengers.ca.dal.com.fitfood;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by LENOVO on 14-03-2018.
 */

public class PlacesAdapter extends ArrayAdapter<RestaurantItem> {

    private ArrayList<RestaurantItem> restaurantList;

    public PlacesAdapter(Context context, int textViewResourceId, ArrayList<RestaurantItem> restaurantList) {
        super(context, textViewResourceId, restaurantList);
        this.restaurantList = restaurantList;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        RestaurantItem i = restaurantList.get(position);

        if (i != null) {
            TextView tvName = v.findViewById(R.id.tvname);

            TextView tvRating = v.findViewById(R.id.tvrating);
            TextView tvVicinity = v.findViewById(R.id.tvvicinity);

            tvName.setText(i.getName());

            tvRating.setText(i.getRating() + "★");
            tvVicinity.setText("Location:" + i.getVicinity());
        }

        return v;
    }
}