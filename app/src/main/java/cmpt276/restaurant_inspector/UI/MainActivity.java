package cmpt276.restaurant_inspector.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cmpt276.restaurant_inspector.service.FileDownloadClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *  Splash screen to welcome user
 */
public class MainActivity extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST = 100;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //grant access to download to disk
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST);
//        }

        downloadRestaurants();
        Log.d("Start", " yes!");
        downloadCsv("api/3/action/package_show?id=restaurants");
        //download on startup, check feature not implemented yet


        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
            Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
            startActivity(intent);
            finish();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void downloadRestaurants() {
        //create Retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://data.surrey.ca/");

        Retrofit retrofit = builder.build();

        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);
        Call<ResponseBody> call = fileDownloadClient.downloadRestaurants();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                //boolean writtenToDisk = writeResponseBodyToDisk(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            //change pathname to proper file directory
            File restaurants = new File(getExternalFilesDir(null) + File.separator + "restaurants.csv");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(restaurants);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("Restaurant file", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void downloadCsv(String url) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://data.surrey.ca/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
        FileDownloadClient client = retrofit.create(FileDownloadClient.class);
        Call<String> stringCall = client.getFileTypeResponse(url);

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String responseString = response.body();
                    String resourceStrings[] = getResourceStrings(responseString);
                    int i;

                    for (i = 0; !resourceStrings[i].contains("CSV"); i++) {
                    }

                    String csvString = resourceStrings[i];
                    Log.d("downloadUrl", getFileUrl(csvString, "url"));
                    Log.d("downloadDate", getFileUrl(csvString, "last_modified"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("DataDownload: ", "failed");
            }
        });
    }

    private String getFileUrl(String jsonInput, String attribute) {
        final String ATTRIBUTE = '"' + attribute + "\": ";
        int start = jsonInput.indexOf(ATTRIBUTE) + ATTRIBUTE.length();
        int end = jsonInput.indexOf('"', start);

        return jsonInput.substring(start, end);
    }

    private String[] getResourceStrings(String jsonInput) {
        final String RESOURCES = "\"resources\": [";
        int start = jsonInput.indexOf(RESOURCES) + RESOURCES.length();
        int end = jsonInput.indexOf(']', start);

        return jsonInput.substring(start, end).split(",");
    }
}
