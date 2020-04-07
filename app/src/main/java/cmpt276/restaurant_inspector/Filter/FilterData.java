package cmpt276.restaurant_inspector.Filter;
/*
Save data for filter box
 */

import java.util.ArrayList;
import java.util.List;

public class FilterData {
    private Boolean isFavorite = false;
    private String hazardLevel = "Select one";
    private int numberOfViolationsMoreThan = 0;
    private String searchRestaurantByName = "";
    private List<Integer> restaurantPositionInFilterBox = new ArrayList<>();

    private static FilterData instance;
    public FilterData(){
        //Empty
    }
    public static FilterData getInstance(){
        if(instance == null) {
            instance = new FilterData();
        }
        return instance;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favoriteFilterBox) {
        isFavorite = favoriteFilterBox;
    }

    public String getHazardLevel() {
        return hazardLevel;
    }

    public void setHazardLevel(String hazardLevel) {
        this.hazardLevel = hazardLevel;
    }

    public int getNumberOfViolationsMoreThan() {
        return numberOfViolationsMoreThan;
    }

    public void setNumberOfViolationsMoreThan(int numberOfViolationsMoreThan) {
        this.numberOfViolationsMoreThan = numberOfViolationsMoreThan;
    }

    public String getSearchRestaurantByName() {
        return searchRestaurantByName;
    }
    public void setSearchRestaurantByName(String s) {
        searchRestaurantByName = s.toLowerCase();
    }

    public List<Integer> getRestaurantPositionInFilterBox() {
        return restaurantPositionInFilterBox;
    }

    public void addRestaurantPosition(int position) {
        this.restaurantPositionInFilterBox.add(position);
    }
    public void clearRestaurantPosition() {
        this.restaurantPositionInFilterBox.clear();
    }
}
