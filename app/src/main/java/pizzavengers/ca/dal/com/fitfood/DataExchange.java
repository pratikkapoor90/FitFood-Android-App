package pizzavengers.ca.dal.com.fitfood;

///https://www.youtube.com/watch?v=evkPjPIV6cw///
public class DataExchange {
    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getCalories() {
        return calories;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getUID() {
        return UID;
    }

    public String getGender() { return gender; }

    public String getGoal() {
        return goal;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
    public void setGoal(String goal){this.goal = goal;}
    public void setGender(String gender){this.gender = gender;
    }

    public void setRestaurantName(String restaurantName) {

        this.restaurantName= restaurantName;

    }


    public void setUserdata(int protein,int fat,int calories, String goal){
        this.calories =     calories;
        this.fat      =     fat;
        this.protein  =     protein;
        this.goal     =     goal;

    }

    private int protein, fat, calories;
    private String goal,restaurantName,UID,gender;


    private static DataExchange dataInstance;
    private DataExchange() {

        //SingleInstance ;)

    }
    public static DataExchange getDataInstance(){
        if(dataInstance==null) {
             dataInstance = new DataExchange();
        }
        return dataInstance;
        }


}


