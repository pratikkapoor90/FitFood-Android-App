package pizzavengers.ca.dal.com.fitfood;

/**
 * Created by Pratik Kapoor on 2018-03-18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("nf_calories")
    @Expose
    private Integer nfCalories;
    @SerializedName("nf_total_fat")
    @Expose
    private Integer nfTotalFat;
    @SerializedName("nf_protein")
    @Expose
    private Integer nfProtein;
    @SerializedName("nf_serving_size_qty")
    @Expose
    private Integer nfServingSizeQty;
    @SerializedName("nf_serving_size_unit")
    @Expose
    private String nfServingSizeUnit;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getNfCalories() {
        return nfCalories;
    }

    public void setNfCalories(Integer nfCalories) {
        this.nfCalories = nfCalories;
    }

    public Integer getNfTotalFat() {
        return nfTotalFat;
    }

    public void setNfTotalFat(Integer nfTotalFat) {
        this.nfTotalFat = nfTotalFat;
    }

    public Integer getNfProtein() {
        return nfProtein;
    }

    public void setNfProtein(Integer nfProtein) {
        this.nfProtein = nfProtein;
    }

    public Integer getNfServingSizeQty() {
        return nfServingSizeQty;
    }

    public void setNfServingSizeQty(Integer nfServingSizeQty) {
        this.nfServingSizeQty = nfServingSizeQty;
    }

    public String getNfServingSizeUnit() {
        return nfServingSizeUnit;
    }

    public void setNfServingSizeUnit(String nfServingSizeUnit) {
        this.nfServingSizeUnit = nfServingSizeUnit;
    }

}