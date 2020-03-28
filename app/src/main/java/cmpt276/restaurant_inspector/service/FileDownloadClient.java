package cmpt276.restaurant_inspector.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface FileDownloadClient {

    @GET("api/3/action/package_show?id=restaurants")
    Call<ResponseBody> downloadRestaurants();

    // https://stackoverflow.com/questions/40973633/retrofit-2-get-json-from-response-body
    // https://square.github.io/retrofit/2.x/converter-scalars/retrofit2/converter/scalars/ScalarsConverterFactory.html
    @GET
    Call<String> getFileTypeResponse(@Url String url);

    @GET
    Call<ResponseBody> downloadFileFromUrl(@Url String fileUrl);

    //@GET ("URL")
    //Call<List<List<URL>>> getRestaurantURL();

    /*@GET("tempForInspections")
    Call<ResponseBody> downloadInspections();*/
}
