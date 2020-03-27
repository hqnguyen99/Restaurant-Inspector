package cmpt276.restaurant_inspector.MapsClass;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

// This class handle each restaurant marker in Google Map

public class MyItem implements ClusterItem {
    private final LatLng position;
    private String title = "";
    private String snippet = "";
    private final BitmapDescriptor icon;
    private final int positionInRestaurantList;
    private final String harzardLevel;

    public MyItem(double lat, double lng, String title, String snippet, BitmapDescriptor icon, int positionInRestaurantList, String harzardLevel) {
        this.position = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
        this.icon = icon;
        this.positionInRestaurantList = positionInRestaurantList;
        this.harzardLevel = harzardLevel;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getSnippet() {
        return snippet;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public int getPositionInRestaurantList() {
        return positionInRestaurantList;
    }

    public String getHarzardLevel() {
        return harzardLevel;
    }
}
