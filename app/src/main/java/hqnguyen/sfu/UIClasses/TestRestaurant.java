package hqnguyen.sfu.UIClasses;

public class TestRestaurant {
    private int imageViewRestaurantIcon;
    private int imageViewHazardLevel;
    private int numberIssueFound;
    private String dateFromNow;
    private String restaurantName;

    public TestRestaurant(int imageViewRestaurantIcon, int imageViewHazardLevel, int numberIssueFound, String dateFromNow, String restaurantName) {
        this.imageViewRestaurantIcon = imageViewRestaurantIcon;
        this.imageViewHazardLevel = imageViewHazardLevel;
        this.numberIssueFound = numberIssueFound;
        this.dateFromNow = dateFromNow;
        this.restaurantName = restaurantName;
    }

    public int getImageViewRestaurantIcon() {
        return imageViewRestaurantIcon;
    }

    public int getImageViewHazardLevel() {
        return imageViewHazardLevel;
    }

    public int getNumberIssueFound() {
        return numberIssueFound;
    }

    public String getDateFromNow() {
        return dateFromNow;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
