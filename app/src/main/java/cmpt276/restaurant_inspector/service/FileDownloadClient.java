package cmpt276.restaurant_inspector.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FileDownloadClient {

    @GET("tempForRestaurants")
    Call<ResponseBody> downloadRestaurants();

    /*@GET("tempForInspections")
    Call<ResponseBody> downloadInspections();*/
}
