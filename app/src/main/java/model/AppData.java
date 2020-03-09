package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Singleton class that holds a map of Restaurant/Inspections entries
 * using the restaurant id as the key
 */
public class AppData {
    private static AppData INSTANCE = null;
    private static RestaurantManager restaurantManager;
    private static InspectionManager inspectionManager;
    public static Map<String, RestaurantInspectionsPair> entries = new LinkedHashMap<>();

    private AppData(RestaurantManager restaurantManager,
                    InspectionManager inspectionManager) {
        AppData.restaurantManager = restaurantManager;
        AppData.inspectionManager = inspectionManager;
    }

    public static void init(String restaurantDataPath, String inspectionDataPath) {
        if (INSTANCE == null) {
            INSTANCE = new AppData(
              new RestaurantManager(restaurantDataPath),
              new InspectionManager(inspectionDataPath)
            );
            makePairs();
        }
    }

    public static AppData getInstance() {
        return INSTANCE;
    }

    private static void makePairs() {
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
