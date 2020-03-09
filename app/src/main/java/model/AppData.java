package model;

public class AppData {
    private static AppData instance = null;
    private final RestaurantManager restaurantManager;
    private final InspectionManager inspectionManager;

    private AppData(RestaurantManager restaurantManager,
                    InspectionManager inspectionManager) {
        this.restaurantManager = restaurantManager;
        this.inspectionManager = inspectionManager;
    }

    public static void init(String restaurantDataPath, String inspectionDataPath) {
        if (instance == null) {
            instance = new AppData(
              new RestaurantManager(restaurantDataPath),
              new InspectionManager(inspectionDataPath)
            );
        }
    }

    public static AppData getInstance() {
        return instance;
    }
}
