package pizzavengers.ca.dal.com.fitfood;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Pratik Kapoor on 2018-03-27.
 */


public class FoodAdapter extends ArrayAdapter<FoodItem> {

    private ArrayList<FoodItem> foodList;

    public FoodAdapter(@NonNull Context context, int textViewResourseId, ArrayList<FoodItem> foodList) {
        super(context,textViewResourseId,foodList);
        this.foodList = foodList;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v= convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.activity_menu_item,null);
        }

        FoodItem i = foodList.get(position);

        if(i != null){
            TextView tvname = v.findViewById(R.id.tvname);
            TextView tvcalories = v.findViewById(R.id.tvcalories);
            TextView tvproteins = v.findViewById(R.id.tvproteins);
            TextView tvfats = v.findViewById(R.id.tvfats);

            tvname.setText(i.getFood_Name());
            tvcalories.setText("Calories: " + i.getCalories());
            tvproteins.setText("Proteins: " + i.getProteins());
            tvfats.setText("Fats: " + i.getFats());

        }
        return v;
    }
}
