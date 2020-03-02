package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;

public class RestaurantManager {
    private List<Restaurant> restaurantList;

    public RestaurantManager() {
        restaurantList = getListFromFile(new File("data/restaurants_itr1.csv"));
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void add(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurantList.remove(restaurant);
    }

    public List<Restaurant> getListFromFile(File file) {
        List<Restaurant> result = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String values[] = line.split(",");

            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].replaceAll("^\"|\"$", "");
            }

            result.add(new Restaurant(
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4],
                    Double.parseDouble(values[5]),
                    Double.parseDouble(values[6])
            ));
        }

        return result;
    }

}
