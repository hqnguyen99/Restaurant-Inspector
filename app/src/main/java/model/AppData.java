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
    INSTANCE
    {
        private RestaurantManager restaurantManager;
        private InspectionManager inspectionManager;
        private Map<String, RestaurantInspectionsPair> entries;
        private List<String> keyList;

        @Override
        public void init(BufferedReader restaurantReader, BufferedReader inspectionReader)
        {
            if (restaurantManager == null) {
                restaurantManager = new RestaurantManager(restaurantReader);
            }

            if (inspectionManager == null) {
                inspectionManager = new InspectionManager(inspectionReader);
            }

            makePairs();
            generateKeyList();
        }

        @Override
        public Map<String, RestaurantInspectionsPair> entries() {
            return entries;
        }

        @Override
        public RestaurantInspectionsPair getEntryAtIndex(int index) {
            return entries.get(keyList.get(index));
        }

        @Override
        public int size() {
            return keyList.size();
        }

        private void makePairs()
        {
            if (entries != null) {
                return;
            }

            entries = new LinkedHashMap<>();
            createNewPairs();
            insertInspectionsToPairs();
        }

        private void createNewPairs()
        {
            List<Restaurant> restaurants = restaurantManager.getRestaurantList();

            for (int i = 0; i < restaurants.size(); i++) {
                Restaurant r = restaurants.get(i);
                entries.put(r.id, new RestaurantInspectionsPair(
                    r, new ArrayList<Inspection>())
                );
            }
        }

        private void insertInspectionsToPairs()
        {
            List<Inspection> inspections = inspectionManager.getInspectionList();

            for (Inspection i : inspections) {
                if (entries.containsKey(i.id)) {
                    Objects.requireNonNull(entries.get(i.id)).addInspection(i);
                }
            }
        }

        private void generateKeyList()
        {
            if (keyList != null) {
                return;
            }

            keyList = new ArrayList<>(entries.keySet());
        }
    }
}
