package model;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;

public interface DataSingleton {
        void init(BufferedReader restaurantReader, BufferedReader inspectionReader);
        Map<String, RestaurantInspectionsPair> entries();
}
