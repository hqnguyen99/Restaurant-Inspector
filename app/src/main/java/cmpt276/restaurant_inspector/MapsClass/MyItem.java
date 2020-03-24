package cmpt276.restaurant_inspector.MapsClass;

import android.graphics.drawable.Icon;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle = "";
    private String mSnippet = "";
    private final BitmapDescriptor icon;

    public MyItem(double lat, double lng, BitmapDescriptor icon) {
        this.mPosition = new LatLng(lat, lng);
        this.icon = icon;
    }

    public MyItem(double lat, double lng, String mTitle, String mSnippet, BitmapDescriptor icon) {
        this.mPosition = new LatLng(lat, lng);
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
        this.icon = icon;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }
    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }
}
