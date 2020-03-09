package model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Singleton class that holds a map of Restaurant/Inspections entries
 * using the restaurant id as the key
 */
public enum AppData implements DataSingleton{
    INSTANCE {
        private RestaurantManager restaurantManager;
        private InspectionManager inspectionManager;
        private Map<String, RestaurantInspectionsPair> entries = new LinkedHashMap<>();

        @Override
        public void init(BufferedReader restaurantReader, BufferedReader inspectionReader) {
            if (restaurantManager == null) {
                restaurantManager = new RestaurantManager(restaurantReader);
            }

            if (inspectionManager == null) {
                inspectionManager = new InspectionManager(inspectionReader);
            }

            makePairs();
        }

        @Override
        public Map<String, RestaurantInspectionsPair> entries() {
            return entries;
        }

        private void makePairs() {
            List<Restaurant> restaurants = restaurantManager.getRestaurantList();

            // Create new Pairs for each restaurant in restaurantManager's underlying ArrayList
            for (int i = 0; i < restaurants.size(); i++) {
                Restaurant r = restaurants.get(i);
                entries.put(r.id, new RestaurantInspectionsPair(
                        r, new ArrayList<Inspection>()
                ));
            }

            List<Inspection> inspections = inspectionManager.getInspectionList();

            // Put inspections into the created Pairs using the restaurant's id as the key
            for (Inspection i : inspections) {
                if (entries.containsKey(i.id)) {
                    Objects.requireNonNull(entries.get(i.id)).addInspection(i);
                }
            }

        }
    }
}
