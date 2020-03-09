package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;

import static java.util.Collections.sort;

public class RestaurantManager {
    private List<Restaurant> restaurantList = new ArrayList<>();

    RestaurantManager(String filePath) {
        setListFromFile(new File(filePath));
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void add(Restaurant restaurant) {
        restaurantList.add(restaurant);
        sort(restaurantList);
    }

    public void remove(Restaurant restaurant) {
        restaurantList.remove(restaurant);
    }

    private void setListFromFile(File file) {
        if (file.exists() && !file.isDirectory()) {
            restaurantList.clear();
        }

        try {
            Scanner in = new Scanner(file);
            in.nextLine();

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String values[] = line.split(",");

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

            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid File.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
