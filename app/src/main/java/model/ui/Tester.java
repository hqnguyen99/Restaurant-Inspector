package model.ui;

import java.util.List;

import model.Restaurant;
import model.RestaurantManager;

public class Tester {
    public static void main(String[] args) {
        RestaurantManager manager  = new RestaurantManager();
        List<Restaurant> restaurantList = manager.getRestaurantList();

        for (Restaurant r : restaurantList) {
            System.out.println(r.toString() + "\n");
        }
    }
}
