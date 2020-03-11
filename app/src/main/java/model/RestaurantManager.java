package model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

 /**
 * Contains the data of all restaurants.
 * Loads data into underlying its ArrayList if initialized with a csv filepath
 */
public class RestaurantManager {
    private List<Restaurant> restaurantList = new ArrayList<>();

    RestaurantManager(BufferedReader reader) {
        setListFromFile(reader);
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void add(Restaurant restaurant) {
        restaurantList.add(restaurant);
        sort(restaurantList);
    }

     private void setListFromFile(BufferedReader reader) {
        String line = "";

        try {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "");
                }

                add(new Restaurant(
                        values[0],
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6])
                ));
            }
        } catch (IOException e) {
            Log.wtf("RestaurantManager", "Error reading file on line " + line, e);
            e.printStackTrace();
        }
    }

}
