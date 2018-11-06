package pizzavengers.ca.dal.com.fitfood;

/**
 * Created by LENOVO on 18-03-2018.
 */

public class RestaurantItem {
    private String name;
    private String opennow;
    private String rating;
    private String vicinity;

    public RestaurantItem(String name, String opennow, String rating, String vicinity){
        this.name = name;
        this.opennow = opennow;
        this.rating = rating;
        this.vicinity = vicinity;
    }

    public String getName() {return this.name;}
    public String getOpennow() {return this.opennow;}
    public String getRating() {return  this.rating;}
    public String getVicinity() {return this.vicinity;}
}