package model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public interface DataSingleton {
        void init(BufferedReader restaurantReader, BufferedReader inspectionReader);
        Map<String, RestaurantInspectionsPair> entries();
        RestaurantInspectionsPair getEntryAtIndex(int index);
        int size();
}
