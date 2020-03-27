package cmpt276.restaurant_inspector.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FileDownloadClient {

    @GET("api/3/action/package_show?id=restaurants")
    Call<ResponseBody> downloadRestaurants();

    //@GET ("URL")
    //Call<List<List<URL>>> getRestaurantURL();

    /*@GET("tempForInspections")
    Call<ResponseBody> downloadInspections();*/
}
