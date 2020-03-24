package cmpt276.restaurant_inspector.MapsClass;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import cmpt276.restaurant_inspector.UI.MapsActivity;

public class CustomClusterRenderer extends DefaultClusterRenderer<MyItem> {
    private final Context mContext;


    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        this.mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item,
                                                         MarkerOptions markerOptions) {
        markerOptions.icon(item.getIcon()).title(item.getTitle()).snippet(item.getSnippet());

    }

    @Override
    public void setMinClusterSize(int minClusterSize) {
        super.setMinClusterSize(minClusterSize);
    }
}
