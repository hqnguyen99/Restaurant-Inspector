package cmpt276.restaurant_inspector.model;

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
 class RestaurantManager
 {
    private List<Restaurant> restaurantList = new ArrayList<>();

    RestaurantManager(BufferedReader reader) {
        setListFromFile(reader);
    }

    List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    private void add(Restaurant restaurant) {
        restaurantList.add(restaurant);
        sort(restaurantList);
    }

     private void setListFromFile(BufferedReader reader)
     {
        String line = "";

        try {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                List<String> values = csvParserUtil.parseLine(line);
                add(new Restaurant(
                    values.get(0),
                    values.get(1),
                    values.get(2),
                    values.get(3),
                    values.get(4),
                    Double.parseDouble(values.get(5)),
                    Double.parseDouble(values.get(6))
                ));
            }
        } catch (IOException e) {
            Log.wtf("RestaurantManager", "Error reading file on line " + line, e);
            e.printStackTrace();
        }
    }
 }
