package pizzavengers.ca.dal.com.fitfood;


/**
 * Created by Pratik Kapoor on 2018-03-27.
 */


public class FoodItem {
    private String name;
    private String calories;
    private String fats;
    private String proteins;

    public FoodItem(String name, String calories, String fats, String proteins){
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.proteins = proteins;

    }
    public String getFood_Name(){return this.name;}
    public String getCalories() {return this.calories;}
    public String getFats() {return this.fats;}
    public String getProteins() {return this.proteins;}

}
