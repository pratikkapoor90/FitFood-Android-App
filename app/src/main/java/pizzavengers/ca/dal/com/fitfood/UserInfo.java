package pizzavengers.ca.dal.com.fitfood;

import com.firebase.ui.auth.data.model.User;

/**
 * Created by Pratik Kapoor on 2018-03-30.
 */

// Reference Link: https://www.youtube.com/watch?v=oyifGv42pWE&t=573s

public class UserInfo {

    public String firstName;
    public String lastName;
    public String uGender;
    public String uGoal;
    public Double uWeight, uHeight;
    public Integer uAge;

    public UserInfo() {

    }

    public UserInfo(String firstName, String lastName, String uGender, String uGoal,  Integer uAge, Double uWeight, Double uHeight){

        this.firstName = firstName;
        this.lastName = lastName;
        this.uGender = uGender;
        this.uGoal = uGoal;
        this.uAge = uAge;
        this.uWeight = uWeight;
        this.uHeight = uHeight;
        /**/

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getuGender() {
        return uGender;
    }

    public void setuGender(String uGender) {
        this.uGender = uGender;
    }

    public String getuGoal() {
        return uGoal;
    }

    public void setuGoal(String uGoal) {
        this.uGoal = uGoal;
    }

    public Double getuWeight() {
        return uWeight;
    }

    public void setuWeight(Double uWeight) {
        this.uWeight = uWeight;
    }

    public Double getuHeight() {
        return uHeight;
    }

    public void setuHeight(Double uHeight) {
        this.uHeight = uHeight;
    }

    public Integer getuAge() {
        return uAge;
    }

    public void setuAge(Integer uAge) {
        this.uAge = uAge;
    }


}