package hqnguyen.sfu.restaurantinspector.model;

import android.util.Log;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;

import hqnguyen.sfu.restaurantinspector.R;

public class RestaurantManager {
    private List<Restaurant> restaurantList;

    public RestaurantManager() {
        restaurantList = new ArrayList<>();
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

    // this only works in an activity
    /*
    public void readRestaurantData(){
        InputStream is = this.getResources().openRawResource(R.raw.restaurants_itr1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            //step over headers
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                //Split by ','
                String[] values = line.split(",");

                //read the data

                //we should use setters and getters here along instead

                restaurantList.add(new Restaurant(
                        values[0],
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6])
                ));
                // to check for empty, if double is empty set to 0
            }
        } catch (IOException e) {
            Log.wtf("MyActivity" , "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

    }
    */
    public void setListFromFile(File file) {
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

                restaurantList.add(new Restaurant(
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
